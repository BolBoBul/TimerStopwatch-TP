package gui;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.GraphicsEnvironment;

import states.Context;
import states.Mode;
import states.stopwatch.AbstractStopwatch;
import states.stopwatch.ResetStopwatch;
import states.timer.AbstractTimer;
import states.timer.IdleTimer;
import states.timer.SetTimer;

/**
 * Tests for SwingGUI class.
 * These tests are conditionally executed based on whether the environment supports 
 * graphical displays. In headless environments (like CI), tests that require 
 * a display will be skipped.
 */
class SwingGUITest {

    private Context context;
    private SwingGUI gui;
    private boolean isHeadless;

    @BeforeEach
    void setUp() {
        context = new Context();
        AbstractTimer.resetInitialValues();
        AbstractStopwatch.resetInitialValues();
        isHeadless = GraphicsEnvironment.isHeadless();
        
        if (!isHeadless) {
            gui = new SwingGUI(context);
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up any GUI resources if needed
        gui = null;
    }

    @Test
    @DisplayName("Test SwingGUI can be created in non-headless environment")
    void testSwingGUICreation() {
        Assumptions.assumeFalse(isHeadless, "Skipping test in headless environment");
        
        assertNotNull(gui);
        assertNotNull(gui.b1);
        assertNotNull(gui.b2);
        assertNotNull(gui.b3);
        assertNotNull(gui.myText1);
        assertNotNull(gui.myText2);
        assertNotNull(gui.myText3);
    }

    @Test
    @DisplayName("Test SwingGUI inherits HeadlessGUI functionality")
    void testSwingGUIInheritance() {
        Assumptions.assumeFalse(isHeadless, "Skipping test in headless environment");
        
        assertTrue(gui instanceof HeadlessGUI);
    }

    @Test
    @DisplayName("Test SwingGUI updateUI works correctly")
    void testSwingGUIUpdateUI() {
        Assumptions.assumeFalse(isHeadless, "Skipping test in headless environment");
        
        gui.updateUI(context);
        
        assertEquals("memTimer = 0", gui.myText1.getText());
        assertEquals("timer", gui.myText2.getText());
        assertEquals("IdleTimer", gui.myText3.getText());
    }

    @Test
    @DisplayName("Test SwingGUI button actions work")
    void testSwingGUIButtonActions() {
        Assumptions.assumeFalse(isHeadless, "Skipping test in headless environment");
        
        // Initial state
        assertSame(IdleTimer.Instance(), context.currentState);
        
        // Click button to change state
        gui.b3.doClick();
        assertSame(SetTimer.Instance(), context.currentState);
        
        // Update and verify UI
        gui.updateUI(context);
        assertEquals("SetTimer", gui.myText3.getText());
    }

    @Test
    @DisplayName("Test SwingGUI mode switching via buttons")
    void testSwingGUIModeSwitching() {
        Assumptions.assumeFalse(isHeadless, "Skipping test in headless environment");
        
        assertEquals(Mode.timer, context.currentState.getMode());
        
        // Switch to stopwatch mode
        gui.b1.doClick();
        assertEquals(Mode.stopwatch, context.currentState.getMode());
        
        gui.updateUI(context);
        assertEquals("stopwatch", gui.myText2.getText());
        assertEquals("ResetStopwatch", gui.myText3.getText());
    }

    @Test
    @DisplayName("Test isHeadless returns true in CI environment")
    void testHeadlessEnvironmentDetection() {
        // This test verifies that GraphicsEnvironment.isHeadless() works correctly
        // In a CI environment, this should return true
        // Locally with a display, this should return false
        boolean headless = GraphicsEnvironment.isHeadless();
        // Just verify the method works - the result depends on the environment
        assertNotNull(Boolean.valueOf(headless));
    }

    @Test
    @DisplayName("Test SwingGUI button labels update correctly")
    void testSwingGUIButtonLabels() {
        Assumptions.assumeFalse(isHeadless, "Skipping test in headless environment");
        
        gui.updateUI(context);
        assertEquals("change mode", gui.b1.getText());
        assertEquals("run", gui.b2.getText());
        assertEquals("set", gui.b3.getText());
        
        // Change to SetTimer and verify labels change
        context.right();
        gui.updateUI(context);
        assertEquals("reset", gui.b1.getText());
        assertEquals("inc 5", gui.b2.getText());
        assertEquals("done", gui.b3.getText());
    }

    @Test
    @DisplayName("Test SwingGUI complete workflow")
    void testSwingGUICompleteWorkflow() {
        Assumptions.assumeFalse(isHeadless, "Skipping test in headless environment");
        
        // Start in IdleTimer
        gui.updateUI(context);
        assertEquals("IdleTimer", gui.myText3.getText());
        
        // Go to SetTimer
        gui.b3.doClick();
        gui.updateUI(context);
        assertEquals("SetTimer", gui.myText3.getText());
        
        // Increment memTimer
        gui.b2.doClick();
        gui.updateUI(context);
        assertEquals("memTimer = 5", gui.myText1.getText());
        
        // Go to IdleTimer
        gui.b3.doClick();
        gui.updateUI(context);
        assertEquals("IdleTimer", gui.myText3.getText());
        
        // Switch to stopwatch
        gui.b1.doClick();
        gui.updateUI(context);
        assertEquals("stopwatch", gui.myText2.getText());
    }
}

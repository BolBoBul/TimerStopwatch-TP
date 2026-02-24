package gui;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.Context;
import states.Mode;
import states.stopwatch.AbstractStopwatch;
import states.stopwatch.ResetStopwatch;
import states.stopwatch.RunningStopwatch;
import states.timer.AbstractTimer;
import states.timer.IdleTimer;
import states.timer.SetTimer;

class HeadlessGUITest {

    private Context context;
    private HeadlessGUI gui;

    @BeforeEach
    void setUp() {
        context = new Context();
        AbstractTimer.resetInitialValues();
        AbstractStopwatch.resetInitialValues();
        gui = new HeadlessGUI(context);
    }

    @Test
    @DisplayName("Test HeadlessGUI initialization creates buttons")
    void testInitializationButtons() {
        assertNotNull(gui.b1);
        assertNotNull(gui.b2);
        assertNotNull(gui.b3);
    }

    @Test
    @DisplayName("Test HeadlessGUI initialization creates labels")
    void testInitializationLabels() {
        assertNotNull(gui.myText1);
        assertNotNull(gui.myText2);
        assertNotNull(gui.myText3);
    }

    @Test
    @DisplayName("Test updateUI sets correct text for IdleTimer")
    void testUpdateUIIdleTimer() {
        gui.updateUI(context);
        
        assertEquals("memTimer = 0", gui.myText1.getText());
        assertEquals("timer", gui.myText2.getText());
        assertEquals("IdleTimer", gui.myText3.getText());
        assertEquals("change mode", gui.b1.getText());
        assertEquals("run", gui.b2.getText());
        assertEquals("set", gui.b3.getText());
    }

    @Test
    @DisplayName("Test updateUI sets correct text for SetTimer")
    void testUpdateUISetTimer() {
        context.right(); // Transition to SetTimer
        gui.updateUI(context);
        
        assertEquals("memTimer = 0", gui.myText1.getText());
        assertEquals("timer", gui.myText2.getText());
        assertEquals("SetTimer", gui.myText3.getText());
        assertEquals("reset", gui.b1.getText());
        assertEquals("inc 5", gui.b2.getText());
        assertEquals("done", gui.b3.getText());
    }

    @Test
    @DisplayName("Test updateUI sets correct text for Stopwatch mode")
    void testUpdateUIStopwatch() {
        context.left(); // Transition to Stopwatch
        gui.updateUI(context);
        
        assertEquals("totalTime = 0", gui.myText1.getText());
        assertEquals("stopwatch", gui.myText2.getText());
        assertEquals("ResetStopwatch", gui.myText3.getText());
        assertEquals("change mode", gui.b1.getText());
        assertEquals("run", gui.b2.getText());
        assertEquals("(unused)", gui.b3.getText());
    }

    @Test
    @DisplayName("Test button1 click triggers left action")
    void testButton1ActionListener() {
        // Initial state should be IdleTimer
        assertSame(IdleTimer.Instance(), context.currentState);
        
        // Simulate button1 click (calls observer.left())
        gui.b1.doClick();
        
        // Should transition to Stopwatch mode
        assertEquals(Mode.stopwatch, context.currentState.getMode());
        assertSame(ResetStopwatch.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test button2 click triggers up action in stopwatch")
    void testButton2ActionListener() {
        // Go to stopwatch first
        context.left();
        assertSame(ResetStopwatch.Instance(), context.currentState);
        
        // Simulate button2 click (calls observer.up())
        gui.b2.doClick();
        
        // Should transition to RunningStopwatch
        assertSame(RunningStopwatch.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test button3 click triggers right action")
    void testButton3ActionListener() {
        // Initial state should be IdleTimer
        assertSame(IdleTimer.Instance(), context.currentState);
        
        // Simulate button3 click (calls observer.right())
        gui.b3.doClick();
        
        // Should transition to SetTimer
        assertSame(SetTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test updateUI reflects state changes")
    void testUpdateUIReflectsStateChanges() {
        // Initial state
        gui.updateUI(context);
        assertEquals("IdleTimer", gui.myText3.getText());
        
        // Change state
        context.right();
        gui.updateUI(context);
        assertEquals("SetTimer", gui.myText3.getText());
        
        // Increment memTimer
        context.up();
        gui.updateUI(context);
        assertEquals("memTimer = 5", gui.myText1.getText());
    }

    @Test
    @DisplayName("Test updateUI updates button labels correctly")
    void testUpdateUIButtonLabels() {
        gui.updateUI(context);
        assertEquals("change mode", gui.b1.getText());
        assertEquals("run", gui.b2.getText());
        assertEquals("set", gui.b3.getText());
        
        // Change to SetTimer
        context.right();
        gui.updateUI(context);
        assertEquals("reset", gui.b1.getText());
        assertEquals("inc 5", gui.b2.getText());
        assertEquals("done", gui.b3.getText());
    }

    @Test
    @DisplayName("Test complete workflow through GUI")
    void testCompleteWorkflow() {
        // 1. Go to SetTimer via button3
        gui.b3.doClick();
        gui.updateUI(context);
        assertEquals("SetTimer", gui.myText3.getText());
        
        // 2. Increment memTimer via button2
        gui.b2.doClick();
        gui.updateUI(context);
        assertEquals("memTimer = 5", gui.myText1.getText());
        
        // 3. Go back to IdleTimer via button3
        gui.b3.doClick();
        gui.updateUI(context);
        assertEquals("IdleTimer", gui.myText3.getText());
        assertEquals("memTimer = 5", gui.myText1.getText());
        
        // 4. Switch to stopwatch mode via button1
        gui.b1.doClick();
        gui.updateUI(context);
        assertEquals("stopwatch", gui.myText2.getText());
        assertEquals("ResetStopwatch", gui.myText3.getText());
        
        // 5. Start stopwatch via button2
        gui.b2.doClick();
        gui.updateUI(context);
        assertEquals("RunningStopwatch", gui.myText3.getText());
    }

    @Test
    @DisplayName("Test mode display text for timer")
    void testModeDisplayTimer() {
        gui.updateUI(context);
        assertEquals("timer", gui.myText2.getText());
    }

    @Test
    @DisplayName("Test mode display text for stopwatch")
    void testModeDisplayStopwatch() {
        context.left();
        gui.updateUI(context);
        assertEquals("stopwatch", gui.myText2.getText());
    }

    @Test
    @DisplayName("Test multiple button clicks in sequence")
    void testMultipleButtonClicks() {
        // SetTimer -> increment -> increment -> done -> run timer
        gui.b3.doClick(); // SetTimer
        gui.b2.doClick(); // memTimer = 5
        gui.b2.doClick(); // memTimer = 10
        gui.b3.doClick(); // IdleTimer
        
        assertEquals(10, AbstractTimer.getMemTimer());
        assertSame(IdleTimer.Instance(), context.currentState);
    }
}

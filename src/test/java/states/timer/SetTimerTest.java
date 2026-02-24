package states.timer;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.ClockState;
import states.Context;

class SetTimerTest {

    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        AbstractTimer.resetInitialValues();
        // Navigate to SetTimer state
        context.right();
    }

    @Test
    @DisplayName("Verify we are in SetTimer state after setup")
    void testSetTimerState() {
        assertSame(SetTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test left button resets memTimer to 0")
    void testLeftResetsMemTimer() {
        // First increment memTimer
        context.up(); // memTimer = 5
        context.up(); // memTimer = 10
        assertEquals(10, AbstractTimer.getMemTimer());

        // Now reset with left
        context.left();
        assertEquals(0, AbstractTimer.getMemTimer());
        // Should stay in SetTimer state
        assertSame(SetTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test up button increments memTimer by 5")
    void testUpIncrementsMemTimer() {
        assertEquals(0, AbstractTimer.getMemTimer());
        
        context.up();
        assertEquals(5, AbstractTimer.getMemTimer());
        
        context.up();
        assertEquals(10, AbstractTimer.getMemTimer());
        
        // Should stay in SetTimer state
        assertSame(SetTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test right button returns to IdleTimer")
    void testRightReturnsToIdle() {
        context.up(); // Set some value
        assertEquals(5, AbstractTimer.getMemTimer());
        
        context.right();
        assertSame(IdleTimer.Instance(), context.currentState);
        // memTimer should retain its value
        assertEquals(5, AbstractTimer.getMemTimer());
    }

    @Test
    @DisplayName("Test doIt (tick) increments memTimer")
    void testDoItIncrementsMemTimer() {
        assertEquals(0, AbstractTimer.getMemTimer());
        
        context.tick();
        assertEquals(1, AbstractTimer.getMemTimer());
        
        context.tick();
        assertEquals(2, AbstractTimer.getMemTimer());
        
        // Should stay in SetTimer state
        assertSame(SetTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test getDisplayString shows memTimer")
    void testGetDisplayString() {
        context.up(); // memTimer = 5
        assertEquals("memTimer = 5", context.currentState.getDisplayString());
    }

    @Test
    @DisplayName("Test button labels in SetTimer state")
    void testButtonLabels() {
        assertEquals("reset", context.currentState.getLeftText());
        assertEquals("inc 5", context.currentState.getUpText());
        assertEquals("done", context.currentState.getRightText());
    }
}

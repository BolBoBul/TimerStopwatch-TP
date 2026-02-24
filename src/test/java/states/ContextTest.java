package states;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.stopwatch.AbstractStopwatch;
import states.stopwatch.ResetStopwatch;
import states.stopwatch.RunningStopwatch;
import states.timer.AbstractTimer;
import states.timer.IdleTimer;
import states.timer.SetTimer;

class ContextTest {

    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        AbstractTimer.resetInitialValues();
        AbstractStopwatch.resetInitialValues();
    }

    @Test
    @DisplayName("Test initial state is IdleTimer")
    void testInitialState() {
        assertSame(IdleTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test getDisplayText returns state's display string")
    void testGetDisplayText() {
        assertEquals("memTimer = 0", context.getDisplayText());
        
        context.right(); // SetTimer
        context.up();    // increment memTimer
        assertEquals("memTimer = 5", context.getDisplayText());
    }

    @Test
    @DisplayName("Test getStateText returns class simple name")
    void testGetStateText() {
        assertEquals("IdleTimer", context.getStateText());
        
        context.right();
        assertEquals("SetTimer", context.getStateText());
        
        context.right();
        assertEquals("IdleTimer", context.getStateText());
        
        context.left();
        assertEquals("ResetStopwatch", context.getStateText());
    }

    @Test
    @DisplayName("Test getModeText returns mode name")
    void testGetModeText() {
        assertEquals("timer", context.getModeText());
        
        context.left();
        assertEquals("stopwatch", context.getModeText());
        
        context.left();
        assertEquals("timer", context.getModeText());
    }

    @Test
    @DisplayName("Test getLeftText returns button text")
    void testGetLeftText() {
        assertEquals("change mode", context.getLeftText());
        
        context.right(); // SetTimer
        assertEquals("reset", context.getLeftText());
    }

    @Test
    @DisplayName("Test getUpText returns button text")
    void testGetUpText() {
        assertEquals("run", context.getUpText());
        
        context.right(); // SetTimer
        assertEquals("inc 5", context.getUpText());
        
        context.right(); // IdleTimer
        context.left();  // ResetStopwatch
        assertEquals("run", context.getUpText());
    }

    @Test
    @DisplayName("Test getRightText returns button text")
    void testGetRightText() {
        assertEquals("set", context.getRightText());
        
        context.right(); // SetTimer
        assertEquals("done", context.getRightText());
        
        context.right(); // IdleTimer
        context.left();  // ResetStopwatch
        assertEquals("(unused)", context.getRightText());
    }

    @Test
    @DisplayName("Test left method changes mode")
    void testLeftMethod() {
        assertSame(IdleTimer.Instance(), context.currentState);
        
        context.left();
        assertSame(ResetStopwatch.Instance(), context.currentState);
        
        context.left();
        assertSame(IdleTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test up method triggers state action")
    void testUpMethod() {
        context.left(); // ResetStopwatch
        context.up();   // Should start running
        assertSame(RunningStopwatch.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test right method triggers state action")
    void testRightMethod() {
        context.right();
        assertSame(SetTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test tick method calls doIt on current state")
    void testTickMethod() {
        context.right(); // SetTimer
        assertEquals(0, AbstractTimer.getMemTimer());
        
        context.tick();
        assertEquals(1, AbstractTimer.getMemTimer());
        
        context.tick();
        assertEquals(2, AbstractTimer.getMemTimer());
    }

    @Test
    @DisplayName("Test history state mechanism for stopwatch")
    void testHistoryStateStopwatch() {
        // Go to stopwatch and start running
        context.left();
        context.up();
        assertSame(RunningStopwatch.Instance(), context.currentState);
        
        // Switch to timer
        context.left();
        assertEquals(Mode.timer, context.currentState.getMode());
        
        // Switch back should return to RunningStopwatch (history)
        context.left();
        assertSame(RunningStopwatch.Instance(), context.currentState);
    }
}

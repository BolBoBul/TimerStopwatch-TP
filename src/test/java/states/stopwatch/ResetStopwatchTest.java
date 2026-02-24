package states.stopwatch;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.Context;
import states.Mode;
import states.timer.AbstractTimer;

class ResetStopwatchTest {

    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        AbstractTimer.resetInitialValues();
        AbstractStopwatch.resetInitialValues();
        // Navigate to stopwatch mode
        context.left(); // Go to ResetStopwatch
    }

    @Test
    @DisplayName("Verify we are in ResetStopwatch state after setup")
    void testResetStopwatchState() {
        assertSame(ResetStopwatch.Instance(), context.currentState);
        assertEquals(Mode.stopwatch, context.currentState.getMode());
    }

    @Test
    @DisplayName("Test initial values are zero")
    void testInitialValues() {
        assertEquals(0, AbstractStopwatch.getTotalTime());
        assertEquals(0, AbstractStopwatch.getLapTime());
    }

    @Test
    @DisplayName("Test up button starts stopwatch")
    void testUpStartsStopwatch() {
        context.up();
        assertSame(RunningStopwatch.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test right button does nothing in ResetStopwatch")
    void testRightDoesNothing() {
        context.right();
        // Default behavior: stays in same state
        assertSame(ResetStopwatch.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test getDisplayString shows totalTime")
    void testGetDisplayString() {
        assertEquals("totalTime = 0", context.currentState.getDisplayString());
    }

    @Test
    @DisplayName("Test button labels in ResetStopwatch state")
    void testButtonLabels() {
        assertEquals("change mode", context.currentState.getLeftText());
        assertEquals("run", context.currentState.getUpText());
        assertEquals("(unused)", context.currentState.getRightText());
    }

    @Test
    @DisplayName("Test entry action resets times")
    void testEntryResetsValues() {
        // Start running and accumulate time
        context.up();
        context.tick();
        context.tick();
        assertEquals(2, AbstractStopwatch.getTotalTime());
        
        // Reset
        context.right();
        assertSame(ResetStopwatch.Instance(), context.currentState);
        assertEquals(0, AbstractStopwatch.getTotalTime());
        assertEquals(0, AbstractStopwatch.getLapTime());
    }

    @Test
    @DisplayName("Test left returns to timer mode with history")
    void testLeftReturnsToTimer() {
        context.left();
        assertEquals(Mode.timer, context.currentState.getMode());
    }
}

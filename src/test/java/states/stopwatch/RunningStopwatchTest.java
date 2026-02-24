package states.stopwatch;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.Context;
import states.Mode;
import states.timer.AbstractTimer;

class RunningStopwatchTest {

    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        AbstractTimer.resetInitialValues();
        AbstractStopwatch.resetInitialValues();
        // Navigate to stopwatch mode and start running
        context.left(); // Go to ResetStopwatch
        context.up();   // Go to RunningStopwatch
    }

    @Test
    @DisplayName("Verify we are in RunningStopwatch state after setup")
    void testRunningStopwatchState() {
        assertSame(RunningStopwatch.Instance(), context.currentState);
        assertEquals(Mode.stopwatch, context.currentState.getMode());
    }

    @Test
    @DisplayName("Test up button splits to LaptimeStopwatch")
    void testUpSplits() {
        context.tick(); // totalTime = 1
        context.up();
        assertSame(LaptimeStopwatch.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test right button resets to ResetStopwatch")
    void testRightResets() {
        context.tick();
        context.tick();
        assertEquals(2, AbstractStopwatch.getTotalTime());
        
        context.right();
        assertSame(ResetStopwatch.Instance(), context.currentState);
        // Entry action of ResetStopwatch resets times
        assertEquals(0, AbstractStopwatch.getTotalTime());
        assertEquals(0, AbstractStopwatch.getLapTime());
    }

    @Test
    @DisplayName("Test doIt (tick) increments totalTime")
    void testDoItIncrementsTotalTime() {
        assertEquals(0, AbstractStopwatch.getTotalTime());
        
        context.tick();
        assertEquals(1, AbstractStopwatch.getTotalTime());
        
        context.tick();
        assertEquals(2, AbstractStopwatch.getTotalTime());
        
        assertSame(RunningStopwatch.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test getDisplayString shows totalTime")
    void testGetDisplayString() {
        assertEquals("totalTime = 0", context.currentState.getDisplayString());
        context.tick();
        assertEquals("totalTime = 1", context.currentState.getDisplayString());
    }

    @Test
    @DisplayName("Test button labels in RunningStopwatch state")
    void testButtonLabels() {
        assertEquals("change mode", context.currentState.getLeftText());
        assertEquals("split", context.currentState.getUpText());
        assertEquals("reset", context.currentState.getRightText());
    }
}

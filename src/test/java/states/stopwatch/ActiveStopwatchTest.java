package states.stopwatch;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.Context;
import states.timer.AbstractTimer;

class ActiveStopwatchTest {

    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        AbstractTimer.resetInitialValues();
        AbstractStopwatch.resetInitialValues();
    }

    @Test
    @DisplayName("Test ActiveStopwatch.Instance returns RunningStopwatch")
    void testActiveStopwatchInstance() {
        // ActiveStopwatch is a composite state, its initial state is RunningStopwatch
        assertSame(RunningStopwatch.Instance(), ActiveStopwatch.Instance());
    }

    @Test
    @DisplayName("Test AbstractStopwatch.Instance returns ResetStopwatch")
    void testAbstractStopwatchInstance() {
        // AbstractStopwatch is a composite state, its initial state is ResetStopwatch
        assertSame(ResetStopwatch.Instance(), AbstractStopwatch.Instance());
    }

    @Test
    @DisplayName("Test history state transitions for stopwatch")
    void testHistoryStateTransitions() {
        // Go to stopwatch, run, and then switch modes
        context.left();  // ResetStopwatch
        context.up();    // RunningStopwatch
        context.tick();
        
        // Switch to timer mode
        context.left();
        
        // Return to stopwatch - should remember RunningStopwatch
        context.left();
        assertSame(RunningStopwatch.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test history state preserves LaptimeStopwatch")
    void testHistoryPreservesLaptime() {
        // Go to stopwatch, run, split
        context.left();  // ResetStopwatch
        context.up();    // RunningStopwatch
        context.tick();
        context.up();    // LaptimeStopwatch
        
        // Switch to timer mode
        context.left();
        
        // Return to stopwatch - should remember LaptimeStopwatch
        context.left();
        assertSame(LaptimeStopwatch.Instance(), context.currentState);
    }
}

package states.stopwatch;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.Context;
import states.timer.AbstractTimer;

class LaptimeStopwatchTest {

    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        AbstractTimer.resetInitialValues();
        AbstractStopwatch.resetInitialValues();
        // Navigate to stopwatch mode, run, then split
        context.left(); // Go to ResetStopwatch
        context.up();   // Go to RunningStopwatch
        context.tick(); // totalTime = 1
        context.tick(); // totalTime = 2
        context.up();   // Go to LaptimeStopwatch
    }

    @Test
    @DisplayName("Verify we are in LaptimeStopwatch state after setup")
    void testLaptimeStopwatchState() {
        assertSame(LaptimeStopwatch.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test lapTime is captured on entry")
    void testLapTimeOnEntry() {
        // LapTime should be 2 (totalTime when we entered LaptimeStopwatch)
        assertEquals(2, AbstractStopwatch.getLapTime());
        assertEquals(2, AbstractStopwatch.getTotalTime());
    }

    @Test
    @DisplayName("Test up button unsplits to RunningStopwatch")
    void testUpUnsplits() {
        context.up();
        assertSame(RunningStopwatch.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test doIt continues incrementing totalTime while showing lapTime")
    void testDoItContinuesIncrementing() {
        int lapTime = AbstractStopwatch.getLapTime();
        assertEquals(2, lapTime);
        
        context.tick();
        // totalTime should increment but lapTime stays the same
        assertEquals(3, AbstractStopwatch.getTotalTime());
        assertEquals(2, AbstractStopwatch.getLapTime());
        
        context.tick();
        assertEquals(4, AbstractStopwatch.getTotalTime());
        assertEquals(2, AbstractStopwatch.getLapTime());
    }

    @Test
    @DisplayName("Test automatic timeout returns to RunningStopwatch")
    void testTimeoutReturnsToRunning() {
        // Timeout is 5 seconds, let's tick 5 times
        context.tick(); // timeout = 4
        context.tick(); // timeout = 3
        context.tick(); // timeout = 2
        context.tick(); // timeout = 1
        assertSame(LaptimeStopwatch.Instance(), context.currentState);
        
        context.tick(); // timeout = 0, should transition
        assertSame(RunningStopwatch.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test getDisplayString shows lapTime")
    void testGetDisplayString() {
        assertEquals("lapTime = 2", context.currentState.getDisplayString());
    }

    @Test
    @DisplayName("Test button labels in LaptimeStopwatch state")
    void testButtonLabels() {
        assertEquals("change mode", context.currentState.getLeftText());
        assertEquals("unsplit", context.currentState.getUpText());
    }

    @Test
    @DisplayName("Test unsplit before timeout preserves totalTime")
    void testUnsplitBeforeTimeout() {
        context.tick(); // totalTime = 3
        context.tick(); // totalTime = 4
        
        context.up(); // unsplit
        assertSame(RunningStopwatch.Instance(), context.currentState);
        assertEquals(4, AbstractStopwatch.getTotalTime());
    }
}

package states.timer;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.Context;

class RunningTimerTest {

    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        AbstractTimer.resetInitialValues();
        // Set memTimer and start running
        context.right(); // Go to SetTimer
        context.up();    // memTimer = 5
        context.right(); // Back to IdleTimer
        context.up();    // Start running (timer = memTimer = 5)
    }

    @Test
    @DisplayName("Verify we are in RunningTimer state after setup")
    void testRunningTimerState() {
        assertSame(RunningTimer.Instance(), context.currentState);
        assertEquals(5, AbstractTimer.getTimer());
    }

    @Test
    @DisplayName("Test up button pauses the timer")
    void testUpPausesTimer() {
        context.up();
        assertSame(PausedTimer.Instance(), context.currentState);
        // Timer value should remain unchanged
        assertEquals(5, AbstractTimer.getTimer());
    }

    @Test
    @DisplayName("Test right button stops and returns to IdleTimer")
    void testRightStopsTimer() {
        context.right();
        assertSame(IdleTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test doIt (tick) decrements timer")
    void testDoItDecrementsTimer() {
        assertEquals(5, AbstractTimer.getTimer());
        
        context.tick();
        assertEquals(4, AbstractTimer.getTimer());
        assertSame(RunningTimer.Instance(), context.currentState);
        
        context.tick();
        assertEquals(3, AbstractTimer.getTimer());
    }

    @Test
    @DisplayName("Test timer countdown to ringing")
    void testTimerCountdownToRinging() {
        // Tick down to 1
        context.tick(); // timer = 4
        context.tick(); // timer = 3
        context.tick(); // timer = 2
        context.tick(); // timer = 1
        assertEquals(1, AbstractTimer.getTimer());
        assertSame(RunningTimer.Instance(), context.currentState);
        
        // Final tick should transition to RingingTimer
        context.tick(); // timer = 0
        assertEquals(0, AbstractTimer.getTimer());
        assertSame(RingingTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test getDisplayString shows timer")
    void testGetDisplayString() {
        assertEquals("timer = 5", context.currentState.getDisplayString());
        context.tick();
        assertEquals("timer = 4", context.currentState.getDisplayString());
    }

    @Test
    @DisplayName("Test button labels in RunningTimer state")
    void testButtonLabels() {
        assertEquals("change mode", context.currentState.getLeftText());
        assertEquals("pause", context.currentState.getUpText());
        assertEquals("stop", context.currentState.getRightText());
    }
}

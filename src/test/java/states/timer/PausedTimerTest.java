package states.timer;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.Context;

class PausedTimerTest {

    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        AbstractTimer.resetInitialValues();
        // Set memTimer and start running, then pause
        context.right(); // SetTimer
        context.up();    // memTimer = 5
        context.right(); // IdleTimer
        context.up();    // RunningTimer (timer = 5)
        context.tick();  // timer = 4
        context.up();    // PausedTimer
    }

    @Test
    @DisplayName("Verify we are in PausedTimer state after setup")
    void testPausedTimerState() {
        assertSame(PausedTimer.Instance(), context.currentState);
        assertEquals(4, AbstractTimer.getTimer());
    }

    @Test
    @DisplayName("Test up button resumes timer")
    void testUpResumesTimer() {
        context.up();
        assertSame(RunningTimer.Instance(), context.currentState);
        assertEquals(4, AbstractTimer.getTimer());
    }

    @Test
    @DisplayName("Test right button resets to IdleTimer")
    void testRightResetsToIdle() {
        context.right();
        assertSame(IdleTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test doIt (tick) does not change timer while paused")
    void testDoItDoesNotChangeTimer() {
        assertEquals(4, AbstractTimer.getTimer());
        context.tick();
        assertEquals(4, AbstractTimer.getTimer());
        assertSame(PausedTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test getDisplayString shows timer value")
    void testGetDisplayString() {
        assertEquals("timer = 4", context.currentState.getDisplayString());
    }

    @Test
    @DisplayName("Test button labels in PausedTimer state")
    void testButtonLabels() {
        assertEquals("change mode", context.currentState.getLeftText());
        assertEquals("run", context.currentState.getUpText());
        assertEquals("reset", context.currentState.getRightText());
    }

    @Test
    @DisplayName("Test pause and resume cycle")
    void testPauseResumeCycle() {
        int timerValue = AbstractTimer.getTimer();
        
        // Resume
        context.up();
        assertSame(RunningTimer.Instance(), context.currentState);
        
        // Tick once
        context.tick();
        assertEquals(timerValue - 1, AbstractTimer.getTimer());
        
        // Pause again
        context.up();
        assertSame(PausedTimer.Instance(), context.currentState);
        
        // Tick should not affect timer
        context.tick();
        assertEquals(timerValue - 1, AbstractTimer.getTimer());
    }
}

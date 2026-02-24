package states.timer;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.Context;
import states.Mode;

class IdleTimerTest {

    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        AbstractTimer.resetInitialValues();
    }

    @Test
    @DisplayName("Verify IdleTimer is initial state")
    void testIdleTimerIsInitial() {
        assertSame(IdleTimer.Instance(), context.currentState);
        assertEquals(Mode.timer, context.currentState.getMode());
    }

    @Test
    @DisplayName("Test up with memTimer=0 stays in IdleTimer")
    void testUpWithZeroMemTimer() {
        assertEquals(0, AbstractTimer.getMemTimer());
        context.up();
        assertSame(IdleTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test up with memTimer>0 goes to ActiveTimer")
    void testUpWithPositiveMemTimer() {
        // Set memTimer > 0
        context.right(); // SetTimer
        context.up();    // memTimer = 5
        context.right(); // IdleTimer
        
        assertEquals(5, AbstractTimer.getMemTimer());
        
        context.up(); // Should go to ActiveTimer (RunningTimer)
        assertSame(RunningTimer.Instance(), context.currentState);
        assertEquals(5, AbstractTimer.getTimer());
    }

    @Test
    @DisplayName("Test right goes to SetTimer")
    void testRightGoesToSetTimer() {
        context.right();
        assertSame(SetTimer.Instance(), context.currentState);
    }

    @Test
    @DisplayName("Test getDisplayString shows memTimer")
    void testGetDisplayString() {
        assertEquals("memTimer = 0", context.currentState.getDisplayString());
        
        context.right();
        context.up(); // memTimer = 5
        context.right();
        assertEquals("memTimer = 5", context.currentState.getDisplayString());
    }

    @Test
    @DisplayName("Test button labels in IdleTimer state")
    void testButtonLabels() {
        assertEquals("change mode", context.currentState.getLeftText());
        assertEquals("run", context.currentState.getUpText());
        assertEquals("set", context.currentState.getRightText());
    }

    @Test
    @DisplayName("Test AbstractTimer.Instance returns IdleTimer")
    void testAbstractTimerInstance() {
        assertSame(IdleTimer.Instance(), AbstractTimer.Instance());
    }
}

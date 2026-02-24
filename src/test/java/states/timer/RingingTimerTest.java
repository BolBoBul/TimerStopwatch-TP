package states.timer;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.Context;

class RingingTimerTest {

    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        AbstractTimer.resetInitialValues();
        // Set memTimer=1 and run until ringing
        context.right(); // SetTimer
        context.tick();  // memTimer = 1
        context.right(); // IdleTimer
        context.up();    // RunningTimer (timer = 1)
        context.tick();  // timer = 0, transitions to RingingTimer
    }

    @Test
    @DisplayName("Verify we are in RingingTimer state after setup")
    void testRingingTimerState() {
        assertSame(RingingTimer.Instance(), context.currentState);
        assertEquals(0, AbstractTimer.getTimer());
    }

    @Test
    @DisplayName("Test isRinging returns true when in RingingTimer")
    void testIsRingingTrue() {
        assertTrue(AbstractTimer.isRinging());
    }

    @Test
    @DisplayName("Test right button resets to IdleTimer and stops ringing")
    void testRightResetsToIdle() {
        assertTrue(AbstractTimer.isRinging());
        
        context.right();
        assertSame(IdleTimer.Instance(), context.currentState);
        assertFalse(AbstractTimer.isRinging());
    }

    @Test
    @DisplayName("Test getDisplayString shows Time's up")
    void testGetDisplayString() {
        assertEquals("Time's up !", context.currentState.getDisplayString());
    }

    @Test
    @DisplayName("Test doIt keeps ringing state")
    void testDoItKeepsRinging() {
        assertSame(RingingTimer.Instance(), context.currentState);
        context.tick();
        assertSame(RingingTimer.Instance(), context.currentState);
        assertTrue(AbstractTimer.isRinging());
    }

    @Test
    @DisplayName("Test button labels in RingingTimer state")
    void testButtonLabels() {
        assertEquals("change mode", context.currentState.getLeftText());
        assertEquals("(unused)", context.currentState.getUpText());
        assertEquals("reset", context.currentState.getRightText());
    }

    @Test
    @DisplayName("Test ringing flag is false initially")
    void testRingingFlagInitiallyFalse() {
        // Create a new context without reaching ringing state
        Context newContext = new Context();
        AbstractTimer.resetInitialValues();
        assertFalse(AbstractTimer.isRinging());
    }
}

package states;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.stopwatch.*;
import states.timer.*;

/**
 * BDD-style scenario tests for the Timer/Stopwatch application.
 * 
 * This class contains JUnit tests written in a BDD (Behavior-Driven Development) style,
 * using descriptive names and Given-When-Then structure in comments.
 * 
 * For full Cucumber BDD tests with Gherkin syntax, see:
 * - src/test/resources/features/timer.feature
 * - src/test/resources/features/stopwatch.feature
 * - src/test/java/bdd/ChronometerStepDefinitions.java
 */
class TestScenarios {

	Context c;
	
    @BeforeEach
	@DisplayName("Initialise the state machine context and reset the timer values")
	void setup() {
    	c = new Context();
     	//before each test, reset the timer values to avoid interference between tests:
    	AbstractTimer.resetInitialValues();
    	AbstractStopwatch.resetInitialValues();
    }

    // ========== TIMER BDD SCENARIOS ==========

    @Nested
    @DisplayName("Timer Scenarios")
    class TimerScenarios {
        
        @Test
        @DisplayName("Scenario: User sets timer and starts countdown")
        void givenIdleTimer_whenUserSetsValueAndStarts_thenTimerCountsDown() {
            // Given: The timer is in idle state
            assertEquals(IdleTimer.Instance(), c.currentState);
            assertEquals(0, AbstractTimer.getMemTimer());
            
            // When: User presses right to enter set mode and waits for 5 ticks
            c.right();
            for (int i = 0; i < 5; i++) {
                c.tick();
            }
            
            // Then: memTimer should be 5
            assertEquals(5, AbstractTimer.getMemTimer());
            assertSame(SetTimer.Instance(), c.currentState);
            
            // When: User presses right to exit set mode and up to start timer
            c.right();
            c.up();
            
            // Then: Timer should be running with value 5
            assertSame(RunningTimer.Instance(), c.currentState);
            assertEquals(5, AbstractTimer.getTimer());
            
            // When: 2 ticks pass
            c.tick();
            c.tick();
            
            // Then: Timer should have counted down to 3
            assertEquals(3, AbstractTimer.getTimer());
        }
        
        @Test
        @DisplayName("Scenario: User pauses and resumes timer")
        void givenRunningTimer_whenUserPausesAndResumes_thenTimerContinues() {
            // Given: Timer is set to 5 and running
            c.right();
            for (int i = 0; i < 5; i++) c.tick();
            c.right();
            c.up();
            c.tick(); // Timer is now 4
            
            // When: User pauses the timer
            c.up();
            
            // Then: Timer should be paused at 4
            assertSame(PausedTimer.Instance(), c.currentState);
            assertEquals(4, AbstractTimer.getTimer());
            
            // When: A tick passes (should not decrement while paused)
            c.tick();
            
            // Then: Timer should still be 4
            assertEquals(4, AbstractTimer.getTimer());
            
            // When: User resumes the timer
            c.up();
            
            // Then: Timer should be running again
            assertSame(RunningTimer.Instance(), c.currentState);
            
            // When: A tick passes
            c.tick();
            
            // Then: Timer should now be 3
            assertEquals(3, AbstractTimer.getTimer());
        }
        
        @Test
        @DisplayName("Scenario: Timer rings when reaching zero")
        void givenRunningTimer_whenCountdownReachesZero_thenTimerRings() {
            // Given: Timer is set to 2 and running
            c.right();
            c.tick();
            c.tick();
            c.right();
            c.up();
            
            // When: Timer counts down to zero
            c.tick();
            c.tick();
            
            // Then: Timer should be ringing
            assertSame(RingingTimer.Instance(), c.currentState);
            assertEquals(0, AbstractTimer.getTimer());
            
            // When: User acknowledges the alarm
            c.right();
            
            // Then: Timer returns to idle
            assertSame(IdleTimer.Instance(), c.currentState);
        }
    }

    // ========== STOPWATCH BDD SCENARIOS ==========

    @Nested
    @DisplayName("Stopwatch Scenarios")
    class StopwatchScenarios {
        
        @BeforeEach
        void switchToStopwatch() {
            c.left(); // Switch to stopwatch mode
        }
        
        @Test
        @DisplayName("Scenario: User starts stopwatch and records time")
        void givenResetStopwatch_whenUserStartsAndWaits_thenTimeAccumulates() {
            // Given: Stopwatch is in reset state
            assertSame(ResetStopwatch.Instance(), c.currentState);
            assertEquals(0, AbstractStopwatch.getTotalTime());
            
            // When: User starts the stopwatch
            c.up();
            
            // Then: Stopwatch should be running
            assertSame(RunningStopwatch.Instance(), c.currentState);
            
            // When: 3 ticks pass
            c.tick();
            c.tick();
            c.tick();
            
            // Then: Total time should be 3
            assertEquals(3, AbstractStopwatch.getTotalTime());
        }
        
        @Test
        @DisplayName("Scenario: User records lap time")
        void givenRunningStopwatch_whenUserRecordsLap_thenLapTimeIsStored() {
            // Given: Stopwatch is running and 3 ticks have passed
            c.up();
            c.tick();
            c.tick();
            c.tick();
            assertEquals(3, AbstractStopwatch.getTotalTime());
            
            // When: User records lap time
            c.up();
            
            // Then: Lap time should be recorded and stopwatch continues in laptime mode
            assertSame(LaptimeStopwatch.Instance(), c.currentState);
            assertEquals(3, AbstractStopwatch.getLapTime());
            
            // When: 2 more ticks pass
            c.tick();
            c.tick();
            
            // Then: Total time continues, lap time also updates
            assertEquals(5, AbstractStopwatch.getTotalTime());
        }
        
        @Test
        @DisplayName("Scenario: User resets running stopwatch")
        void givenRunningStopwatch_whenUserResetsStopwatch_thenStopwatchReturnsToZero() {
            // Given: Stopwatch is running with accumulated time
            c.up();
            c.tick();
            c.tick();
            c.tick();
            assertEquals(3, AbstractStopwatch.getTotalTime());
            
            // When: User resets the stopwatch (pressing right)
            c.right();
            
            // Then: Stopwatch should be reset to zero
            assertSame(ResetStopwatch.Instance(), c.currentState);
            assertEquals(0, AbstractStopwatch.getTotalTime());
            assertEquals(0, AbstractStopwatch.getLapTime());
        }
    }

    // ========== MODE SWITCHING SCENARIOS ==========

    @Nested
    @DisplayName("Mode Switching Scenarios")
    class ModeSwitchingScenarios {
        
        @Test
        @DisplayName("Scenario: Switching modes preserves history state")
        void givenActiveStates_whenUserSwitchesModes_thenHistoryIsPreserved() {
            // Given: Timer is running
            c.right();
            c.tick();
            c.tick();
            c.right();
            c.up();
            assertSame(RunningTimer.Instance(), c.currentState);
            
            // When: User switches to stopwatch
            c.left();
            
            // Then: Stopwatch starts at reset state
            assertSame(ResetStopwatch.Instance(), c.currentState);
            assertEquals(Mode.stopwatch, c.currentState.getMode());
            
            // When: User switches back to timer
            c.left();
            
            // Then: Timer resumes at running state (history preserved)
            assertSame(RunningTimer.Instance(), c.currentState);
            assertEquals(Mode.timer, c.currentState.getMode());
        }
    }

    // ========== INTEGRATION SCENARIO ==========
    
    @Test
    @DisplayName("Scenario: Complete end-to-end user journey")
    void completeScenario() {
        /*
         * This integration test simulates a complete user journey:
         * 1. User sets timer to 2
         * 2. User starts and pauses timer
         * 3. User switches to stopwatch and records lap time
         * 4. User switches back to timer (history preserved)
         * 5. Timer finishes and rings
         * 6. User acknowledges alarm
         */
        
        // Given: Fresh context with timer in idle state
	  assertEquals(IdleTimer.Instance(),c.currentState);
	  assertEquals(0,AbstractTimer.getMemTimer());
	  
        // When: User enters set mode and sets timer to 2
	  c.right(); // start incrementing the memTimer variable
	  c.tick();
	  assertSame(SetTimer.Instance(),c.currentState);
	  assertEquals(1,AbstractTimer.getMemTimer());
	  assertEquals(0,AbstractTimer.getTimer());

	  c.tick();
	  assertEquals(2,AbstractTimer.getMemTimer());
	  assertEquals(0,AbstractTimer.getTimer());

        // When: User exits set mode
	  c.right(); // stop incrementing the memTimer variable
	  c.tick();
	  assertEquals(2,AbstractTimer.getMemTimer());
	  assertEquals(0,AbstractTimer.getTimer());
	  
        // When: User starts the timer
	  c.up(); // start running the timer
	  assertEquals(2, AbstractTimer.getTimer(),"value of timer ");
	  c.tick();
	  assertEquals(2, AbstractTimer.getMemTimer(),"value of memTimer ");
	  assertEquals(1, AbstractTimer.getTimer(),"value of timer ");
	  
        // When: User pauses the timer
	  c.up(); // pause the timer
	  c.tick();
	  assertSame(PausedTimer.Instance(), c.currentState);
	  assertEquals(2, AbstractTimer.getMemTimer(),"value of memTimer ");
	  assertEquals(1, AbstractTimer.getTimer(),"value of timer ");
	  
        // When: User switches to stopwatch mode
	  c.left(); // go to stopwatch mode
	  c.tick();
	  assertSame(ResetStopwatch.Instance(), c.currentState);
	  assertEquals(0, AbstractStopwatch.getTotalTime(),"value of totalTime ");
	  assertEquals(0, AbstractStopwatch.getLapTime(),"value of lapTime ");
	  
        // When: User starts stopwatch and records lap time
	  c.up(); //start running the stopwatch
	  c.tick();
	  assertSame(RunningStopwatch.Instance(), c.currentState);
	  assertEquals(1, AbstractStopwatch.getTotalTime(),"value of totalTime ");
	  assertEquals(0, AbstractStopwatch.getLapTime(),"value of lapTime ");
	 
	  c.up(); // record stopwatch laptime
	  c.tick();
	  assertSame(LaptimeStopwatch.Instance(), c.currentState);
	  assertEquals(2, AbstractStopwatch.getTotalTime(),"value of totalTime ");
	  assertEquals(1, AbstractStopwatch.getLapTime(),"value of lapTime ");

        // When: User switches back to timer (history state should be preserved)
	  c.left(); // go back to timer mode (remembering history state)
	  c.tick();
	  assertSame(PausedTimer.Instance(), c.currentState);
	  assertEquals(2, AbstractTimer.getMemTimer(),"value of memTimer ");
	  assertEquals(1, AbstractTimer.getTimer(),"value of timer ");
	  
        // When: User resumes timer and it reaches zero
	  c.up(); // continue running timer
	  assertSame(RunningTimer.Instance(), c.currentState);
	  c.tick();
        
        // Then: Timer should be ringing
	  //automatic switch to ringing timer since timer has reached 0...
	  assertSame(RingingTimer.Instance(), c.currentState);
	  assertEquals(2, AbstractTimer.getMemTimer(),"value of memTimer ");
	  assertEquals(0, AbstractTimer.getTimer(),"value of timer ");
	  
        // When: User acknowledges the alarm
	  c.right(); // return to idle timer state
	  c.tick();
        
        // Then: Timer returns to idle state
	  assertSame(IdleTimer.Instance(), c.currentState);
	  assertEquals(2, AbstractTimer.getMemTimer(),"value of memTimer ");
	  assertEquals(0, AbstractTimer.getTimer(),"value of timer ");
	  }

}

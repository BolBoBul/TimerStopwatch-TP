package states.stopwatch;

import states.*;
import states.timer.*;

public abstract class AbstractStopwatch extends ClockState {

	//This is a composite state, so we need to defer its
	//creation to its initial substate ResetStopwatch
	public static AbstractStopwatch initialState = ResetStopwatch.Instance();
	public static AbstractStopwatch historyState;

	// totalTime and lapTime need to be static, since their values
	// need to be shared between all Stopwatch substates.
	protected static int totalTime;
	protected static int lapTime;
	
    // these getters are needed for testing purposes
	public static int getTotalTime() {return totalTime;}
	public static int getLapTime() {return lapTime;}

    public static void resetInitialValues() {
    	totalTime = 0;
    	lapTime = 0;
    }

	// use Singleton design pattern
    private static AbstractStopwatch instance = null;
    public static AbstractStopwatch Instance() {
        if (instance == null) {
        	resetInitialValues(); // initialise the values of totalTime and lapTime
        	instance = initialState;
        	}
        return instance;
    }
	
    @Override
    public ClockState left() {
       	// when leaving the Stopwatch composite state (through outgoing transition "left")
    	// we record the history (i.e., the state "this" we are currently in)
    	// before exiting the Stopwatch STM
    	historyState = this;
    	// go to the history state of the Timer STM
    	return transition(AbstractTimer.historyState);    	
    }
    
	public Mode getMode() {
	        return Mode.stopwatch;
	    }
	}

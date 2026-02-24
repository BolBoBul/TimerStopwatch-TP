package states.timer;

import states.*;
import states.stopwatch.*;

// Timer is a composite statechart
// Subclasses of Timer correspond to the states contained in the composite
public abstract class AbstractTimer extends ClockState {

	//This is a composite state, so we need to defer its
	//creation to its initial substate IdleTimer
	public static AbstractTimer initialState = IdleTimer.Instance();
	public static AbstractTimer historyState;
	
	// timer and memTimer need to be static, since their values
	// need to be shared between all Timer substates.
	protected static int timer;
	protected static int memTimer;

    // these getters are needed for testing purposes
	public static int getTimer() {return timer;}
	public static int getMemTimer() {return memTimer;}

	public static void resetInitialValues() {
    	timer = 0;
    	memTimer = 0;
    }

	// use Singleton design pattern
    private static AbstractTimer instance = null;
    public static AbstractTimer Instance() {
        if (instance == null) {
        	resetInitialValues(); // initialise the values of timer and memTimer
        	instance = initialState;
        	}
        return instance;
    }	

    @Override
    public ClockState left() {
    	// when leaving the Timer composite state (through outgoing transition "left")
    	// we record the history (i.e., the state "this" we are currently in)
    	// before exiting the Timer STM
    	historyState = this;
    	// go to the history state of the Stopwatch STM
    	return transition(AbstractStopwatch.historyState);    	
    }

    public Mode getMode() {
        return Mode.timer;
    }
   
}

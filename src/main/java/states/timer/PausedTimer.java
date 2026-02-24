package states.timer;

import states.ClockState;

public class PausedTimer extends ActiveTimer {
     
	// use Singleton design pattern
	private PausedTimer() {}; // make constructor invisible to clients
    private static PausedTimer instance = null;
    public static PausedTimer Instance() {
        if(instance == null) instance = new PausedTimer();        
        return instance;
    }

    @Override
    public ClockState up() {
        return transition(RunningTimer.Instance());
    }
    public String getUpText() { return "run"; }   
 
    public String getDisplayString() {
    	// display a fixed value here (since we are in pause mode we do not increment the timer)
        return "timer = " + timer;
    }
    
}

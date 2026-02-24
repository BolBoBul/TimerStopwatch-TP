package states.timer;

import states.ClockState;

// display decreasing values starting from memTimer counting down to 0
public class RunningTimer extends ActiveTimer {

	// use Singleton design pattern
	private RunningTimer() {}; // make constructor invisible to clients
    private static RunningTimer instance = null;
    public static RunningTimer Instance() {
        if(instance == null) instance = new RunningTimer();                
        return instance;
    }

    @Override
    public ClockState up() {
        return transition(PausedTimer.Instance());
    }
    public String getUpText() { return "pause"; }
    
    @Override
    public ClockState right() {
    	return transition(IdleTimer.Instance());
    }
    public String getRightText() { return "stop"; }
    
    @Override
    public ClockState doIt() {
    	timer--; // decrement timer after every tick
    	if (timer <= 0) { return transition(RingingTimer.Instance()); }
    	else { return this; }
    }
    
    public String getDisplayString() {
        return "timer = " + timer;
    }
    
}

package states.timer;

import states.ClockState;

public class SetTimer extends AbstractTimer {

	// use Singleton design pattern
	private SetTimer() {}; // make constructor invisible to clients
    private static SetTimer instance = null;
    static public SetTimer Instance() {
        if(instance == null) instance = new SetTimer();             
        return instance;
    }

	@Override
	public ClockState left() {
    	// "inner first" semantics!
    	// in the SetTimer state, the "left" button behaviour overrides
    	// the default behaviour specified in the Timer superclass
    	
	    memTimer = 0; // clicking on the button resets the memory to 0   
	    return this; // do not switch to a different state
	    }
    public String getLeftText() { return "reset"; }

	@Override
	public ClockState up() {
		memTimer = memTimer +5; // increase the memory with 5
		return this; // do not switch to a different state
    }
    public String getUpText() { return "inc 5"; }
	
	@Override
	public ClockState right() {
        return transition(IdleTimer.Instance());
    }
    public String getRightText() { return "done"; }
	    
    @Override
    public ClockState doIt() {
    	memTimer++; // increment memTimer every second
    	return this;
    }
    
    public String getDisplayString() {
        return "memTimer = "+ memTimer;
    }
    
}

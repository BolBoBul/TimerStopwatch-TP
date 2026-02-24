package states.timer;

import states.ClockState;

public class IdleTimer extends AbstractTimer {

	// use Singleton design pattern
	private IdleTimer() {}; // make constructor invisible to clients
	private static IdleTimer instance = null;
	public static IdleTimer Instance() {
	    if(instance == null) instance = new IdleTimer();       
	    return instance;
	    }
	
    @Override
    public ClockState up() {
    	if (memTimer > 0) {
    		timer = memTimer; 
    		return transition(ActiveTimer.Instance());
    		}
    	else { return this; }
    }
    @Override
    public String getUpText() { return "run"; }

    @Override
    public ClockState right() {
        return transition(SetTimer.Instance());
    }
    @Override
    public String getRightText() {return "set"; }
   
   public String getDisplayString() {
       return "memTimer = "+ memTimer;
   }

}

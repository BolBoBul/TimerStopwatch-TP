package states.timer;

import states.ClockState;

public class RingingTimer extends ActiveTimer {
 	
	// use Singleton design pattern
	private RingingTimer() {}; // make constructor invisible to clients
    private static RingingTimer instance = null;
    public static RingingTimer Instance() {
        if(instance == null) instance = new RingingTimer();        
        return instance;
    }
    
    @Override
    protected void entry() {
    	super.entry();
    	AbstractTimer.ringing = true;
    }
    
    @Override
    protected void exit() {
    	super.exit();
    	AbstractTimer.ringing = false;
    } 

    @Override
    public ClockState doIt() {
    	java.awt.Toolkit.getDefaultToolkit().beep();
    	return this;
    }
    
    public String getDisplayString() {
    	// display decreasing values starting from memTimer counting down to 0
        return "Time's up !";
    }
    
}

package states.stopwatch;

import states.ClockState;

public class LaptimeStopwatch extends ActiveStopwatch {
    
	private int timeout;
	
	// use Singleton design pattern
    private LaptimeStopwatch() {};
    private static LaptimeStopwatch instance = null;
    public static LaptimeStopwatch Instance() {
        if(instance == null) instance = new LaptimeStopwatch();
        return instance;
    }

   @Override
   public ClockState up() {
        return transition(RunningStopwatch.Instance());
    }
    
    public String getUpText() { return "unsplit"; }
         
    @Override
    protected void entry() {
        lapTime = totalTime;
        timeout = 5; // is used to return to RunningStopwatch state after a timeout of 5 seconds    	
    }
    
    @Override
    protected ClockState doIt() {   
    	totalTime++; // continue incrementing totalTime while displaying lapTime
        timeout--;
        if (timeout == 0) { return transition(RunningStopwatch.Instance()); }
        else { return this; }
    }
    
    public String getDisplayString() {
        return "lapTime = " + lapTime;
    }
     
}

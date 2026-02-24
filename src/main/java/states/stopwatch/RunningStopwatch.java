package states.stopwatch;

import states.ClockState;

public class RunningStopwatch extends ActiveStopwatch {
 
	// use Singleton design pattern
	private RunningStopwatch() {}; // make constructor invisible to clients
    private static RunningStopwatch instance = null;
    public static RunningStopwatch Instance() {
        if(instance == null) instance = new RunningStopwatch();                
        return instance;
    }

    @Override
    public ClockState up() {
        return transition(LaptimeStopwatch.Instance());
    }
    public String getUpText() { return "split"; }
    
    @Override
    public ClockState right() {
        return transition(ResetStopwatch.Instance());
    }
    public String getRightText() {return "reset"; }
    
    @Override
    protected ClockState doIt() {
      totalTime++;
      return this;
      }
    
    public String getDisplayString() {
        return "totalTime = " + totalTime;
    }
     
}

package states.stopwatch;

import states.ClockState;

public class ResetStopwatch extends AbstractStopwatch {

	// use Singleton design pattern
    private ResetStopwatch() {};
    private static ResetStopwatch instance = null;
    public static ResetStopwatch Instance() {
        if(instance == null) instance = new ResetStopwatch();
        return instance;
    }

    @Override
    public ClockState up() {
    	return transition(ActiveStopwatch.Instance());
    	}
	    
	public String getUpText() { return "run"; }
	    	    
	@Override
	protected void entry() {
		super.entry();
		totalTime = 0;
	    lapTime = 0;
	    }

	public String getDisplayString() {
		return "totalTime = " + totalTime;
	    }
	     
	}


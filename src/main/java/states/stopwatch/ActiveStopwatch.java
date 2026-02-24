package states.stopwatch;

public abstract class ActiveStopwatch extends AbstractStopwatch {

	//This is a composite state, so we need to defer its
	//creation to its initial substate RunningStopwatch
	
	// use Singleton design pattern
    private static ActiveStopwatch instance = null;
    public static ActiveStopwatch Instance() {
        if (instance == null) instance = RunningStopwatch.Instance();
        return instance;
    }

}

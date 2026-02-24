package states;

import states.stopwatch.AbstractStopwatch;
import states.timer.AbstractTimer;

public class Context implements EventListener {
	
    //private JLabel display;
  	    
    public ClockState currentState;
     	
    public Context() {
    	currentState = AbstractTimer.Instance(); // set initial state
        AbstractTimer.historyState = AbstractTimer.Instance();
        	// set history state of Timer composite statechart
        AbstractStopwatch.historyState = AbstractStopwatch.Instance();
    	// set history state of Stopwatch composite statechart        
    }
    
    public void left() { currentState = currentState.left(); }
    public void up() { currentState = currentState.up(); }
    public void right() { currentState = currentState.right();}
    
    public void tick() { currentState = currentState.doIt();}
   
    
    public String getDisplayText() {
    	return currentState.getDisplayString();
    	//The substring is used to remove the prefix "multiChrono.states."
    	}
    
    public String getStateText() {
    	return currentState.getClass().getSimpleName();
    	// uses Java reflection mechanism; 
    }
    
    public String getModeText() { 
    	return currentState.getMode().name();
    	}
    
    public String getLeftText() {
    	return currentState.getLeftText();
    }
    
    public String getUpText() {
    	return currentState.getUpText();
    }
    
    public String getRightText() {
    	return currentState.getRightText();
    }
}

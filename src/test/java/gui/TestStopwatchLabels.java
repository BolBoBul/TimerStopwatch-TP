package gui;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TestStopwatchLabels extends TestGUIAbstract {

    @Test
	void testStopwatch1() {
    	c.left(); //simulate clicking on the left button
    	g.updateUI(c); //apply the effect on the user interface
    	assertEquals("change mode",g.b1.getText());     
    	assertEquals("run",g.b2.getText());
    	assertEquals("(unused)",g.b3.getText());
    	assertEquals("totalTime = 0",g.myText1.getText());
    	assertEquals("stopwatch",g.myText2.getText());
    	assertEquals("ResetStopwatch",g.myText3.getText());
    }

    @Test
	void testStopwatch2() {
    	c.left(); //simulate clicking on the left button
    	c.up(); //simulate clicking on the right button
    	g.updateUI(c); //apply the effect on the user interface
    	assertEquals("change mode",g.b1.getText());     
    	assertEquals("split",g.b2.getText());
    	assertEquals("reset",g.b3.getText());
    	assertEquals("totalTime = 0",g.myText1.getText());
    	assertEquals("stopwatch",g.myText2.getText());
    	assertEquals("RunningStopwatch",g.myText3.getText());
    }

}

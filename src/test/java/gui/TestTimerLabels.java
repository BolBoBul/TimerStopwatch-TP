package gui;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.timer.*;

class TestTimerLabels extends TestGUIAbstract {

	void assertTimerLabels(AbstractTimer state) {
		c.currentState = state;
		String stateName = state.getClass().getSimpleName();
		System.out.println(stateName);
		g.updateUI(c);
		assertEquals(g.b1.getText(),c.getLeftText(),"button 1 for state " + stateName);
		assertEquals(g.b2.getText(),c.getUpText(),"button 2 for state " + stateName);
		assertEquals(g.b3.getText(),c.getRightText(),"button 3 for state " + stateName);
	}
	@Test
	void testIdleTimerLabels() {
		assertTimerLabels(IdleTimer.Instance());
		assertTimerLabels(PausedTimer.Instance());
		assertTimerLabels(RingingTimer.Instance());
		assertTimerLabels(RunningTimer.Instance());
		assertTimerLabels(SetTimer.Instance());
	}

   @Test
   void testTimerButtonLabels1() {
		g.updateUI(c);
		assertEquals("change mode",g.b1.getText());
		assertEquals("run",g.b2.getText());
		assertEquals("set",g.b3.getText());
		assertEquals("IdleTimer",g.myText3.getText());
		assertEquals("timer",g.myText2.getText());
		assertEquals("memTimer = 0",g.myText1.getText());
	};

	@Test
	void testTimerButtonLabels2() {
		c.right(); //simulate clicking on the left button
		g.updateUI(c); //apply the effect on the user interface
		assertEquals("reset",g.b1.getText());
		assertEquals("inc 5",g.b2.getText());
		assertEquals("done",g.b3.getText());
		assertEquals("SetTimer",g.myText3.getText());
		assertEquals("timer",g.myText2.getText());
		assertEquals("memTimer = 0",g.myText1.getText());
	}

	@Test
	void testStopwatchButtonLabels1() {
		c.left(); //simulate clicking on the left button
		g.updateUI(c); //apply the effect on the user interface
		assertEquals("change mode",g.b1.getText());
		assertEquals("run",g.b2.getText());
		assertEquals("(unused)",g.b3.getText());
	}

	@Test
	void testStopwatchButtonLabels2() {
		c.left(); //simulate clicking on the left button
		c.up(); //simulate clicking on the right button
		g.updateUI(c); //apply the effect on the user interface
		assertEquals("change mode",g.b1.getText());
		assertEquals("split",g.b2.getText());
		assertEquals("reset",g.b3.getText());
	}


}

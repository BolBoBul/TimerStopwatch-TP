package gui;

import org.junit.jupiter.api.BeforeEach;
import states.Context;
import states.timer.AbstractTimer;

abstract class TestGUIAbstract {

	protected Context c;
	protected HeadlessGUI g;

    @BeforeEach
	void setup() {
    	c = new Context();

		// Run the GUI tests with the HeadlessGUI to ensure that
		// the GUI tests also work with the automatic build on GitHub:
    	g = new HeadlessGUI(c);
		// Replacing HeadlessGUI by SwingGUI in the line above should still work locally,
		// but throws a Headless exception when running the tests on GitHub

    	//before each test, reset the timer values to avoid interference between tests:
    	AbstractTimer.resetInitialValues();
    }

}

package bdd;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import states.*;
import states.stopwatch.*;
import states.timer.*;

public class ChronometerStepDefinitions {

    private Context context;

    // ========== Given Steps ==========

    @Given("I have a fresh chronometer context")
    public void iHaveAFreshChronometerContext() {
        context = new Context();
    }

    @Given("the timer values are reset")
    public void theTimerValuesAreReset() {
        AbstractTimer.resetInitialValues();
    }

    @Given("the stopwatch values are reset")
    public void theStopwatchValuesAreReset() {
        AbstractStopwatch.resetInitialValues();
    }

    @Given("I am in stopwatch mode")
    public void iAmInStopwatchMode() {
        context.left(); // Switch from timer to stopwatch
    }

    @Given("the timer is set to {int}")
    public void theTimerIsSetTo(int value) {
        context.right(); // Go to SetTimer
        for (int i = 0; i < value; i++) {
            context.tick(); // Increment memTimer
        }
        context.right(); // Exit SetTimer
    }

    @Given("the timer is running")
    public void theTimerIsRunning() {
        context.up(); // Start the timer
    }

    @Given("the stopwatch is running")
    public void theStopwatchIsRunning() {
        context.up(); // Start the stopwatch
    }

    @Given("I press the up button to pause the timer")
    public void iPressTheUpButtonToPauseTheTimerGiven() {
        context.up();
    }

    @Given("I wait for {int} tick(s)")
    public void iWaitForTicksGiven(int ticks) {
        for (int i = 0; i < ticks; i++) {
            context.tick();
        }
    }

    @Given("I press the up button to record lap time")
    public void iPressTheUpButtonToRecordLapTimeGiven() {
        context.up();
    }

    @Given("I press the right button to pause stopwatch")
    public void iPressTheRightButtonToPauseStopwatchGiven() {
        context.right();
    }

    // ========== When Steps ==========

    @When("I press the right button")
    public void iPressTheRightButton() {
        context.right();
    }

    @When("I press the left button")
    public void iPressTheLeftButton() {
        context.left();
    }

    @When("I press the up button")
    public void iPressTheUpButton() {
        context.up();
    }

    @When("I wait for {int} tick(s)")
    public void iWaitForTicks(int ticks) {
        for (int i = 0; i < ticks; i++) {
            context.tick();
        }
    }

    @When("I press the up button to start the timer")
    public void iPressTheUpButtonToStartTheTimer() {
        context.up();
    }

    @When("I press the up button to pause the timer")
    public void iPressTheUpButtonToPauseTheTimer() {
        context.up();
    }

    @When("I press the up button to resume the timer")
    public void iPressTheUpButtonToResumeTheTimer() {
        context.up();
    }

    @When("I press the right button to stop the timer")
    public void iPressTheRightButtonToStopTheTimer() {
        context.right();
    }

    @When("I press the right button to acknowledge")
    public void iPressTheRightButtonToAcknowledge() {
        context.right();
    }

    @When("I press the left button to switch to stopwatch")
    public void iPressTheLeftButtonToSwitchToStopwatch() {
        context.left();
    }

    @When("I press the left button to switch back to timer")
    public void iPressTheLeftButtonToSwitchBackToTimer() {
        context.left();
    }

    @When("I press the up button to start the stopwatch")
    public void iPressTheUpButtonToStartTheStopwatch() {
        context.up();
    }

    @When("I press the up button to record lap time")
    public void iPressTheUpButtonToRecordLapTime() {
        context.up();
    }

    @When("I press the up button to clear lap time")
    public void iPressTheUpButtonToClearLapTime() {
        context.up();
    }

    @When("I press the right button to pause stopwatch")
    public void iPressTheRightButtonToPauseStopwatch() {
        context.right();
    }

    @When("I press the right button to reset stopwatch")
    public void iPressTheRightButtonToResetStopwatch() {
        context.right();
    }

    @When("I press the right button to resume stopwatch")
    public void iPressTheRightButtonToResumeStopwatch() {
        context.right();
    }

    @When("I press the up button to reset stopwatch")
    public void iPressTheUpButtonToResetStopwatch() {
        context.up();
    }

    @When("I press the left button to switch to timer")
    public void iPressTheLeftButtonToSwitchToTimer() {
        context.left();
    }

    @When("I press the left button to switch back to stopwatch")
    public void iPressTheLeftButtonToSwitchBackToStopwatch() {
        context.left();
    }

    // ========== Then Steps ==========

    @Then("the current state should be {string}")
    public void theCurrentStateShouldBe(String expectedState) {
        String actualState = context.currentState.getClass().getSimpleName();
        assertEquals(expectedState, actualState,
            "Expected state '" + expectedState + "' but was '" + actualState + "'");
    }

    @Then("the timer value should be {int}")
    public void theTimerValueShouldBe(int expectedValue) {
        assertEquals(expectedValue, AbstractTimer.getTimer(),
            "Timer value mismatch");
    }

    @Then("the memTimer value should be {int}")
    public void theMemTimerValueShouldBe(int expectedValue) {
        assertEquals(expectedValue, AbstractTimer.getMemTimer(),
            "MemTimer value mismatch");
    }

    @Then("the mode should be {string}")
    public void theModeShouldBe(String expectedMode) {
        String actualMode = context.currentState.getMode().name();
        assertEquals(expectedMode, actualMode,
            "Mode mismatch");
    }

    @Then("the totalTime value should be {int}")
    public void theTotalTimeValueShouldBe(int expectedValue) {
        assertEquals(expectedValue, AbstractStopwatch.getTotalTime(),
            "TotalTime value mismatch");
    }

    @Then("the lapTime value should be {int}")
    public void theLapTimeValueShouldBe(int expectedValue) {
        assertEquals(expectedValue, AbstractStopwatch.getLapTime(),
            "LapTime value mismatch");
    }
}

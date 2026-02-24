Feature: Stopwatch functionality
  As a user of the chronometer application
  I want to use the stopwatch feature
  So that I can measure elapsed time

  Background:
    Given I have a fresh chronometer context
    And the stopwatch values are reset
    And I am in stopwatch mode

  Scenario: Initial stopwatch state
    Then the current state should be "ResetStopwatch"
    And the totalTime value should be 0
    And the lapTime value should be 0
    And the mode should be "stopwatch"

  Scenario: Start stopwatch
    When I press the up button to start the stopwatch
    Then the current state should be "RunningStopwatch"
    When I wait for 3 ticks
    Then the totalTime value should be 3
    And the lapTime value should be 0

  Scenario: Record lap time
    Given the stopwatch is running
    And I wait for 2 ticks
    When I press the up button to record lap time
    Then the current state should be "LaptimeStopwatch"
    And the lapTime value should be 2

  Scenario: Continue after lap time recording
    Given the stopwatch is running
    And I wait for 2 ticks
    And I press the up button to record lap time
    When I wait for 3 ticks
    Then the totalTime value should be 5
    And the lapTime value should be 5

  Scenario: Clear lap time and continue
    Given the stopwatch is running
    And I wait for 2 ticks
    And I press the up button to record lap time
    When I press the up button to clear lap time
    Then the current state should be "RunningStopwatch"
    And the lapTime value should be 0

  Scenario: Reset running stopwatch
    Given the stopwatch is running
    And I wait for 3 ticks
    When I press the right button to reset stopwatch
    Then the current state should be "ResetStopwatch"
    And the totalTime value should be 0
    And the lapTime value should be 0

  Scenario: Switch to timer mode preserves stopwatch history
    Given the stopwatch is running
    And I wait for 3 ticks
    When I press the left button to switch to timer
    Then the mode should be "timer"
    When I press the left button to switch back to stopwatch
    Then the mode should be "stopwatch"
    And the current state should be "RunningStopwatch"

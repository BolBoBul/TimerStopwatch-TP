Feature: Timer functionality
  As a user of the chronometer application
  I want to use the timer feature
  So that I can count down from a set time

  Background:
    Given I have a fresh chronometer context
    And the timer values are reset

  Scenario: Initial timer state
    Then the current state should be "IdleTimer"
    And the timer value should be 0
    And the memTimer value should be 0
    And the mode should be "timer"

  Scenario: Set timer value
    When I press the right button
    Then the current state should be "SetTimer"
    When I wait for 3 ticks
    Then the memTimer value should be 3
    And the timer value should be 0

  Scenario: Start timer countdown
    Given the timer is set to 5
    When I press the up button to start the timer
    Then the current state should be "RunningTimer"
    And the timer value should be 5
    When I wait for 2 ticks
    Then the timer value should be 3

  Scenario: Pause running timer
    Given the timer is set to 5
    And the timer is running
    When I press the up button to pause the timer
    Then the current state should be "PausedTimer"
    When I wait for 1 tick
    Then the timer value should be 5

  Scenario: Resume paused timer
    Given the timer is set to 5
    And the timer is running
    And I press the up button to pause the timer
    When I press the up button to resume the timer
    Then the current state should be "RunningTimer"

  Scenario: Stop timer and return to idle
    Given the timer is set to 5
    And the timer is running
    When I press the right button to stop the timer
    Then the current state should be "IdleTimer"

  Scenario: Timer rings when countdown reaches zero
    Given the timer is set to 2
    And the timer is running
    When I wait for 2 ticks
    Then the current state should be "RingingTimer"
    And the timer value should be 0

  Scenario: Acknowledge ringing timer
    Given the timer is set to 1
    And the timer is running
    When I wait for 1 tick
    Then the current state should be "RingingTimer"
    When I press the right button to acknowledge
    Then the current state should be "IdleTimer"

  Scenario: Switch to stopwatch mode preserves timer history
    Given the timer is set to 5
    And the timer is running
    When I press the left button to switch to stopwatch
    Then the mode should be "stopwatch"
    And the current state should be "ResetStopwatch"
    When I press the left button to switch back to timer
    Then the mode should be "timer"
    And the current state should be "RunningTimer"

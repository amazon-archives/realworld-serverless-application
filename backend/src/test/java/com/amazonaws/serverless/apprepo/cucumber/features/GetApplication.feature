Feature: GetApplication
  As a user, I would like to get the details of my application.

  Scenario: User gets an application
    Given a user has an application
    When the user gets the application
    Then the application should be returned

  Scenario: User gets a non-existent application
    When a user gets a non-existent application
    Then the call should fail because the application does not exist

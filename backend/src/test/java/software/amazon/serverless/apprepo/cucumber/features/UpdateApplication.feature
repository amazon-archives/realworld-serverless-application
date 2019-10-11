Feature: UpdateApplication
  As a user, I would like to update my application.

  Scenario: User updates an application
    Given a user has an application
    When the user updates the application
    Then the application should be updated

  Scenario: User updates an application with no update
    Given a user has an application
    When the user updates the application with no update
    Then the call should fail because there is no update

  Scenario: User updates a non-existent application
    When a user updates a non-existent application
    Then the call should fail because the application does not exist

  Scenario: User updates an application with invalid author
    Given a user has an application
    When the user updates the application with invalid author
    Then the call should fail because of bad request

  Scenario: User updates an application with invalid description
    Given a user has an application
    When the user updates the application with invalid description
    Then the call should fail because of bad request

  Scenario: User updates an application with invalid home page URL
    Given a user has an application
    When the user updates the application with invalid home page URL
    Then the call should fail because of bad request
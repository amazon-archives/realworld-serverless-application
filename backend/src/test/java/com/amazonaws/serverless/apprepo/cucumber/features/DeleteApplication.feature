Feature: DeleteApplication
  As a user, I would like to delete my application.

  Scenario: User deletes an application
    Given a user has an application
    When the user deletes the application
    Then the application should be deleted
    And the application should no longer be listed

  Scenario: User deletes a non-existent application
    When a user deletes a non-existent application
    Then the call should fail because the application does not exist

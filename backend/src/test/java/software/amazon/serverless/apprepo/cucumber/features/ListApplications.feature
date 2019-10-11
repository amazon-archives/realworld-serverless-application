Feature: ListApplications
  As a user, I would like to see what applications I have.

  Scenario: User lists applications
    Given a user has an application
    And the user creates another application
    When the user lists applications
    Then all applications should be listed
    And the listed applications should be in alphabetical order

  Scenario: User lists applications with pagination
    Given a user has an application
    And the user creates another application
    And the user creates another application
    When the user lists applications with 2 max items
    Then 2 applications should be listed
    And the user lists applications with next token
    Then 1 applications should be listed

  Scenario: Unauthorized user lists applications
    When an unauthorized user lists applications
    Then the call should fail because of access denied
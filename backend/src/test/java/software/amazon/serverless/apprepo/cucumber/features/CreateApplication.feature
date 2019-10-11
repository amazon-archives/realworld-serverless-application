Feature: CreateApplication
  As a user, I would like to create an application.

  Scenario: User creates an application
    When a user creates an application
    Then a new application should be created

  Scenario: User creates an application with the same id
    Given a user has an application
    When the user creates an application with the same id
    Then the call should fail because the application already exists

  Scenario: User creates an application without application id
    When a user creates an application without application id
    Then the call should fail because of bad request

  Scenario: User creates an application without author
    When a user creates an application without author
    Then the call should fail because of bad request

  Scenario: User creates an application without description
    When a user creates an application without description
    Then the call should fail because of bad request

  Scenario: User creates an application with invalid application id
    When a user creates an application with invalid application id
    Then the call should fail because of bad request

  Scenario: User creates an application with invalid author
    When a user creates an application with invalid author
    Then the call should fail because of bad request

  Scenario: User creates an application with invalid description
    When a user creates an application with invalid description
    Then the call should fail because of bad request

  Scenario: User creates an application with invalid home page URL
    When a user creates an application with invalid home page URL
    Then the call should fail because of bad request
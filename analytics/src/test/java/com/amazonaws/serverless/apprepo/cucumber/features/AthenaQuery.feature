Feature: CreateApplication
  As a user, I would like to create an application.

  Scenario: User creates an application
    When a user creates an application
    Then a new application should be created
    And the administrator can analyze the data with Athena queries

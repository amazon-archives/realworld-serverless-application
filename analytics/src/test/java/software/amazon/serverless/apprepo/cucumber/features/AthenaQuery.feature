Feature: Analyze Data
  As an administrator, I would like to analyze the customer usage of the service

  Scenario: User creates an application
    When a user creates an application
    Then a new application should be created
    And the administrator can analyze the data with Athena queries

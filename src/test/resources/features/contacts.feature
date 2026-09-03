Feature: Contact list management
  As a user of the Contact List app
  I want to add and view contacts
  So that I can keep track of people's names

  Background:
    Given I am on the contacts page

  Scenario: Add a valid contact
    When I add a contact with first name "Ada" and last name "Lovelace"
    Then the contact "Ada Lovelace" should appear in the contact list

  Scenario: First and last name are required
    When I add a contact with first name "" and last name ""
    Then the contact list should not gain a new entry

  Scenario Outline: Reject names containing special characters
    When I add a contact with first name "<firstName>" and last name "<lastName>"
    Then an error message should be displayed

    Examples:
      | firstName | lastName  |
      | %%%%%%    | 1234444   |
      | John123   | Doe       |
      | Jane      | Doe!      |

  Scenario: Remove an existing contact
    Given a contact "Grace Hopper" already exists
    When I remove the contact "Grace Hopper"
    Then the contact "Grace Hopper" should not appear in the contact list

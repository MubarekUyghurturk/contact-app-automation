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

  Scenario Outline: Accept names written in non-English languages
    When I add a contact with first name "<firstName>" and last name "<lastName>"
    Then the contact "<firstName> <lastName>" should appear in the contact list
    And no error message should be displayed

    Examples:
      | firstName  | lastName       | language              |
      | مبارك      | ئويغۇرتۈرك     | Uyghur/Arabic          |
      | 李          | 伟             | Chinese                |
      | Иван       | Петров         | Cyrillic               |
      | José       | Muñoz          | Accented Latin         |
      | राजेश      | शर्मा          | Hindi/Devanagari (combining marks) |
      | สวัสดี     | ประเทศไทย      | Thai (combining marks) |
      | 田中        | 太郎           | Japanese               |
      | 김          | 민준           | Korean                 |
      | Γιώργος    | Παπαδόπουλος   | Greek                  |
      | אברהם      | כהן            | Hebrew                 |
      | Nguyễn     | Văn An         | Vietnamese              |
      | Müller     | Öztürk         | German/Turkish umlauts |

  Scenario Outline: Reject names that mix digits into a non-English script
    When I add a contact with first name "<firstName>" and last name "<lastName>"
    Then an error message should be displayed

    Examples:
      | firstName | lastName |
      | राजेश1    | शर्मा    |
      | 李2        | 伟       |

  Scenario: Remove an existing contact
    Given a contact "Grace Hopper" already exists
    When I remove the contact "Grace Hopper"
    Then the contact "Grace Hopper" should not appear in the contact list

# Contact App Automation

Selenium + Cucumber BDD test automation framework for the [Contact List web application](https://github.com/MubarekUyghurturk/AI-project).

## Stack

- Java 11
- Maven
- Selenium WebDriver 4
- WebDriverManager (automatic browser driver management)
- Cucumber BDD (Gherkin `.feature` files)
- Page Object Model
- JUnit 4 (Cucumber runner)
- Automatic screenshot capture on scenario failure (attached to the Cucumber HTML report and saved under `target/screenshots/`)

## Project structure

```
src/test/java/com/contactapp/automation/
  pages/            Page Object classes
  stepdefinitions/  Cucumber step definitions
  hooks/            @Before/@After hooks, screenshot-on-failure
  runners/          Cucumber JUnit runner
  utils/            WebDriver factory, config reader
src/test/resources/features/
  contacts.feature  Gherkin scenarios
```

## Prerequisites

- Java 11+
- Maven 3.6+
- Google Chrome installed
- The Contact List app running locally (default: `http://localhost:3000`)

## Running the tests

```bash
mvn test
```

Override the app URL or run headless:

```bash
mvn test -DbaseUrl=http://localhost:3000 -Dheadless=true
```

## Reports & screenshots

- HTML report: `target/cucumber-reports/report.html`
- JSON report: `target/cucumber-reports/report.json`
- Failure screenshots: `target/screenshots/`

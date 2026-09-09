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

- Plain Cucumber HTML report: `target/cucumber-reports/report.html`
- JSON report: `target/cucumber-reports/report.json`
- Failure screenshots: `target/screenshots/`

### Rich HTML report

For a polished, standalone HTML report (pass/fail charts, per-feature and
per-step breakdown, embedded failure screenshots), generate it from the
JSON output as a separate step after the tests run:

```bash
mvn test
mvn net.masterthought:maven-cucumber-reporting:generate
```

It's a separate command on purpose: `mvn test` fails fast on scenario
failures (so CI can gate on its exit code), which would stop a
phase-bound plugin from ever running — exactly when a report is most
useful. Running it as its own step means it's generated regardless of
whether the tests passed.

Open `target/cucumber-html-reports/overview-features.html`. Pass
`-Dreport.buildNumber=<id>` (e.g. `$BUILD_NUMBER` in CI) to label the
report with a build identifier.

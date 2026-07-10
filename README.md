# App Utility Methods - JUnit 5 Unit Testing

This repository contains a small Java utility class and a JUnit 5 test suite created for Home Assignment 2 in Development Tools.

## Team members

- Nadav Maharer
- Alice Raines
- Tomer Lanker

## Prerequisites

- Git
- Java 21

The repository includes the Gradle Wrapper, so a separate Gradle installation is not required.

## Clone and enter the project

```bash
git clone https://github.com/Afeka-DevTools/26b-10142-unittesting-nadav_maharer_alice_raines_tomar_lanker.git
cd 26b-10142-unittesting-nadav_maharer_alice_raines_tomar_lanker
```

## Run all tests

### Windows

```powershell
.\gradlew.bat clean test
```

### Linux or macOS

```bash
./gradlew clean test
```

The first run may download the Gradle distribution and project dependencies. A successful run ends with `BUILD SUCCESSFUL`.

## Reports

After running the tests, open these files in a web browser:

- JUnit HTML report: `app/build/reports/tests/test/index.html`
- JaCoCo HTML coverage report: `app/build/reports/jacoco/test/html/index.html`

In the JaCoCo report, select `org.example`, then `App`, to inspect coverage for each utility method.

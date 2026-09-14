# Community Garden

CAB302 group project: a JavaFX desktop app for managing community gardens and member accounts. Built with Java 21, Maven and SQLite. Still in development.

## Getting started

1. Open the folder containing `pom.xml` in IntelliJ as a Maven project.
2. Set the project SDK and Maven runner JDK to Java 21.
3. Reload Maven to download the dependencies.
4. In the Maven panel, run `Plugins → javafx → javafx:run` to start the app.

You can also run it from the project folder in a terminal:

```sh
# Windows PowerShell
.\mvnw.cmd javafx:run

# Git Bash, macOS or Linux
sh ./mvnw javafx:run
```

For terminal commands, set `JAVA_HOME` to your JDK 21 installation folder.

## Tests

In IntelliJ, right-click `src/test/java` and select **Run All Tests**.
To run through Maven, use `Lifecycle → test` in the Maven panel, or:

```sh
# Windows PowerShell
.\mvnw.cmd clean test

# Git Bash, macOS or Linux
sh ./mvnw clean test
```

Keep new tests under `src/test/java` in the matching package, with class names ending in `Test`. GitHub Actions also runs the Maven tests on pushes and pull requests.

## Project structure

- `src/main/java` — application code
- `src/main/resources` — FXML views, styles and images
- `src/test/java` — tests
- `pom.xml` — dependencies and build configuration

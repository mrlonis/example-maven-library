# Repository guide

## Java compatibility

Write all production and test code using Java 8 syntax and Java 8 APIs. Use Java 8-compatible dependencies for the library and its tests. Build and test with JDK 8, as CI does using `.java-version`.

Run Spotless with JDK 17 or newer. The `spotless` Maven profile sets the minimum JDK for the formatter. Keep the project's `java.version` and compiler target at `1.8`.

## Format and verify

Run these commands from the repository root. Point `JAVA_HOME` at the JDK for each step. Use `./mvnw -version` to see which Java runtime Maven picked up.

1. With JDK 17 or newer, format all changes using the repository's Spotless configuration. Have Node.js and npm on `PATH` for its Prettier steps.

   ```sh
   ./mvnw enforcer:enforce spotless:apply -P spotless
   ```
2. Switch back to JDK 8 and run the full build and test suite.

   ```sh
   ./mvnw clean verify
   ```

To check formatting without editing files, run `./mvnw enforcer:enforce spotless:check -P spotless` on JDK 17 or newer. In IntelliJ, select that JDK for the `mvn spotless:apply` run configuration and JDK 8 for `mvn clean verify`.

## JSON and XML tests

Treat Jackson property names, ordering, null handling, and timestamp representations as part of the library's public contract. When changing a DTO, exercise both JSON and XML through the shared `BaseJsonTests` and `BaseXmlTests` fixtures in `src/test/java/com/mrlonis/test`. Their baseline and customized mappers use the same fixtures to catch accidental dependence on mapper settings.

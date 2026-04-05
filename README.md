# Explainable Self-Healing Automation Framework

A Maven + Selenium + Java + TestNG framework that not only tries fallback locators when the primary locator fails, but also explains why the failure likely happened and what permanent fix is recommended.

## Included in this MVP
- Selenium Java framework structure
- WebDriverManager for automatic browser driver setup
- Multi-locator healing engine
- Explanation engine for root-cause-style messages
- Screenshot capture on healing and failure
- Extent report integration
- Demo login test for ParaBank

## Project Structure

```text
explainable-self-healing-framework/
├── pom.xml
├── testng.xml
├── src/
│   ├── main/java/com/prashant/framework/
│   │   ├── base/
│   │   ├── core/
│   │   ├── listeners/
│   │   ├── models/
│   │   ├── pages/
│   │   └── utils/
│   ├── test/java/com/prashant/framework/tests/
│   └── test/resources/config.properties
├── reports/
└── screenshots/
```

## How the healing works
1. Framework tries the primary locator.
2. If it fails, it tries fallback locators one by one.
3. If any fallback works, it logs:
   - what failed
   - which locator recovered the step
   - probable reason
   - recommendation for permanent fix
4. If all fail, it captures screenshot and throws clear failure.

## Run the project
1. Open in IntelliJ as Maven project.
2. Let Maven download dependencies.
3. Run:
   - `testng.xml`, or
   - `mvn clean test`

## Important note
The provided username and password are demo values from the current working conversation context. If ParaBank resets or invalidates them, update `config.properties` with your own valid demo account.

## Next improvements you can add
- Read locator bundles from JSON or properties
- Excel-driven test data using Apache POI
- Allure reporting
- Retry analyzer
- CI/CD with GitHub Actions
- Healed locator persistence

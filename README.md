# 🚀 Explainable Self-Healing Automation Framework

A Maven + Selenium + Java + TestNG framework designed to reduce flaky test failures caused by broken locators.

Unlike a traditional Selenium framework, this project not only tries fallback locators when the primary locator fails, but also explains:

* Why the failure likely happened
* How the step was recovered
* What permanent fix is recommended

---

## 💡 Why this project matters

In real-world automation, most test failures are not actual product bugs — they occur due to:

* Locator changes
* Dynamic UI updates
* Synchronization issues

This framework improves automation reliability by introducing:

* Multi-locator fallback strategy
* Failure explanation engine
* Screenshot-based debugging
* Smart reporting

---

## 🔥 Key Features

* ✅ Multi-locator strategy (Primary + Fallback)
* ✅ Automatic self-healing on locator failure
* ✅ Explainable failure analysis
* ✅ Screenshot capture on failure and healing
* ✅ Extent Report integration
* ✅ Clean Page Object Model structure

---

## 🏗️ Tech Stack

* Java
* Selenium WebDriver
* TestNG
* WebDriverManager
* Extent Reports

---

## 📂 Project Structure

```text
explainable-self-healing-framework/
├── pom.xml
├── testng.xml
├── .gitignore
├── README.md
├── assets/
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

---

## ⚙️ How the Self-Healing Works

1. Framework first tries the **primary locator**

2. If it fails, it automatically switches to **fallback locators**

3. When a fallback succeeds, the framework logs:

   * Failed locator
   * Successful fallback locator
   * Probable reason (DOM change / sync issue)
   * Recommendation for permanent fix

4. If all locators fail:

   * Screenshot is captured
   * Clear failure message is generated

---

## 🧠 Explainable Logic

This framework does not just heal — it explains.

Example output:

```
Primary locator failed: id=loginBtn  
Fallback used: xpath=//button[text()='Login']  

Reason: Possible DOM change  
Recommendation: Update locator to a stable selector  
```

---

## 🧪 Demo Scenario

To simulate a real-world failure:

* The primary locator is intentionally modified
* The framework attempts recovery using fallback locators
* Healing process is logged and reported

This demonstrates how the framework handles locator changes without breaking tests.

---

## ▶️ How to Run

1. Clone the repository
2. Open in IntelliJ as a Maven project
3. Set Project SDK (Java 11 or 17)
4. Let Maven download dependencies

Run using:

```
testng.xml
```

OR

```
mvn clean test
```

---

## 📸 Reports & Screenshots

* Reports generated in: `/reports`
* Screenshots stored in: `/screenshots`

(Add demo images in `/assets` for better visualization)

---

## ⚠️ Important Note

The provided credentials are demo values.
If ParaBank resets them, update `config.properties` with valid credentials.

---

## 🚧 Limitations

* Cannot handle complete UI redesign
* Cannot fix business logic failures
* Risk of false positives if validation is weak

---

## 🚀 Future Enhancements

* JSON-based locator storage
* Excel-driven test data (Apache POI)
* Allure reporting
* Retry analyzer
* CI/CD integration (GitHub Actions)
* Healed locator persistence

---

## 👨‍💻 Author

**Prashant Kumar**

---

## ⭐ Final Note

This project focuses on making automation frameworks smarter by combining:

👉 Resilience (Self-Healing)
👉 Intelligence (Explainable Failures)

to reduce maintenance effort and improve test stability.

# QA UI Automation Challenge

End-to-end test automation framework for Sauce Demo e-commerce platform using Playwright and Java.

## 🎯 Overview

This project automates critical user interface flows for the Sauce Demo website to ensure quality and system stability. Built following industry best practices with the Page Object Model design pattern.

## 🛠️ Tech Stack

- **Language:** Java 17
- **Automation Framework:** Playwright 1.59.0
- **Test Framework:** JUnit 5.14.4
- **Build Tool:** Maven 3.8+
- **Reporting:** Allure 2.28.0
- **Design Pattern:** Page Object Model (POM)

## 📋 Prerequisites

- Java 17+ installed
- Maven 3.8+ installed
- Git installed

## 🚀 How to Run

```bash
# Install dependencies
mvn clean install

# Run all tests
mvn test

# Run tests with visible browser (debug mode)
mvn test -Dheadless=false

# Run specific test
mvn test -Dtest=E2E01_HomeNavigationTest

# Generate Allure report
mvn allure:serve
```

## 📁 Project Structure

src/
├── main/java/com/saucedemo/
│   ├── base/BasePage.java                 # Common page methods
│   ├── config/
│   │   ├── PlaywrightFactory.java         # Browser management
│   │   └── TestConfig.java                # Configuration
│   └── pages/
│       ├── HomePage.java                  # Home page objects
│       ├── ProductPage.java               # Product page objects
│       ├── CartPage.java                  # Cart drawer objects
│       └── CheckoutPage.java              # Checkout form objects
│
└── test/java/com/saucedemo/
├── base/BaseTest.java                 # Test setup/teardown
└── tests/
├── E2E01_HomePageTest.java        # Home page tests (4 scenarios)
├── E2E02_ProductPageTest.java     # Product tests (4 scenarios)
├── E2E03_CartManagementTest.java  # Cart tests (4 scenarios)
└── E2E04_CheckoutFlowTest.java    # Checkout tests (4 scenarios)
## ✅ Status

🚧 In development

## 👤 Author

Luidson Lucas Bortolatto

---

_Developed as part of Novigi Solution technical assessment_

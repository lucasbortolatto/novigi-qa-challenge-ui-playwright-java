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
├── main/java/          # Framework code (Page Objects, Utils, Config)
└── test/java/          # E2E tests

## ✅ Status

🚧 In development

## 👤 Author

Luidson Lucas Bortolatto

---

_Developed as part of Novigi Solution technical assessment_

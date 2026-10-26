# Library Management System - Improved Edition (Weekend Challenge Task 2)

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0%2B-blue.svg)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/Gradle-9.3.1-brightgreen.svg)](https://gradle.org)
[![Build Status](https://img.shields.io/badge/Tests-Passing-success.svg)]()

> **Course**: Mobile Application Development / Kotlin Fundamentals  
> **Student**: Habtamu Shewamene Haile ([@HabtamuShewamene](https://github.com/HabtamuShewamene))  
> **Original Base Project**: [Debre-birhan-university/kotlin__gradle_project](https://github.com/Debre-birhan-university/kotlin__gradle_project)  
> **Selected Student Fork**: [awokedersie/kotlin__gradle_project](https://github.com/awokedersie/kotlin__gradle_project) by Awoke Derssie  
> **Challenge Specification Credit**: Anthropic, Claude AI (2026) for prompt write-up  

---

## 📖 1. Project Purpose & Objectives

This project is an enhanced **Library Management System** implemented in Kotlin and built using Gradle. As part of **Task 2 of the Weekend Challenge ("Build, Compare, and Improve a Project")**, this repository takes the previous student solution created by **Awoke Derssie ([@awokedersie](https://github.com/awokedersie))**, analyzes its design decisions, resolves critical limitations, and implements significant architectural and functional improvements.

### Core Objectives:
* Master Kotlin object-oriented patterns (data classes, interfaces, companion objects, extension functions).
* Understand Gradle toolchains and multi-project build lifecycles.
* Practice rigorous software engineering: code evaluation, regression testing, input sanitization, and metric analysis.

---

## 🛠️ 2. Original Features vs. Implemented Improvements

| Feature Area | Original Solution (`awokedersie`) | Improved Edition (`HabtamuShewamene`) |
| :--- | :--- | :--- |
| **Borrow Limit** | Hardcoded to **3 books** (`size >= 3`) | Corrected to **5 books** (`MAX_BORROW_LIMIT = 5`) matching the official course specification |
| **Input Validation** | None (empty strings and negative years accepted) | Strict input validation on ISBN, title, author, patron ID, and publication years |
| **Patron View** | Basic listing | Enriched output displaying total count and comma-separated borrowed book lists |
| **Search Function** | Simple case-insensitive match | Trimmed whitespace handling, structured numbered results, and catalog index tracking |
| **Library Health** | None | Added **Option 8: Library Health & Analytics** (utilization rate, active borrowers count, book status breakdown) |
| **Error Feedback** | Generic error messages | Contextual error diagnostics with actionable guidance |
| **Testing** | 0 unit tests | Automated JUnit 5 test suite verifying validation, limits, returns, and analytics |

---

## 🚀 3. How Improvements Benefit Users

1. **Policy Compliance**: Restoring the borrowing limit to 5 books allows patrons full access according to the library's official guidelines.
2. **Data Integrity**: Input sanitization prevents corrupted or blank records from polluting the in-memory catalog.
3. **Operational Visibility**: The new **Library Analytics** feature gives administrators real-time insights into inventory turnover and utilization.
4. **Error Transparency**: Clear error feedback prevents user confusion when borrowing limits or missing items occur.

---

## 💻 4. Setup & Execution Instructions

### Prerequisites
* Java Development Kit (JDK 21 or higher)
* Gradle wrapper (included in repository)

### Build the Project
```bash
# Build and run automated tests
./gradlew build

# Run unit tests specifically
./gradlew test
```

### Run the Application
```bash
# Option 1: Run directly via Gradle
./gradlew run

# Option 2: Execute compiled JAR
java -jar app/build/libs/app.jar
```

---

## 🖥️ 5. Sample Console Execution

```text
================ LIBRARY MENU [Improved Edition] ================
 1 | Add Book
 2 | Register Patron
 3 | Borrow Book
 4 | Return Book
 5 | Search
 6 | List All Books
 7 | View All Patrons
 8 | Library Statistics & Health
 0 | Exit
==============================================
 Enter option: 8
--- 📊 Library Health & Analytics ---
 Total Catalog Books:   3
 Available Books:       2
 Currently Borrowed:    1
 Total Registered Users:1
 Active Borrowers:      1
 Utilization Rate:      33.3%
---------------------------------------
```

---

## 📜 Academic Integrity & Attribution
* **Base Upstream Project**: [Debre-birhan-university/kotlin__gradle_project](https://github.com/Debre-birhan-university/kotlin__gradle_project)
* **Analyzed Student Fork**: [awokedersie/kotlin__gradle_project](https://github.com/awokedersie/kotlin__gradle_project)
* **Challenge Write-Up Credit**: Anthropic, Claude AI (2026)

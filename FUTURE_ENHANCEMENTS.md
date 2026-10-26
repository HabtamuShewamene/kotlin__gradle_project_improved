# Future Enhancements Roadmap: Library Management System

This document provides a forward-looking engineering roadmap for upcoming students, contributors, and maintainers of the **Kotlin Library Management System**.

---

## 1. Features That Are Currently Missing

* **Persistent Storage**:
  * *Current State*: All catalog data and patron records exist strictly in volatile RAM (`LinkedHashMap`). Stopping the application destroys all newly added books and checkout histories.
  * *Recommended Enhancement*: Integrate JSON serialization (using `kotlinx.serialization` or `Gson`), SQLite, or Room database persistence to retain state across sessions.
* **Due Dates & Overdue Fines**:
  * *Current State*: Books can be borrowed indefinitely without due dates or penalty calculations.
  * *Recommended Enhancement*: Introduce `borrowDate: LocalDate` and `dueDate: LocalDate` (defaulting to 14 days), with calculated overdue fines ($0.50/day).
* **Multi-Copy Inventory Tracking**:
  * *Current State*: Each book is represented by a single ISBN copy. If a library owns 5 copies of *Clean Code*, each currently requires a fake unique ISBN.
  * *Recommended Enhancement*: Refactor `Book` to separate metadata (`title`, `author`, `isbn`) from individual inventory items (`copyId`, `status: Available | Borrowed | Damaged | Lost`).

---

## 2. Remaining Technical Limitations

* **Single-Node In-Memory Storage**:
  * Concurrent CLI sessions or parallel test execution may cause race conditions if `LinkedHashMap` is not synchronized.
  * *Fix*: Migrate to `ConcurrentHashMap` or a thread-safe repository abstraction.
* **Console UI Scalability**:
  * Scrolling through hundreds of books in a terminal causes terminal buffer truncation.
  * *Fix*: Implement pagination (e.g. 10 items per page with `[N]ext / [P]revious` controls).
* **Terminal Input Resilience**:
  * Direct standard input (`readln()`) is susceptible to EOF termination issues in piped environments or CI/CD pipelines.

---

## 3. Possible Usability, Performance & Maintainability Improvements

| Area | Opportunity | Concrete Implementation Idea |
| :--- | :--- | :--- |
| **Search Engine** | Fuzzy String Matching | Replace strict `.contains()` with Levenshtein distance or soundex algorithms to tolerate spelling mistakes. |
| **Architecture** | Dependency Injection | Introduce Koin or Dagger/Hilt to decouple repository creation from `ConsoleApp.kt`. |
| **Reporting** | CSV / PDF Export | Add an option to export monthly borrowing activity or inventory reports to CSV. |
| **UX Polish** | Terminal Colors | Use ANSI escape sequences (e.g., green for available, red for borrowed) to heighten visual hierarchy. |

---

## 4. Innovative Project Ideas for Future Students

1. **REST API Microservice**:
   * Wrap the core `LibraryService` using Ktor or Spring Boot to expose REST endpoints (`GET /api/books`, `POST /api/borrow`).
2. **Android Companion App**:
   * Build a Jetpack Compose mobile interface that connects to this Kotlin backend, utilizing the same domain models.
3. **Barcode / QR Code Scanner**:
   * Generate QR codes for patrons and books for automated kiosk checkout.

---

## 5. Suggested Milestones & Student Challenges

* [ ] **Milestone 1**: Implement file persistence using `kotlinx.serialization` to save and load `library_data.json` on app startup and shutdown.
* [ ] **Milestone 2**: Add patron registration with email validation and phone number formatting.
* [ ] **Milestone 3**: Implement an automated reminder system that flags accounts with books borrowed longer than 14 days.
* [ ] **Milestone 4**: Write integration tests using MockK to verify that repository errors trigger proper domain exceptions.
* [ ] **Milestone 5**: Transition from terminal CLI to a desktop UI using Compose Multiplatform.

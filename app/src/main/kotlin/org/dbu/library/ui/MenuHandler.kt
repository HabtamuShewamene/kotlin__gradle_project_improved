package org.dbu.library.ui

import org.dbu.library.model.Book
import org.dbu.library.model.Patron
import org.dbu.library.repository.LibraryRepository
import org.dbu.library.service.BorrowResult
import org.dbu.library.service.LibraryService

fun handleMenuAction(
    choice: String,
    service: LibraryService,
    repository: LibraryRepository
): Boolean {

    return when (choice) {

        "1" -> {
            addBook(service)
            true
        }

        "2" -> {
            registerPatron(repository)
            true
        }

        "3" -> {
            borrowBook(service)
            true
        }

        "4" -> {
            returnBook(service)
            true
        }

        "5" -> {
            search(service)
            true
        }

        "6" -> {
            listAllBooks(repository)
            true
        }

        "7" -> {
            listAllPatrons(repository)
            true
        }

        "8" -> {
            displayLibraryStats(service)
            true
        }

        "0" -> false

        else -> {
            println("Invalid option. Please enter a valid menu number (0-8).")
            true
        }
    }
}

private fun addBook(service: LibraryService) {
    print("ISBN: ")
    val isbn = readln().trim()
    if (isbn.isEmpty()) {
        println("Error: ISBN cannot be empty.")
        return
    }

    print("Title: ")
    val title = readln().trim()
    if (title.isEmpty()) {
        println("Error: Title cannot be empty.")
        return
    }

    print("Author: ")
    val author = readln().trim()
    if (author.isEmpty()) {
        println("Error: Author cannot be empty.")
        return
    }

    print("Year: ")
    val yearInput = readln().trim()
    val year = yearInput.toIntOrNull()
    if (year == null || year <= 0 || year > 2100) {
        println("Error: Invalid year '$yearInput'. Must be a realistic 4-digit year.")
        return
    }

    val book = Book(isbn, title, author, year)
    if (service.addBook(book)) {
        println("Book added successfully: $title by $author ($year)")
    } else {
        println("Error: Book with ISBN '$isbn' already exists.")
    }
}

private fun registerPatron(repository: LibraryRepository) {
    print("Patron ID: ")
    val id = readln().trim()
    if (id.isEmpty()) {
        println("Error: Patron ID cannot be empty.")
        return
    }

    print("Name: ")
    val name = readln().trim()
    if (name.isEmpty()) {
        println("Error: Patron name cannot be empty.")
        return
    }

    val patron = Patron(id, name)
    if (repository.addPatron(patron)) {
        println("Patron registered successfully: $name (ID: $id)")
    } else {
        println("Error: Patron with ID '$id' already exists.")
    }
}

private fun borrowBook(service: LibraryService) {
    print("Patron ID: ")
    val patronId = readln().trim()
    print("ISBN: ")
    val isbn = readln().trim()

    val result = service.borrowBook(patronId, isbn)
    when (result) {
        BorrowResult.SUCCESS -> println("Book borrowed successfully! (Checked out ISBN: $isbn to Patron: $patronId)")
        BorrowResult.BOOK_NOT_FOUND -> println("Error: Book with ISBN '$isbn' not found in library.")
        BorrowResult.PATRON_NOT_FOUND -> println("Error: Patron with ID '$patronId' is not registered.")
        BorrowResult.NOT_AVAILABLE -> println("Error: Book '$isbn' is currently borrowed by another patron.")
        BorrowResult.LIMIT_REACHED -> println("Error: Patron '$patronId' has reached the maximum borrowing limit (5 books).")
    }
}

private fun returnBook(service: LibraryService) {
    print("Patron ID: ")
    val patronId = readln().trim()
    print("ISBN: ")
    val isbn = readln().trim()

    if (service.returnBook(patronId, isbn)) {
        println("Book returned successfully! ISBN: $isbn is now available.")
    } else {
        println("Error: Return failed. Verify patron ID and ensure the book was actually borrowed by this patron.")
    }
}

private fun search(service: LibraryService) {
    print("Search query: ")
    val query = readln().trim()
    if (query.isEmpty()) {
        println("Error: Search query cannot be empty.")
        return
    }

    val results = service.search(query)
    if (results.isEmpty()) {
        println("No books found matching '$query'.")
    } else {
        println("--- Search Results (${results.size} found) ---")
        results.forEachIndexed { index, book ->
            val status = if (book.isAvailable) "Available" else "Borrowed"
            println("${index + 1}. [${book.isbn}] ${book.title} by ${book.author} (${book.year}) - $status")
        }
    }
}

private fun listAllBooks(repository: LibraryRepository) {
    val books = repository.getAllBooks()
    if (books.isEmpty()) {
        println("No books in library.")
    } else {
        println("--- Library Catalog (${books.size} books) ---")
        books.forEach { book ->
            val status = if (book.isAvailable) "Available" else "Borrowed"
            println("${book.isbn} | ${book.title} | ${book.author} (${book.year}) | $status")
        }
    }
}

private fun listAllPatrons(repository: LibraryRepository) {
    val patrons = repository.getAllPatrons()
    if (patrons.isEmpty()) {
        println("No patrons registered.")
    } else {
        println("--- Registered Patrons (${patrons.size}) ---")
        patrons.forEach { patron ->
            val borrowed = if (patron.borrowedBooks.isEmpty()) "None" else patron.borrowedBooks.joinToString(", ")
            println("ID: ${patron.id} | Name: ${patron.name} | Borrowed (${patron.borrowedBooks.size}): $borrowed")
        }
    }
}

private fun displayLibraryStats(service: LibraryService) {
    val stats = service.getLibraryStats()
    println(
        """
        |--- 📊 Library Health & Analytics ---
        | Total Catalog Books:   ${stats.totalBooks}
        | Available Books:       ${stats.availableBooks}
        | Currently Borrowed:    ${stats.borrowedBooks}
        | Total Registered Users:${stats.totalPatrons}
        | Active Borrowers:      ${stats.activeBorrowers}
        | Utilization Rate:      ${if (stats.totalBooks > 0) String.format("%.1f%%", (stats.borrowedBooks.toDouble() / stats.totalBooks) * 100) else "0.0%"}
        |---------------------------------------
        """.trimMargin()
    )
}

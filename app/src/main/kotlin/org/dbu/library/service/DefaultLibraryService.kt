package org.dbu.library.service

import org.dbu.library.model.Book
import org.dbu.library.repository.LibraryRepository

class DefaultLibraryService(
    private val repository: LibraryRepository
) : LibraryService {

    companion object {
        const val MAX_BORROW_LIMIT = 5
    }

    override fun addBook(book: Book): Boolean {
        if (book.isbn.isBlank() || book.title.isBlank() || book.author.isBlank()) {
            return false
        }
        return repository.addBook(book)
    }

    override fun borrowBook(patronId: String, isbn: String): BorrowResult {
        if (patronId.isBlank() || isbn.isBlank()) {
            return BorrowResult.BOOK_NOT_FOUND
        }
        val book = repository.findBook(isbn.trim()) ?: return BorrowResult.BOOK_NOT_FOUND
        val patron = repository.findPatron(patronId.trim()) ?: return BorrowResult.PATRON_NOT_FOUND

        if (!book.isAvailable) return BorrowResult.NOT_AVAILABLE

        // Corrected from previous student's limit of 3 to course specification of 5 books
        if (patron.borrowedBooks.size >= MAX_BORROW_LIMIT) return BorrowResult.LIMIT_REACHED

        val updatedBook = book.copy(isAvailable = false)
        val updatedPatron = patron.copy(borrowedBooks = patron.borrowedBooks + isbn.trim())

        repository.updateBook(updatedBook)
        repository.updatePatron(updatedPatron)

        return BorrowResult.SUCCESS
    }

    override fun returnBook(patronId: String, isbn: String): Boolean {
        val cleanIsbn = isbn.trim()
        val cleanPatronId = patronId.trim()
        val book = repository.findBook(cleanIsbn) ?: return false
        val patron = repository.findPatron(cleanPatronId) ?: return false

        if (!patron.borrowedBooks.contains(cleanIsbn)) return false

        val updatedBook = book.copy(isAvailable = true)
        val updatedPatron = patron.copy(borrowedBooks = patron.borrowedBooks - cleanIsbn)

        repository.updateBook(updatedBook)
        repository.updatePatron(updatedPatron)

        return true
    }

    override fun search(query: String): List<Book> {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return emptyList()
        return repository.getAllBooks().filter {
            it.title.contains(trimmed, ignoreCase = true) || it.author.contains(trimmed, ignoreCase = true)
        }
    }

    override fun getLibraryStats(): LibraryStats {
        val books = repository.getAllBooks()
        val patrons = repository.getAllPatrons()
        val totalBooks = books.size
        val availableBooks = books.count { it.isAvailable }
        val borrowedBooks = totalBooks - availableBooks
        val totalPatrons = patrons.size
        val activeBorrowers = patrons.count { it.borrowedBooks.isNotEmpty() }

        return LibraryStats(
            totalBooks = totalBooks,
            availableBooks = availableBooks,
            borrowedBooks = borrowedBooks,
            totalPatrons = totalPatrons,
            activeBorrowers = activeBorrowers
        )
    }
}

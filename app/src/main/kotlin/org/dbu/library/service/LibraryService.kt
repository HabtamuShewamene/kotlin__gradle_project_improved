package org.dbu.library.service

import org.dbu.library.model.Book

data class LibraryStats(
    val totalBooks: Int,
    val availableBooks: Int,
    val borrowedBooks: Int,
    val totalPatrons: Int,
    val activeBorrowers: Int
)

interface LibraryService {
    fun addBook(book: Book): Boolean
    fun borrowBook(patronId: String, isbn: String): BorrowResult
    fun returnBook(patronId: String, isbn: String): Boolean
    fun search(query: String): List<Book>
    fun getLibraryStats(): LibraryStats
}

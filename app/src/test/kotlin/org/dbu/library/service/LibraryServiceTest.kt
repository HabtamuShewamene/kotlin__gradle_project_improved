package org.dbu.library.service

import org.dbu.library.model.Book
import org.dbu.library.model.Patron
import org.dbu.library.repository.InMemoryLibraryRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LibraryServiceTest {

    private lateinit var repository: InMemoryLibraryRepository
    private lateinit var service: DefaultLibraryService

    @BeforeEach
    fun setUp() {
        repository = InMemoryLibraryRepository()
        service = DefaultLibraryService(repository)

        repository.addBook(Book("101", "Kotlin in Action", "Dmitry Jemerov", 2017))
        repository.addBook(Book("102", "Effective Kotlin", "Marcin Moskala", 2019))
        repository.addPatron(Patron("P1", "Alice"))
    }

    @Test
    fun testAddBookSuccessAndValidation() {
        val validBook = Book("103", "Atomic Kotlin", "Bruce Eckel", 2021)
        assertTrue(service.addBook(validBook))

        // Empty fields validation
        val invalidBook = Book("", "Title", "Author", 2021)
        assertFalse(service.addBook(invalidBook))
    }

    @Test
    fun testBorrowBookLimitIsFiveNotThree() {
        // Add 5 books and borrow them
        for (i in 1..5) {
            val isbn = "B$i"
            repository.addBook(Book(isbn, "Book $i", "Author $i", 2020))
            val res = service.borrowBook("P1", isbn)
            assertEquals(BorrowResult.SUCCESS, res, "Borrowing book $i should succeed under limit of 5")
        }

        // 6th book should trigger LIMIT_REACHED
        val res6 = service.borrowBook("P1", "101")
        assertEquals(BorrowResult.LIMIT_REACHED, res6)
    }

    @Test
    fun testReturnBookSuccess() {
        service.borrowBook("P1", "101")
        assertTrue(service.returnBook("P1", "101"))
        assertTrue(repository.findBook("101")!!.isAvailable)
    }

    @Test
    fun testLibraryStats() {
        val statsBefore = service.getLibraryStats()
        assertEquals(2, statsBefore.totalBooks)
        assertEquals(2, statsBefore.availableBooks)
        assertEquals(0, statsBefore.borrowedBooks)
        assertEquals(1, statsBefore.totalPatrons)
        assertEquals(0, statsBefore.activeBorrowers)

        service.borrowBook("P1", "101")
        val statsAfter = service.getLibraryStats()
        assertEquals(1, statsAfter.availableBooks)
        assertEquals(1, statsAfter.borrowedBooks)
        assertEquals(1, statsAfter.activeBorrowers)
    }
}

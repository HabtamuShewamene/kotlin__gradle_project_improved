package org.dbu.library.ui

fun showMenu(): String {
    println(
        """
        |================ LIBRARY MENU [Improved Edition] ================
        | 1 | Add Book
        | 2 | Register Patron
        | 3 | Borrow Book
        | 4 | Return Book
        | 5 | Search
        | 6 | List All Books
        | 7 | View All Patrons
        | 8 | Library Statistics & Health
        | 0 | Exit
        |==============================================
        | Enter option:
        """.trimMargin()
    )

    return readln().trim()
}

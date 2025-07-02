package org.example;

import org.example.caliculatefee.DayWiseFeeCaliculation;
import org.example.caliculatefee.FeeStrategy;
import org.example.payment.CashPayment;
import org.example.payment.PaymentChannel;

public class LMS {

    public static void main(String[] args) {
        // Initialize the library system
        FeeStrategy feeStrategy = new DayWiseFeeCaliculation();
        PaymentChannel paymentChannel = new CashPayment();
        Library library = new Library(1, "City Library", "123 Main St", feeStrategy, paymentChannel);

        // Create some books
        Book book1 = new Book(9780135166307L, "Effective Java", "Joshua Bloch", 2018);
        Book book2 = new Book(9780596009205L, "Head First Java", "Kathy Sierra", 2003);

        // Add books to the library
        library.addBook(book1);
        library.addBook(book1);
        library.addBook(book2);

        // Create a patron
        Patron patron = new Patron(1, "Alice Smith", "456 Elm St", "555-1234");
        // Add patron to the library
        library.addPatron(patron);
        // Borrow a book
        patron.LendBook(book1, library);
        // Return a book
        patron.ReturnBook(book1, library);


        library.searchBooks("Head");

    }
}

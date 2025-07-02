package org.example;

import org.example.caliculatefee.FeeStrategy;
import org.example.payment.PaymentChannel;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Library {

    private int LibraryId;
    private String LibraryName;
    private String Address;
    private Map<Book,Integer> books;
    private Set<Patron> patrons;
    private Map<Patron,List<Map<Book, Date>>> issuedBooks;
    private FeeStrategy feeStrategy;
    private PaymentChannel paymentChannel;


    public Library(int libraryId, String libraryName, String address,FeeStrategy feeStrategy, PaymentChannel paymentChannel) {
        LibraryId = libraryId;
        LibraryName = libraryName;
        Address = address;
        this.feeStrategy = feeStrategy;
        this.paymentChannel = paymentChannel;
        System.out.println("Library created with ID: " + libraryId + ", Name: " + libraryName + ", Address: " + address);
    }

    public void issueBook(Book book, Patron patron) {
        Book b1 = books.keySet().stream().filter(b -> b.getIsbn() == book.getIsbn()).findFirst().orElse(null);
        if (b1 == null) {
            System.out.println("Book not found in the library.");
        } else {
            if (books.get(b1) <= 0) {
                System.out.println("Books are out of stock");
            } else {
                if (patron.getBorrowedBooks() == null) {
                    patron.setBorrowedBooks(new ArrayList<>());
                }
                if (patron.getBorrowedBooks().contains(b1)) {
                    System.out.println("Patron already has this book borrowed.");
                    return;
                }
                books.put(b1, books.get(b1) - 1);
                patron.getBorrowedBooks().add(b1);
                if (issuedBooks == null) {
                    issuedBooks = new HashMap<>();
                }
                List<Map<Book, Date>> borrowedList = issuedBooks.get(patron);
                if (borrowedList == null) {
                    borrowedList = new ArrayList<>();
                    issuedBooks.put(patron, borrowedList);
                }
                Map<Book, Date> borrowEvent = new HashMap<>();
                borrowEvent.put(b1, new Date());
                borrowedList.add(borrowEvent);
                System.out.println("Book issued successfully to " + patron.getName()+
                        ". Current copies: " + books.get(b1) + " of " + b1.getTitle() + " by " + b1.getAuthor());
            }
        }
    }

    public void collectBook(Book book, Patron patron) {
        Book b1 = books.keySet().stream().filter(b -> b.getIsbn() == book.getIsbn()).findFirst().orElse(null);
        if (b1 == null) {
            System.out.println("Book not found in the library.");
        } else {
            if (patron.getBorrowedBooks() == null || !patron.getBorrowedBooks().contains(b1)) {
                System.out.println("Patron does not have this book borrowed.");
            } else {
                patron.getBorrowedBooks().remove(b1);
                books.put(b1, books.getOrDefault(b1, 0) + 1);
                System.out.println("Book collected successfully from " + patron.getName());
                Date date = getIssueDate(patron, book);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                int dateAsInt = Integer.parseInt(sdf.format(date));
                feeStrategy.calculateFee(dateAsInt, (int) (new Date().getTime() / 1000 - date.getTime() / 1000) / (24 * 3600));
                paymentChannel.pay();
                if (issuedBooks != null && issuedBooks.get(patron) != null) {
                    issuedBooks.get(patron).removeIf(entry -> entry.containsKey(b1));
                }

            }
        }
    }

    public void addBook(Book book) {
        // Logic to add a book to the library's collection
        if (books == null) {
            books = new java.util.HashMap<>();
        }
        Book b1 = books.keySet().stream().filter(b -> b.getIsbn() == book.getIsbn()).findFirst().orElse(null);
        if (b1 != null) {
            books.put(b1, books.get(b1) + 1);
            System.out.println("Book already exists in the library. Added another copy."+ " Current copies: " + books.get(b1) + " of " + b1.getTitle() + " by " + b1.getAuthor());
        } else {
            books.put(book, 1);
            System.out.println("Book added successfully: " + book.getTitle() + " by " + book.getAuthor());
        }
    }

    public void removeBook(Book book) {
        // Logic to remove a book from the library's collection
        // This could involve checking if the book exists and updating the records.
        Book b1=books.keySet().stream().filter(b->b.getIsbn()==book.getIsbn()).findFirst().orElse(null);
        if(b1==null){
            System.out.println("Book not found in the library.");
        }else{
            if(books.get(b1) <= 0) {
                System.out.println("No copies of the book available to remove.");
            }else{
                books.put(b1, books.get(b1) - 1);
                if(books.get(b1) == 0) {
                    books.remove(b1);
                }
                System.out.println("Book removed successfully.");
            }
        }

    }

    public void addPatron(Patron patron) {
        if (patrons == null) {
            patrons = new java.util.HashSet<>();
        }
        if (patrons.contains(patron)) {
            System.out.println("Patron already exists in the library.");
        } else {
            patrons.add(patron);
            System.out.println("Patron added successfully: " + patron.getName());
        }
    }

    public void removePatron(Patron patron) {
        if (patrons == null) {
            System.out.println("No patrons in the library.");
            return;
        }
        if (patrons.contains(patron)) {
            patrons.remove(patron);
            System.out.println("Patron removed successfully: " + patron.getName());
        } else {
            System.out.println("Patron not found in the library.");
        }
    }

    public Date getIssueDate(Patron patron, Book book) {
        if (issuedBooks == null || !issuedBooks.containsKey(patron)) {
            return null;
        }
        List<Map<Book, Date>> borrowedList = issuedBooks.get(patron);
        for (Map<Book, Date> entry : borrowedList) {
            if (entry.containsKey(book)) {
                return entry.get(book);
            }
        }
        return null;
    }

    public void searchBooks(String s){
        //search for books by title or author
        if (books == null || books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }
        boolean found = false;
        for (Book book : books.keySet()) {
            if (book.getTitle().toLowerCase().contains(s.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(s.toLowerCase())) {
                System.out.println("\nSearches Found: \n" + book.getTitle() + " by " + book.getAuthor() +
                                   ". Copies available: " + books.get(book)+"\n");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found matching the search criteria: " + s);
        }

    }


    public Map<Patron, List<Map<Book, Date>>> getIssuedBooks() {
        return issuedBooks;
    }

    public void setIssuedBooks(Map<Patron, List<Map<Book, Date>>> issuedBooks) {
        this.issuedBooks = issuedBooks;
    }
}

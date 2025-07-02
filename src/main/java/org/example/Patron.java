package org.example;

import java.util.List;

public class Patron {

    private int id;
    private String name;
    private String Address;
    private String phoneNumber;

    private List<Book> borrowedBooks;

    public Patron(int id, String name, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        Address = address;
        this.phoneNumber = phoneNumber;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void LendBook(Book book,Library library) {
        library.issueBook(book,this);

    }

    public void ReturnBook(Book book, Library library) {
        // Logic to return a book to the library
        // This could involve checking if the book is currently lent out to this patron
        // and updating the library's records accordingly.
        library.collectBook(book, this);
    }

    public void readInLibrary(Book book, Library library) {
        // Logic to read a book in the library
        // This could involve checking if the book is available for reading in the library
        // and updating the library's records accordingly.

            boolean isIssued = library.getIssuedBooks() != null &&
                library.getIssuedBooks().values().stream()
                    .flatMap(List::stream)
                    .anyMatch(map -> map.containsKey(book));
            if (isIssued) {
                System.out.println("Book is currently issued and not available for reading in the library.");
            } else {
                System.out.println("Book is available for reading in the library.");
            }

    }




}

# 📚 Library Management System (LMS)
This document outlines the design and requirements for a Library Management System (LMS) that allows users to manage books, patrons, and transactions effectively.


## ✅ Functional Requirements

1. Ability to store work (books)
2. Add or remove books from the library
3. Check-in and check-out of a book
4. Add or remove patrons
5. Search books by name or author
6. Calculate the fee
7. Allow different payment channels

---

## 🧱 Entities and Responsibilities

### 📘 Book

| Field            | Description             |
|------------------|-------------------------|
| `bookName`       | Name of the book        |
| `authorName`     | Author's name           |
| `isbnNumber`     | ISBN number             |
| `publicationYear`| Year of publication     |

> Includes: Getters and Setters

---

### 👤 Patron

| Field            | Description             |
|------------------|-------------------------|
| `id`             | Patron ID               |
| `name`           | Patron name             |
| `address`        | Patron address          |
| `emailAddress`   | Patron email            |
| `phoneNumber`    | Patron phone number     |
| `List<Book>`     | List of borrowed books  |

> Methods:
- `lendBook()`
- `returnBook()`
- `readInLibrary()`
- Getters and Setters

---

### 🏢 Library

| Field                                         | Description                             |
|-----------------------------------------------|-----------------------------------------|
| `libraryId`                                   | Unique ID of the library                |
| `libraryName`                                 | Name of the library                     |
| `address`                                     | Address of the library                  |
| `Map<String, Integer>`                        | Books with count (`bookName → quantity`)|
| `Set<Patron>`                                 | Set of registered patrons               |
| `Map<Patron, List<Map<Book, Date>>>`          | Issued books with issue date per patron |
| `FeeStrategy`                                 | Object to calculate fees                |
| `PaymentChannel`                              | Payment handling method                 |

> Methods:
- `issueBook()`
- `collectBook()`
- `addBook()`
- `removeBook()`
- `addPatron()`
- `removePatron()`
- `searchBooks()`
- Getters and Setters

---

### 💳 PaymentChannel

> Interface to handle fee payment

> Method:
- `pay()`

---

### 💰 FeeStrategy

> Interface to calculate the fee

> Method:
- `calculateFee()`

---

### 🛠 LMS (Library Management System)

This class is responsible for:
- Creating the library instance
- Adding books and patrons
- Performing actions like issuing or returning books

---

## 🔗 Relationships Between Entities

- A **Library** has multiple **Books** (1 → many)
- A **Library** has multiple **Patrons** (1 → many)
- A **Library** has **one PaymentChannel** (1 → 1)
- A **Library** has **one FeeStrategy** (1 → 1)
- A **Patron** has multiple borrowed **Books** (1 → many)
- A **Patron** uses a **Library**

---

submit: LMS Design Document


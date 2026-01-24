# MyLibrary - Library Management System 📚

MyLibrary is a Java-based desktop application designed to manage personal book collections, reading lists, and favorite authors.

##  Features

This project provides the following features for users to manage their libraries efficiently:

* **User Login & Security:**
    * Admin and Standard User authorization.
    * Secure password storage using SHA-256 encryption.
* **Book Management (CRUD):**
    * Add new books, update existing ones, and remove books.
    * View book covers and detailed information.
* **Lists & Tracking:**
    * **Unread Books:** Keep track of books you haven't read yet.
    * **Favorites:** Automatically lists books with high ratings (4+) and favorite authors (3+ books).
    * **Wishlist Notifications:** Automatic notifications on the login screen for books in your wishlist being released within the next 7 days.
* **Database Integration:**
    * Robust data storage infrastructure using MySQL.

## 🛠️ Technologies

* **Language:** Java (JDK 8+)
* **Interface:** Java Swing (JFrame)
* **Database:** MySQL
* **Connectivity:** JDBC (MySQL Connector)

## ⚙️ Installation & Setup

Follow these steps to run the project on your local machine:

### 1. Prerequisites
* Java Development Kit (JDK) must be installed.
* MySQL Server must be installed and running.

### 2. Database Configuration
1.  Create a database named `library` in MySQL.
2.  Import the `SQLMYLibrary.sql` file located in the project's root directory to create the necessary tables (`users`, `books`, `authors`).
3.  Open `src/DataBase.java` and update the connection details with your own MySQL credentials:
    ```java
    private static final String URL = "jdbc:mysql://localhost:3306/library";

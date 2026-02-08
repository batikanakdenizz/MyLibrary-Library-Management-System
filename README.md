# MyLibrary - Library Management System  📚

MyLibrary is a Java-based desktop application designed to manage personal book collections, reading lists, and favorite authors.

Detailed explanation A detailed system analysis and project report can be found in the **[MyLibrary_System_SRS_Document](MyLibrary_System_SRS_Document.pdf)** file.

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

## 🛠️ Tech Stack & Tools

This project utilizes a comprehensive set of technologies for development, design, and data management:

* **Programming Language:** Java (JDK 8+)
* **User Interface (GUI):** Java Swing, AWT
* **Database System:** MySQL (v8.0.42)
* **Data Connectivity:** JDBC (Java Database Connectivity)
* **Security & Cryptography:** Java Security API (SHA-256 Hashing for passwords)
* **Development Environment (IDE):** Apache NetBeans 25
* **Analysis & Design Tools:**
    * **UML & DFD Modeling:** Visual Paradigm 17.1
    * **Database Design:** MySQL Workbench
* **Build System:** Apache Ant (Native NetBeans Build)

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
    private static final String USER = "root";
    private static final String PASSWORD = "YOUR_PASSWORD";
    ```

### 3. Running the Application
You can start the application by compiling and running the `src/LIBRARY.java` class.

```bash
javac src/*.java
java src/LIBRARY

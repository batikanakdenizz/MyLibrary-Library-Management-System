
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author batik
 */
public class DataBase {
     private static String SECRET_KEY = "L0ZRUIWjsQgPf74IEAHO5w==";
    private static final String URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    public static final String EXTREMLY_SECURE_STRONG_PASSWORD = "123456789";
    private User user;
    private Connection connection;      //this part is inspired from Atabarış Hoca's example.
                                        // but we use different hash system that is SHA256.
    
    public String hashPassword(String password) {
    try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        return null;
    }
}
    
    public void connect() throws SQLException {
    connection = DriverManager.getConnection(URL, USER, PASSWORD);
}

    public void close() {
    try {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    } catch (SQLException e) {
        System.err.println("Connection close error");
    }
}
    
    public boolean login(String name, String password) {
    ResultSet rs;
    try {
        connect(); 
        String sqlLogin = "SELECT login(?, ?)";
        String sqlGetUser = "CALL get_user(?, ?)";

        PreparedStatement login = connection.prepareStatement(sqlLogin);
        PreparedStatement getUser = connection.prepareStatement(sqlGetUser);

        String hashedPassword = this.hashPassword(password);
        
        login.setString(1, name);
        login.setString(2, hashedPassword);
        getUser.setString(1, name);
        getUser.setString(2, hashedPassword);

        rs = login.executeQuery();
        if (rs.next() && rs.getBoolean(1)) {
            rs = getUser.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("USERID");
                String username = rs.getString("USERNAME");
                int userType = rs.getInt("USERTYPE");

                this.user = new User(userId, username, userType);
            }
            return true;
        }

    } catch (SQLException e) {
        System.out.println("Login error: " + e.getMessage());
    } finally {
        close(); 
    }

    return false;
}

    public int getAuthorId(String name, String surname) {
    String selectSQL = "SELECT authorId FROM Authors WHERE name = ? AND surname = ?";
    String insertSQL = "INSERT INTO Authors (name, surname, website) VALUES (?, ?, ?)";
    String updateSQL = "UPDATE Authors SET website = ? WHERE authorId = ?";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

        // check
        try (PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
            selectStmt.setString(1, name);
            selectStmt.setString(2, surname);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("authorId");
            }
        }

 
        int generatedId = -1;
        try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, name);
            insertStmt.setString(2, surname);
            insertStmt.setString(3, "temp"); 
            insertStmt.executeUpdate();

            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
                System.out.println("Generated Author ID: " + generatedId);
            }
        }

        // rule for website update
        if (generatedId > 0) {
            String finalWebsite = "website-" + generatedId;
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                updateStmt.setString(1, finalWebsite);
                updateStmt.setInt(2, generatedId);
                updateStmt.executeUpdate();
            }
        } else {
            System.err.println(" Error: Could not generate authorId properly!");
        }

        return generatedId;

    } catch (SQLException e) {
        e.printStackTrace();
        return -1;
    }
}





    public int getNextBookId() {
    int nextId = 1; // default olarak 1'den başlasın

    String sql = "SELECT MAX(bookId) AS maxId FROM Books";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            int maxId = rs.getInt("maxId");
            if (!rs.wasNull()) {
                nextId = maxId + 1;
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return nextId;
}

    public boolean addBook(int bookId, String title, String authorName, String authorSurname,
                       int year, int pages, String cover, String about,
                       int read, int rating, String comments, String releaseDate) {

    // ✅ Check for duplicate Book ID
    if (bookIdExists(bookId)) {
        JOptionPane.showMessageDialog(null, "A book with ID " + bookId + " already exists!");
        return false;
    }

    int authorId = getAuthorId(authorName, authorSurname);

    String sql = "INSERT INTO Books (bookId, authorId, title, year, numberOfPages, cover, about, `read`, rating, comments, releaseDate) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, bookId);
        stmt.setInt(2, authorId);
        stmt.setString(3, title);
        stmt.setInt(4, year);
        stmt.setInt(5, pages);
        stmt.setString(6, cover);
        stmt.setString(7, about);
        stmt.setInt(8, read);

        if (read != 1) {
            rating = 0;
        }
        stmt.setInt(9, rating);
        stmt.setString(10, comments);

        if (read == 3) {
            stmt.setString(11, releaseDate);
        } else {
            stmt.setNull(11, java.sql.Types.DATE);
        }

        int result = stmt.executeUpdate();
        return result > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    private boolean bookIdExists(int bookId) {
    String checkSql = "SELECT 1 FROM Books WHERE bookId = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(checkSql)) {
        stmt.setInt(1, bookId);
        ResultSet rs = stmt.executeQuery();
        return rs.next(); // varsa true döner
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    public Book getBookById(int bookId) {
    String sql = "SELECT * FROM Books WHERE bookId = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, bookId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
           return new Book(
    rs.getInt("bookId"),
    rs.getInt("authorId"),
    rs.getString("title"),
    rs.getInt("year"),
    rs.getInt("numberOfPages"),
    rs.getString("cover"),
    rs.getString("about"),
    rs.getInt("read"),
    rs.getInt("rating"),
    rs.getString("comments"),
    rs.getDate("releaseDate")
);


        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}


    public boolean removeBookById(int bookId) {
    String getAuthorIdSQL = "SELECT authorId FROM Books WHERE bookId = ?";
    String deleteBookSQL = "DELETE FROM Books WHERE bookId = ?";
    String checkAuthorSQL = "SELECT COUNT(*) FROM Books WHERE authorId = ?";
    String deleteAuthorSQL = "DELETE FROM Authors WHERE authorId = ?";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

        
        int authorId = -1;
        try (PreparedStatement stmt = conn.prepareStatement(getAuthorIdSQL)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                authorId = rs.getInt("authorId");
            } else {
                return false; 
            }
        }

        
        try (PreparedStatement stmt = conn.prepareStatement(deleteBookSQL)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        }

      // rule: If author has no book then author must be delete ✅ 
        try (PreparedStatement stmt = conn.prepareStatement(checkAuthorSQL)) {
            stmt.setInt(1, authorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                
                try (PreparedStatement deleteAuthor = conn.prepareStatement(deleteAuthorSQL)) {
                    deleteAuthor.setInt(1, authorId);
                    deleteAuthor.executeUpdate();
                }
            }
        }

        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    public Author getAuthorByName(String name, String surname) {
    String sql = "SELECT * FROM Authors WHERE name = ? AND surname = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, name);
        stmt.setString(2, surname);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int authorId = rs.getInt("authorId");
            String website = rs.getString("website");
            return new Author(authorId, name, surname); 
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null; 
}


    public boolean updateBook(int bookId, String title, int year, int pages, String cover, String about,
                          int read, int rating, String comments, String releaseDate, int authorId) {

    String sql = "UPDATE Books SET title = ?, year = ?, numberOfPages = ?, cover = ?, about = ?, " +
                 "`read` = ?, rating = ?, comments = ?, releaseDate = ?, authorId = ? WHERE bookId = ?";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, title);
        stmt.setInt(2, year);
        stmt.setInt(3, pages);
        stmt.setString(4, cover);
        stmt.setString(5, about);
        stmt.setInt(6, read);
        stmt.setInt(7, read == 1 ? rating : 0);
        stmt.setString(8, comments);

        if (read == 3 && !releaseDate.isEmpty()) {
            stmt.setDate(9, Date.valueOf(releaseDate));
        } else {
            stmt.setNull(9, java.sql.Types.DATE);
        }

        stmt.setInt(10, authorId);
        stmt.setInt(11, bookId);

        int result = stmt.executeUpdate();
        return result > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
    
}

 public List<Book> getFavoriteBooks() {
    List<Book> favoriteBooks = new ArrayList<>();

    String sql = "SELECT * FROM Books WHERE `read` = 1 AND rating >= 4";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Book book = new Book(
                rs.getInt("bookId"),
                rs.getInt("authorId"),
                rs.getString("title"),
                rs.getInt("year"),
                rs.getInt("numberOfPages"),
                rs.getString("cover"),
                rs.getString("about"),
                rs.getInt("read"),
                rs.getInt("rating"),
                rs.getString("comments"),
                rs.getDate("releaseDate")
            );
            favoriteBooks.add(book);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return favoriteBooks;
}


         

   public List<Author> getFavoriteAuthors() {
    List<Author> favAuthors = new ArrayList<>();

    String sql = """
        SELECT a.authorId, a.name, a.surname, a.website
        FROM Authors a
        JOIN Books b ON a.authorId = b.authorId
        GROUP BY a.authorId
        HAVING COUNT(b.bookId) >= 3;
    """;

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Author a = new Author(
                rs.getInt("authorId"),
                rs.getString("name"),
                rs.getString("surname")
            );
            favAuthors.add(a);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return favAuthors;
}

    
    public List<Book> getUnreadBooks() {
    List<Book> unreadBooks = new ArrayList<>();

    String sql = "SELECT * FROM Books WHERE `read` = 2";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Book book = new Book(
                rs.getInt("bookId"),
                rs.getInt("authorId"),
                rs.getString("title"),
                rs.getInt("year"),
                rs.getInt("numberOfPages"),
                rs.getString("cover"),
                rs.getString("about"),
                rs.getInt("read"),
                rs.getInt("rating"),
                rs.getString("comments"),
                rs.getDate("releaseDate")
            );
            unreadBooks.add(book);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return unreadBooks;
}

 public List<Book> getUpcomingWishlistBooks() {
    List<Book> books = new ArrayList<>();
    String sql = "SELECT * FROM Books WHERE `read` = 3 AND releaseDate IS NOT NULL AND releaseDate BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Book book = new Book(
                rs.getInt("bookId"),
                rs.getInt("authorId"),
                rs.getString("title"),
                rs.getInt("year"),
                rs.getInt("numberOfPages"),
                rs.getString("cover"),
                rs.getString("about"),
                rs.getInt("read"),
                rs.getInt("rating"),
                rs.getString("comments"),
                rs.getDate("releaseDate")
            );
            books.add(book);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return books;
}

public List<Book> getBooksByAuthor(String name, String surname) {
    List<Book> books = new ArrayList<>();

    String sql = """
        SELECT b.* FROM Books b
        JOIN Authors a ON b.authorId = a.authorId
        WHERE a.name = ? AND a.surname = ?
    """;

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, name);
        stmt.setString(2, surname);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Book book = new Book(
                    rs.getInt("bookId"),
                    rs.getInt("authorId"),
                    rs.getString("title"),
                    rs.getInt("year"),
                    rs.getInt("numberOfPages"),
                    rs.getString("cover"),
                    rs.getString("about"),
                    rs.getInt("read"),
                    rs.getInt("rating"),
                    rs.getString("comments"),
                    rs.getDate("releaseDate")
                );
                books.add(book);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return books;
}






public User getUser() {
    return user;
}

    
    
    
}

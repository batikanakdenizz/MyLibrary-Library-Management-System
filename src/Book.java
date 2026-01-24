/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author batik
 */
import java.util.Date;

public class Book {
    // Attributes
    private int bookId;
    private int authorId;
    private String title;
    private int year;
    private int numberOfPages;
    private String cover;
    private String about;       //requirements:
    private int read;           // 1 = read, 2 = not read, 3 = wish to read
    private int rating;         // 1-5 if read, not read -0 
    private String comments;    // can be null
    private Date releaseDate;   // only if read == 3
    

    
    public Book(int bookId, int authorId, String title, int year, int numberOfPages,
                String cover, String about, int read, int rating,
                String comments, Date releaseDate) {
        this.bookId = bookId;
        this.authorId = authorId;
        this.title = title;
        this.year = year;
        this.numberOfPages = numberOfPages;
        this.cover = cover;
        this.about = about;
        this.read = read;
        this.rating = rating;
        this.comments = comments;
        this.releaseDate = releaseDate;
    }
    
public void validate() {
    // Rule 1: read must be 1-readed , 2-not readed , or 3 wishes
    if (read < 1 || read > 3) {
        throw new IllegalArgumentException("Read must be 1 (read), 2 (not read), or 3 (wish to read).");
    }

    // Rule 2: rating rules
    if (read == 1) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5 if the book is read.");
        }
    } else {
        if (rating != 0) {
            throw new IllegalArgumentException("Rating must be 0 if the book is not read or is wished to be read.");
        }
    }

    // Rule 3: releaseDate rules
    if (read == 3 && releaseDate == null) {
        throw new IllegalArgumentException("Release date must be provided when the book is wished to be read.");
    } else if ((read == 1 || read == 2) && releaseDate != null) {
        throw new IllegalArgumentException("Release date must be null when the book is read or not read.");
    }
}

    


  
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getNumberOfPages() { return numberOfPages; }
    public void setNumberOfPages(int numberOfPages) { this.numberOfPages = numberOfPages; }

    public String getCover() { return cover; }
    public void setCover(String cover) { this.cover = cover; }

    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }

    public int getRead() { return read; }
    public void setRead(int read) { this.read = read; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public Date getReleaseDate() { return releaseDate; }
    public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; }

   
    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", authorId=" + authorId +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", numberOfPages=" + numberOfPages +
                ", cover='" + cover + '\'' +
                ", about='" + about + '\'' +
                ", read=" + read +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author batik
 */
    
    public class Author {
    private int authorId;
    private String name;
    private String surname;
    private String website;

    
    public Author(int authorId, String name, String surname) {
        this.authorId = authorId;
        this.name = name;
        this.surname = surname;
        this.website = generateWebsite(authorId); // Based on project rule
    }

    // goal : Every Author has an website on this format : "website-AuthorID" ✅ 
    private String generateWebsite(int authorId) {
        return "website-" + authorId;
    }


    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
        this.website = generateWebsite(authorId); // update website if authorId changes
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getWebsite() { return website; }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}

    
    

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author batik
 */
public class User {
    private int id;
    private String username;
    private int userType;

    public User(int id, String username, int userType) {
        this.id = id;
        this.username = username;
        this.userType = userType;
    }

    // Getter'lar
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getUserType() {
        return userType;
    }

    // Setter'lar
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}


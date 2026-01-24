/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author batik
 */
public enum DataBase_Commands {
    SQL_LOGIN("SELECT login(?,?)"),  //this part is inspired from Atabarış Hoca's example.
    SQL_GET_USER("CALL get_user(?,?)"),
    SQL_GET_STUDENTS("CALL get_students()"),
    ERROR_INVALID_LOGIN("User name or password is wrong"),
    ERROR_CONNECTION("Can not connect to DB"),
    ERROR_CONNECTION_CLOSE("Can not close DB Connection"),
    ERROR_USER("User not found"),    
    ERROR_GET_STUDENTS("Can not fetch students");

    private final String command;

    private DataBase_Commands(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }
}

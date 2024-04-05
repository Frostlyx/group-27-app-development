package com.example.barcodescanner.ui.login;

/**
 * Class representing user details for reading and writing.
 * This class holds information such as username, email, password, kvkNumber, location, and store name.
 */
public class ReadWriteUserDetails {
    // Fields to store user details
    public String username, email, password, kvkNumber, location, Storename;

    // Constructor to initialize user details
    public ReadWriteUserDetails(String username, String email, String password, String kvkNumber, String location, String Storename){
        this.username = username;
        this.email = email;
        this.password = password;
        this.kvkNumber = kvkNumber;
        this.location = location;
        this.Storename = Storename;
    }
}

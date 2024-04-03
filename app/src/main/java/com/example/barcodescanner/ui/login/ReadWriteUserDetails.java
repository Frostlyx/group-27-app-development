package com.example.barcodescanner.ui.login;

public class ReadWriteUserDetails {
    public String username, email, password, kvkNumber, location, Storename;

    public ReadWriteUserDetails(String username, String email, String password, String kvkNumber, String location, String Storename){
        this.username = username;
        this.email = email;
        this.password = password;
        this.kvkNumber = kvkNumber;
        this.location = location;
        this.Storename = Storename;
    }
}

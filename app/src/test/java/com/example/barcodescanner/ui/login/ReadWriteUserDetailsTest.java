package com.example.barcodescanner.ui.login;

import junit.framework.TestCase;

public class ReadWriteUserDetailsTest extends TestCase {


    public void testReadWriteUserDetailsConstructor() {
        String Username = "s";
        String Email = "s";
        String Password = "s";
        String KvkNumber = "s";
        String Location = "s";
        String Storename = "s";
        ReadWriteUserDetails readWriteUserDetails = new ReadWriteUserDetails(Username, Email, Password, KvkNumber, Location, Storename);
        assertEquals(Username, readWriteUserDetails.username);
        assertEquals(Email, readWriteUserDetails.email);
        assertEquals(Password, readWriteUserDetails.password);
        assertEquals(KvkNumber, readWriteUserDetails.kvkNumber);
        assertEquals(Location, readWriteUserDetails.location);
        assertEquals(Storename, readWriteUserDetails.Storename);
    }
}
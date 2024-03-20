package com.example.barcodescanner.data;

import com.example.barcodescanner.data.model.User;

import java.io.IOException;

/**
 * Class that handles authentication w/ register credentials and retrieves user information.
 */
public class RegisterCustomerDataSource {

    public Result<User> register(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            User fakeUser =
                    new User(
                            java.util.UUID.randomUUID().toString(),
                            username);
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
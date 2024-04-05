package com.example.barcodescanner.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 * This class represents the state of the login form data validation.
 */
public class LoginFormState {
    @Nullable
    private final Integer usernameError; // Error message resource ID for the username field
    @Nullable
    private final Integer passwordError; // Error message resource ID for the password field
    private final boolean isDataValid; // Indicates whether the data is valid or not

    // Constructor to initialize LoginFormState
    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        // Check if both username and password errors are null to determine if data is valid
        this.isDataValid = usernameError == null && passwordError == null;
    }

    // Getter for username error
    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    // Getter for password error
    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    // Method to check if data is valid
    boolean isDataValid() {
        return isDataValid;
    }
}

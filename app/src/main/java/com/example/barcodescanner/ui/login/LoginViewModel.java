package com.example.barcodescanner.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;

/**
 * ViewModel for the login screen.
 * This ViewModel is responsible for handling data validation and providing UI state for the login screen.
 */
public class LoginViewModel extends ViewModel {

    // LiveData to observe login form state
    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();

    // Method to get login form state LiveData
    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    // Method to update login form state based on input data
    public void loginDataChanged(String username, String password) {
        Integer usernameError;
        Integer passwordError;

        // Checking username validity
        if (!isUserNameValid(username)) {
            usernameError = R.string.invalid_username;
        } else {
            usernameError = null;
        }

        // Checking password validity
        if (!isPasswordValid(password)) {
            passwordError = R.string.invalid_password;
        } else {
            passwordError = null;
        }

        // Setting login form state
        loginFormState.setValue(new LoginFormState(usernameError, passwordError));
    }

    // Method to validate username
    private boolean isUserNameValid(String username) {
        return username != null && !username.trim().isEmpty();
    }

    // Method to validate password
    private boolean isPasswordValid(String password) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;

        // Checking password length and character types
        if (password == null || password.trim().length() < 8) {
            return false;
        }

        // Checking for uppercase and lowercase characters
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            }
        }

        // Password should contain at least one uppercase and one lowercase character
        return (hasUpperCase && hasLowerCase);
    }
}

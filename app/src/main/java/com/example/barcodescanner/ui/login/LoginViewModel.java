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

    // Checks if data is valid
    private final ValidityChecker validityChecker = new ValidityChecker();

    // Method to get login form state LiveData
    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    // Method to update login form state based on input data
    public void loginDataChanged(String username, String password) {
        Integer[] formErrors = getFormErrors(username, password);

        // Setting login form state
        loginFormState.setValue(new LoginFormState(formErrors[0], formErrors[1]));
    }

    // Method to get form errors
    public Integer[] getFormErrors(String username, String password) {
        Integer usernameError;
        Integer passwordError;
        Integer[] result = new Integer[2];


        // Checking username validity
        if (!validityChecker.isStringValid(username)) {
            usernameError = R.string.invalid_username;
        } else {
            usernameError = null;
        }

        // Checking password validity
        if (!validityChecker.isPasswordValid(password)) {
            passwordError = R.string.invalid_password;
        } else {
            passwordError = null;
        }

        result[0] = usernameError;
        result[1] = passwordError;

        return result;
    }
}

package com.example.barcodescanner.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Patterns;
import com.example.barcodescanner.R;

/**
 * ViewModel for registering new customers.
 */
public class
RegisterCustomerViewModel extends ViewModel {

    private final MutableLiveData<RegisterCustomerFormState> registerCustomerFormState = new MutableLiveData<>();

    // Checks if data is valid
    private final ValidityChecker validityChecker = new ValidityChecker();

    /**
     * Gets the LiveData for the registration form state.
     *
     * @return LiveData object representing the registration form state.
     */
    LiveData<RegisterCustomerFormState> getRegisterCustomerFormState() {
        return registerCustomerFormState;
    }

    /**
     * Updates the registration form state based on the provided data.
     *
     * @param username        Username entered by the user.
     * @param email           Email entered by the user.
     * @param confirmEmail    Email confirmation entered by the user.
     * @param password        Password entered by the user.
     * @param confirmPassword Password confirmation entered by the user.
     */
    public void registerCustomerDataChanged(String username, String email, String confirmEmail,
                                            String password, String confirmPassword) {
        Integer usernameError;
        Integer emailError;
        Integer confirmEmailError;
        Integer passwordError;
        Integer confirmPasswordError;

        // Check if username is valid
        if (!validityChecker.isStringValid(username)) {
            usernameError = R.string.invalid_username;
        } else {
            usernameError = null;
        }

        // Check if email is valid
        if (!validityChecker.isEmailValid(email)) {
            emailError = R.string.invalid_email;
        } else {
            emailError = null;
        }

        // Check if confirm email matches email
        if (!validityChecker.isConfirmValid(email, confirmEmail)) {
            confirmEmailError = R.string.invalid_email_confirm;
        } else {
            confirmEmailError = null;
        }

        // Check if password is valid
        if (!validityChecker.isPasswordValid(password)) {
            passwordError = R.string.invalid_password;
        } else {
            passwordError = null;
        }

        // Check if confirm password matches password
        if (!validityChecker.isConfirmValid(password, confirmPassword)) {
            confirmPasswordError = R.string.invalid_password_confirm;
        } else {
            confirmPasswordError = null;
        }

        // Update the registration form state LiveData
        registerCustomerFormState.setValue(new RegisterCustomerFormState(usernameError, emailError,
                confirmEmailError, passwordError, confirmPasswordError));
    }
}

package com.example.barcodescanner.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Patterns;
import com.example.barcodescanner.R;

/**
 * ViewModel for registering new customers.
 */
public class RegisterCustomerViewModel extends ViewModel {

    private final MutableLiveData<RegisterCustomerFormState> registerCustomerFormState = new MutableLiveData<>();

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
        if (!isUserNameValid(username)) {
            usernameError = R.string.invalid_username;
        } else {
            usernameError = null;
        }

        // Check if email is valid
        if (!isEmailValid(email)) {
            emailError = R.string.invalid_email;
        } else {
            emailError = null;
        }

        // Check if confirm email matches email
        if (!isConfirmValid(email, confirmEmail)) {
            confirmEmailError = R.string.invalid_email_confirm;
        } else {
            confirmEmailError = null;
        }

        // Check if password is valid
        if (!isPasswordValid(password)) {
            passwordError = R.string.invalid_password;
        } else {
            passwordError = null;
        }

        // Check if confirm password matches password
        if (!isConfirmValid(password, confirmPassword)) {
            confirmPasswordError = R.string.invalid_password_confirm;
        } else {
            confirmPasswordError = null;
        }

        // Update the registration form state LiveData
        registerCustomerFormState.setValue(new RegisterCustomerFormState(usernameError, emailError,
                confirmEmailError, passwordError, confirmPasswordError));
    }

    /**
     * Validates the format of the username.
     *
     * @param username Username to be validated.
     * @return True if the username is not empty, false otherwise.
     */
    private boolean isUserNameValid(String username) {
        return username != null && !username.trim().isEmpty();
    }

    /**
     * Validates the format of the email address using a regular expression.
     *
     * @param email Email address to be validated.
     * @return True if the email address matches the pattern, false otherwise.
     */
    private boolean isEmailValid(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Validates the format of the password. Password must contain at least one uppercase letter,
     * one lowercase letter, and be at least 8 characters long.
     *
     * @param password Password to be validated.
     * @return True if the password meets the requirements, false otherwise.
     */
    private boolean isPasswordValid(String password) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        if (password == null || password.trim().length() < 8) {
            return false;
        }
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            }
        }
        return (hasUpperCase && hasLowerCase);
    }

    /**
     * Checks if two strings have the same value.
     *
     * @param string1 First string to compare.
     * @param string2 Second string to compare.
     * @return True if the strings have the same value, false otherwise.
     */
    private boolean isConfirmValid(String string1, String string2) {
        return string1.equals(string2);
    }
}

package com.example.barcodescanner.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;

/**
 * ViewModel for registering and validating a store owner.
 */
public class RegisterStoreOwnerViewModel extends ViewModel {

    // LiveData for holding the form state
    private final MutableLiveData<RegisterStoreOwnerFormState> registerStoreOwnerFormState = new MutableLiveData<>();

    // Validity checker for validating input data
    private final ValidityChecker validityChecker = new ValidityChecker();

    // Method to observe the form state LiveData
    LiveData<RegisterStoreOwnerFormState> getRegisterStoreOwnerFormState() {
        return registerStoreOwnerFormState;
    }

    // Method to validate and update the form state based on input data
    public void registerStoreOwnerDataChanged(String username, String storeName, String email, String confirmEmail,
                                              String location, int kvk, String password, String confirmPassword) {
        Integer usernameError;
        Integer storeNameError;
        Integer emailError;
        Integer confirmEmailError;
        Integer locationError;
        Integer kvkError;
        Integer passwordError;
        Integer confirmPasswordError;

        // Validate username
        if (!validityChecker.isStringValid(username)) {
            usernameError = R.string.invalid_username;
        } else {
            usernameError = null;
        }

        // Validate store name
        if (!validityChecker.isStringValid(storeName)) {
            storeNameError = R.string.invalid_store_name;
        } else {
            storeNameError = null;
        }

        // Validate email
        if (!validityChecker.isEmailValid(email)) {
            emailError = R.string.invalid_email;
        } else {
            emailError = null;
        }

        // Validate confirm email
        if (!validityChecker.isConfirmValid(email, confirmEmail)) {
            confirmEmailError = R.string.invalid_email_confirm;
        } else {
            confirmEmailError = null;
        }

        // Validate location
        if (!validityChecker.isStringValid(location)) {
            locationError = R.string.invalid_location;
        } else {
            locationError = null;
        }

        // Validate KVK
        if (!validityChecker.isKvkValid(kvk)) {
            kvkError = R.string.invalid_kvk;
        } else {
            kvkError = null;
        }

        // Validate password
        if (!validityChecker.isPasswordValid(password)) {
            passwordError = R.string.invalid_password;
        } else {
            passwordError = null;
        }

        // Validate confirm password
        if (!validityChecker.isConfirmValid(password, confirmPassword)) {
            confirmPasswordError = R.string.invalid_password_confirm;
        } else {
            confirmPasswordError = null;
        }

        // Set the new form state based on validation results
        registerStoreOwnerFormState.setValue(new RegisterStoreOwnerFormState(usernameError, storeNameError, emailError, confirmEmailError, locationError, kvkError, passwordError, confirmPasswordError));
    }
}

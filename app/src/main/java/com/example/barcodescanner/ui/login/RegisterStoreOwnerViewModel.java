package com.example.barcodescanner.ui.login;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;

public class RegisterStoreOwnerViewModel extends ViewModel {

    private final MutableLiveData<RegisterStoreOwnerFormState> registerStoreOwnerFormState = new MutableLiveData<>();

    // Checks if data is valid
    private final ValidityChecker validityChecker = new ValidityChecker();

    LiveData<RegisterStoreOwnerFormState> getRegisterStoreOwnerFormState() {
        return registerStoreOwnerFormState;
    }

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
        if (!validityChecker.isStringValid(username)) {
            usernameError = R.string.invalid_username;
        } else {
            usernameError = null;
        }
        if (!validityChecker.isStringValid(storeName)) {
            storeNameError = R.string.invalid_store_name;
        } else {
            storeNameError = null;
        }
        if (!validityChecker.isEmailValid(email)) {
            emailError = R.string.invalid_email;
        } else {
            emailError = null;
        }
        if (!validityChecker.isConfirmValid(email, confirmEmail)) {
            confirmEmailError = R.string.invalid_email_confirm;
        } else {
            confirmEmailError = null;
        }
        if (!validityChecker.isStringValid(location)) {
            locationError = R.string.invalid_location;
        } else {
            locationError = null;
        }
        if (!validityChecker.isKvkValid(kvk)) {
            kvkError = R.string.invalid_kvk;
        } else {
            kvkError = null;
        }
        if (!validityChecker.isPasswordValid(password)) {
            passwordError = R.string.invalid_password;
        } else {
            passwordError = null;
        }
        if (!validityChecker.isConfirmValid(password, confirmPassword)) {
            confirmPasswordError = R.string.invalid_password_confirm;
        } else {
            confirmPasswordError = null;
        }
        registerStoreOwnerFormState.setValue(new RegisterStoreOwnerFormState(usernameError, storeNameError, emailError, confirmEmailError, locationError, kvkError, passwordError, confirmPasswordError));
    }
}
package com.example.barcodescanner.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.barcodescanner.R;

public class RegisterStoreOwnerViewModel extends ViewModel {

    private final MutableLiveData<RegisterStoreOwnerFormState> registerStoreOwnerFormState = new MutableLiveData<>();

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
        if (!isUserNameValid(username)) {
            usernameError = R.string.invalid_username;
        } else {
            usernameError = null;
        }
        if (!isStoreNameValid(storeName)) {
            storeNameError = R.string.invalid_store_name;
        } else {
            storeNameError = null;
        }
        if (!isEmailValid(email)) {
            emailError = R.string.invalid_email;
        } else {
            emailError = null;
        }
        if (!isConfirmValid(email, confirmEmail)) {
            confirmEmailError = R.string.invalid_email_confirm;
        } else {
            confirmEmailError = null;
        }
        if (!isLocationValid(location)) {
            locationError = R.string.invalid_location;
        } else {
            locationError = null;
        }
        if (!isKvkValid(kvk)) {
            kvkError = R.string.invalid_kvk;
        } else {
            kvkError = null;
        }
        if (!isPasswordValid(password)) {
            passwordError = R.string.invalid_password;
        } else {
            passwordError = null;
        }
        if (!isConfirmValid(password, confirmPassword)) {
            confirmPasswordError = R.string.invalid_password_confirm;
        } else {
            confirmPasswordError = null;
        }
        registerStoreOwnerFormState.setValue(new RegisterStoreOwnerFormState(usernameError, storeNameError, emailError, confirmEmailError, locationError, kvkError, passwordError, confirmPasswordError));
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        return username != null && !username.trim().isEmpty();
    }

    // A placeholder store name validation check
    private boolean isStoreNameValid(String storeName) {
        return storeName != null && !storeName.trim().isEmpty();
    }

    // A placeholder email validation check
    private boolean isEmailValid(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // A placeholder location validation check
    private boolean isLocationValid(String location) {
        return location != null && !location.trim().isEmpty();
    }

    // A placeholder kvk validation check
    private boolean isKvkValid(Integer kvk) {
        return kvk != null && Integer.toString(kvk).length() == 8;
    }

    // A placeholder password validation check
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

    // Checks if two strings have the same value
    private boolean isConfirmValid(String string1, String string2) {
        return string1.equals(string2);
    }
}
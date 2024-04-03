package com.example.barcodescanner.customer;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;

public class ChangePasswordViewModel extends ViewModel {

    private final MutableLiveData<ChangePasswordFormState> changePasswordFormState = new MutableLiveData<>();

    LiveData<ChangePasswordFormState> getChangePasswordFormState() {
        return changePasswordFormState;
    }

    public void changePasswordDataChanged(String password, String confirmPassword) {
        Integer passwordError;
        Integer confirmPasswordError;
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
        changePasswordFormState.setValue(new ChangePasswordFormState(passwordError, confirmPasswordError));
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
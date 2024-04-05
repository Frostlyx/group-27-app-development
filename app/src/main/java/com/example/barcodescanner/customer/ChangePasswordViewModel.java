package com.example.barcodescanner.customer;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;
import com.example.barcodescanner.ui.login.ValidityChecker;

public class ChangePasswordViewModel extends ViewModel {

    private final MutableLiveData<ChangePasswordFormState> changePasswordFormState = new MutableLiveData<>();

    // Checks if data is valid
    private final ValidityChecker validityChecker = new ValidityChecker();

    LiveData<ChangePasswordFormState> getChangePasswordFormState() {
        return changePasswordFormState;
    }

    public void changePasswordDataChanged(String password, String confirmPassword) {
        Integer passwordError;
        Integer confirmPasswordError;
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
        changePasswordFormState.setValue(new ChangePasswordFormState(passwordError, confirmPasswordError));
    }
}
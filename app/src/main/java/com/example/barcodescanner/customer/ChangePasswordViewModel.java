package com.example.barcodescanner.customer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;
import com.example.barcodescanner.ui.login.ValidityChecker;

/**
 * ViewModel for handling change password logic in the ChangePasswordFragment.
 */
public class ChangePasswordViewModel extends ViewModel {

    // MutableLiveData for observing change in form state
    private final MutableLiveData<ChangePasswordFormState> changePasswordFormState = new MutableLiveData<>();

    // Instance of ValidityChecker to validate password and confirm password
    private final ValidityChecker validityChecker = new ValidityChecker();

    // Get LiveData of ChangePasswordFormState
    LiveData<ChangePasswordFormState> getChangePasswordFormState() {
        return changePasswordFormState;
    }

    // Validates and updates the form state based on password and confirm password input
    public void changePasswordDataChanged(String password, String confirmPassword) {
        Integer passwordError;
        Integer confirmPasswordError;

        // Validate password
        if (!validityChecker.isPasswordValid(password)) {
            passwordError = R.string.invalid_password; // Set password error message
        } else {
            passwordError = null; // Clear password error message
        }

        // Validate confirm password
        if (!validityChecker.isConfirmValid(password, confirmPassword)) {
            confirmPasswordError = R.string.invalid_password_confirm; // Set confirm password error message
        } else {
            confirmPasswordError = null; // Clear confirm password error message
        }

        // Update the form state LiveData with new ChangePasswordFormState
        changePasswordFormState.setValue(new ChangePasswordFormState(passwordError, confirmPasswordError));
    }
}

package com.example.barcodescanner.customer;

import androidx.annotation.Nullable;

/**
 * Data validation state of the change password form.
 */
class ChangePasswordFormState {

    // Error state for the password field
    @Nullable
    private final Integer passwordError;
    // Error state for the confirm password field
    @Nullable
    private final Integer confirmPasswordError;
    // Flag indicating if the form data is valid
    private final boolean isDataValid;

    // Constructor
    ChangePasswordFormState(@Nullable Integer passwordError, @Nullable Integer confirmPasswordError) {
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
        // Set the validity of the form based on error states
        this.isDataValid = passwordError == null && confirmPasswordError == null;
    }

    // Gets the password error state
    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    // Gets the confirm password error state
    @Nullable
    Integer getConfirmPasswordError() {
        return confirmPasswordError;
    }

    // Checks if the form data is valid
    boolean isDataValid() {
        return isDataValid;
    }
}

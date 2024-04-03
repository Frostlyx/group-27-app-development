package com.example.barcodescanner.customer;

import androidx.annotation.Nullable;

/**
 * Data validation state of the change password form.
 */
class ChangePasswordFormState {
    @Nullable
    private final Integer passwordError;
    @Nullable
    private final Integer confirmPasswordError;
    private final boolean isDataValid;

    ChangePasswordFormState(@Nullable Integer passwordError, @Nullable Integer confirmPasswordError) {
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
        this.isDataValid = passwordError == null && confirmPasswordError == null;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getConfirmPasswordError() {
        return confirmPasswordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
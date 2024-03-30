package com.example.barcodescanner.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
    @Nullable
    private final Integer emailError;
    @Nullable
    private final Integer passwordError;
    private final boolean isDataValid;

    LoginFormState(@Nullable Integer emailError, @Nullable Integer passwordError) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.isDataValid = emailError == null && passwordError == null;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
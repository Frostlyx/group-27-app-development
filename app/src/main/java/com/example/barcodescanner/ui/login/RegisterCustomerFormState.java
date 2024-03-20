package com.example.barcodescanner.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the register form.
 */
class RegisterCustomerFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer confirmEmailError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer confirmPasswordError;
    private boolean isDataValid;

    RegisterCustomerFormState(@Nullable Integer usernameError, @Nullable Integer emailError, @Nullable Integer confirmEmailError,
                              @Nullable Integer passwordError, @Nullable Integer confirmPasswordError) {
        this.usernameError = usernameError;
        this.emailError = emailError;
        this.confirmEmailError = confirmEmailError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
        this.isDataValid = false;
    }

    RegisterCustomerFormState(boolean isDataValid) {
        this.usernameError = null;
        this.emailError = null;
        this.confirmEmailError = null;
        this.passwordError = null;
        this.confirmPasswordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getEmailError() { return emailError; }

    @Nullable
    Integer getConfirmEmailError() {
        return confirmEmailError;
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
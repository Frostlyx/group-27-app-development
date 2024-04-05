package com.example.barcodescanner.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the register form.
 * This class holds information about validation errors in the registration form.
 */
class RegisterCustomerFormState {
    // Nullable fields to store validation errors for each form field
    @Nullable
    private final Integer usernameError;
    @Nullable
    private final Integer emailError;
    @Nullable
    private final Integer confirmEmailError;
    @Nullable
    private final Integer passwordError;
    @Nullable
    private final Integer confirmPasswordError;
    // Boolean flag indicating whether the data in the form is valid
    private final boolean isDataValid;

    // Constructor to initialize the form state with validation errors
    RegisterCustomerFormState(@Nullable Integer usernameError, @Nullable Integer emailError, @Nullable Integer confirmEmailError,
                              @Nullable Integer passwordError, @Nullable Integer confirmPasswordError) {
        this.usernameError = usernameError;
        this.emailError = emailError;
        this.confirmEmailError = confirmEmailError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
        // Checking if all errors are null to determine if the data is valid
        this.isDataValid = usernameError == null && emailError == null && confirmEmailError == null &&
                passwordError == null && confirmPasswordError == null;
    }

    // Getter methods for each validation error
    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

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

    // Method to check if the form data is valid
     boolean isDataValid() {
        return isDataValid;
    }
}

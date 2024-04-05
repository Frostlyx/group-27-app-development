package com.example.barcodescanner.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the register form for store owners.
 */
class RegisterStoreOwnerFormState {
    // Variables to hold given error messages
    @Nullable
    private final Integer usernameError;
    @Nullable
    private final Integer storeNameError;
    @Nullable
    private final Integer emailError;
    @Nullable
    private final Integer confirmEmailError;
    @Nullable
    private final Integer locationError;
    @Nullable
    private final Integer kvkError;
    @Nullable
    private final Integer passwordError;
    @Nullable
    private final Integer confirmPasswordError;
    // holds if the data is valid or not
    private final boolean isDataValid;

    /**
     * Constructor for RegisterStoreOwnerFormState.
     * Sets isDataValid to true is any of the error fields are non-null.
     */
    RegisterStoreOwnerFormState(@Nullable Integer usernameError, @Nullable Integer storeNameError,
                                @Nullable Integer emailError, @Nullable Integer confirmEmailError,
                                @Nullable Integer locationError, @Nullable Integer kvkError,
                                @Nullable Integer passwordError, @Nullable Integer confirmPasswordError) {
        this.usernameError = usernameError;
        this.storeNameError = storeNameError;
        this.emailError = emailError;
        this.confirmEmailError = confirmEmailError;
        this.locationError = locationError;
        this.kvkError = kvkError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
        this.isDataValid = usernameError == null && storeNameError == null && emailError == null && confirmEmailError == null &&
                locationError == null && kvkError == null && passwordError == null && confirmPasswordError == null;
    }

    // Getter for username error
    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    // Getter for store name error
    @Nullable
    Integer getStoreNameError() {
        return storeNameError;
    }

    // Getter for email error
    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    // Getter for confirm email error
    @Nullable
    Integer getConfirmEmailError() {
        return confirmEmailError;
    }

    // Getter for lcoation error
    @Nullable
    Integer getLocationError() {
        return locationError;
    }

    // Getter for KvK number error
    @Nullable
    Integer getKvkError() {
        return kvkError;
    }

    // Getter for password error
    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    // Getter for confirm password error
    @Nullable
    Integer getConfirmPasswordError() {
        return confirmPasswordError;
    }

    // Method to check if data is valid
    boolean isDataValid() {
        return isDataValid;
    }
}

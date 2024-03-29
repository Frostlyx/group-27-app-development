package com.example.barcodescanner.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the register form.
 */
class RegisterStoreOwnerFormState {
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
    private final boolean isDataValid;

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
        this.isDataValid = usernameError == null && storeNameError == null && emailError == null &&  confirmEmailError == null &&
                locationError == null && kvkError == null && passwordError == null && confirmPasswordError == null;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getStoreNameError() {
        return storeNameError;
    }

    @Nullable
    Integer getEmailError() { return emailError; }

    @Nullable
    Integer getConfirmEmailError() {
        return confirmEmailError;
    }

    @Nullable
    Integer getLocationError() {
        return locationError;
    }

    @Nullable
    Integer getKvkError() {
        return kvkError;
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
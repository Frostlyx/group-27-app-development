package com.example.barcodescanner.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class RegisterCustomerResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    RegisterCustomerResult(@Nullable Integer error) {
        this.error = error;
    }

    RegisterCustomerResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
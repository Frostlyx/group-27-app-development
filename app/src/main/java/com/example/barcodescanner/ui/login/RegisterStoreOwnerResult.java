package com.example.barcodescanner.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class RegisterStoreOwnerResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    RegisterStoreOwnerResult(@Nullable Integer error) {
        this.error = error;
    }

    RegisterStoreOwnerResult(@Nullable LoggedInUserView success) {
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
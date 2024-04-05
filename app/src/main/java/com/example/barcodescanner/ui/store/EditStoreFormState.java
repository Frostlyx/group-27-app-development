package com.example.barcodescanner.ui.store;

import androidx.annotation.Nullable;

/**
 * FormState for checking whether the KvK number is valid (contains 8 digits) when editing store information.
 */
public class EditStoreFormState {

    @Nullable
    private final Integer kvkError;
    private final boolean isDataValid;

    EditStoreFormState(@Nullable Integer kvkError) {
        this.kvkError = kvkError;
        this.isDataValid = kvkError == null;
    }

    @Nullable
    Integer getKvkError() {
        return kvkError;
    }

    boolean isDataValid() {
        return isDataValid;
    }

}

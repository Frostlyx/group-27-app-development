package com.example.barcodescanner.ui.store;

import androidx.annotation.Nullable;

/**
 * FormState for checking whether the KvK number is valid (contains 8 digits) when editing store information.
 */
public class EditStoreFormState {

    @Nullable
    private final Integer kvkError; // Nullable integer representing the error state of KvK number.
    private final boolean isDataValid; // Boolean flag indicating if the form data is valid.

    /**
     * Constructs an EditStoreFormState object.
     *
     * @param kvkError Nullable integer representing the error state of KvK number. Null indicates no error.
     */
    EditStoreFormState(@Nullable Integer kvkError) {
        this.kvkError = kvkError;
        this.isDataValid = kvkError == null; // If KvK error is null, data is considered valid.
    }

    /**
     * Get the KvK error state.
     *
     * @return Nullable integer representing the error state of KvK number. Null indicates no error.
     */
    @Nullable
    Integer getKvkError() {
        return kvkError;
    }

    /**
     * Check if the form data is valid.
     *
     * @return True if the form data is valid, false otherwise.
     */
    boolean isDataValid() {
        return isDataValid;
    }
}

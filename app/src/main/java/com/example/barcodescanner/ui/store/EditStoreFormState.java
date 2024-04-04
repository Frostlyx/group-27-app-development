package com.example.barcodescanner.ui.store;

import androidx.annotation.Nullable;

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

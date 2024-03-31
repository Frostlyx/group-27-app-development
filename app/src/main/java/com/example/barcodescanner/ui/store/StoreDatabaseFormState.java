package com.example.barcodescanner.ui.store;

import androidx.annotation.Nullable;

/**
 * Data validation state of all store forms.
 */
class StoreDatabaseFormState {
    @Nullable
    private final Integer nameError;
    @Nullable
    private final Integer categoryError;
    @Nullable
    private final Integer barcodeError;
    @Nullable
    private final Integer amountError;
    @Nullable
    private final Integer priceError;
    private final boolean isDataValid;

    StoreDatabaseFormState(@Nullable Integer nameError, @Nullable Integer categoryError, @Nullable Integer barcodeError,
                           @Nullable Integer amountError, @Nullable Integer priceError) {
        this.nameError = nameError;
        this.categoryError = categoryError;
        this.barcodeError = barcodeError;
        this.amountError = amountError;
        this.priceError = priceError;
        this.isDataValid = nameError == null && categoryError == null && barcodeError == null &&  amountError == null && priceError == null;
    }

    @Nullable
    Integer getNameError() {
        return nameError;
    }

    @Nullable
    Integer getCategoryError() {
        return categoryError;
    }

    @Nullable
    Integer getBarcodeError() {
        return barcodeError;
    }

    @Nullable
    Integer getAmountError() {
        return amountError;
    }

    @Nullable
    Integer getPriceError() { return priceError; }

    boolean isDataValid() {
        return isDataValid;
    }
}
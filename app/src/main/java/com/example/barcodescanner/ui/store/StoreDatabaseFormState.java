package com.example.barcodescanner.ui.store;

import androidx.annotation.Nullable;

/**
 * Represents the data validation state of all store forms.
 */
class StoreDatabaseFormState {
    // Error messages for each form field
    @Nullable
    private final Integer nameError; // Error message for the name field
    @Nullable
    private final Integer barcodeError; // Error message for the barcode field
    @Nullable
    private final Integer amountError; // Error message for the amount field
    @Nullable
    private final Integer priceError; // Error message for the price field

    // Flag indicating whether all form data is valid
    private final boolean isDataValid;

    /**
     * Constructs a new StoreDatabaseFormState.
     *
     * @param nameError    Error message for the name field, null if no error.
     * @param barcodeError Error message for the barcode field, null if no error.
     * @param amountError  Error message for the amount field, null if no error.
     * @param priceError   Error message for the price field, null if no error.
     */
    StoreDatabaseFormState(@Nullable Integer nameError, @Nullable Integer barcodeError,
                           @Nullable Integer amountError, @Nullable Integer priceError) {
        this.nameError = nameError;
        this.barcodeError = barcodeError;
        this.amountError = amountError;
        this.priceError = priceError;

        // Set isDataValid to true if all error messages are null, indicating all data is valid
        this.isDataValid = nameError == null && barcodeError == null &&
                amountError == null && priceError == null;
    }

    /**
     * Gets the error message for the name field.
     *
     * @return The error message for the name field, or null if no error.
     */
    @Nullable
    Integer getNameError() {
        return nameError;
    }

    /**
     * Gets the error message for the barcode field.
     *
     * @return The error message for the barcode field, or null if no error.
     */
    @Nullable
    Integer getBarcodeError() {
        return barcodeError;
    }

    /**
     * Gets the error message for the amount field.
     *
     * @return The error message for the amount field, or null if no error.
     */
    @Nullable
    Integer getAmountError() {
        return amountError;
    }

    /**
     * Gets the error message for the price field.
     *
     * @return The error message for the price field, or null if no error.
     */
    @Nullable
    Integer getPriceError() {
        return priceError;
    }

    /**
     * Checks if all form data is valid.
     *
     * @return True if all form data is valid, false otherwise.
     */
    boolean isDataValid() {
        return isDataValid;
    }
}

package com.example.barcodescanner.ui.store;

import androidx.annotation.Nullable;

/**
 * Data validation state of all store forms.
 */
class StoreDatabaseFormState {
    @Nullable
    private final Integer nameError;
    @Nullable
    private final Integer barcodeError;
    @Nullable
    private final Integer amountError;
    @Nullable
    private final Integer priceError;
    @Nullable final Integer discountError;
    private final boolean isDataValid;

    StoreDatabaseFormState(@Nullable Integer nameError, @Nullable Integer barcodeError,
                           @Nullable Integer amountError, @Nullable Integer priceError, @Nullable Integer discountError) {
        this.nameError = nameError;
        this.barcodeError = barcodeError;
        this.amountError = amountError;
        this.priceError = priceError;
        this.discountError = discountError;
        this.isDataValid = nameError == null && barcodeError == null &&
                amountError == null && priceError == null && discountError == null;
    }

    @Nullable
    Integer getNameError() {
        return nameError;
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

    @Nullable
    Integer getDiscountError() { return discountError; }

    boolean isDataValid() {
        return isDataValid;
    }
}
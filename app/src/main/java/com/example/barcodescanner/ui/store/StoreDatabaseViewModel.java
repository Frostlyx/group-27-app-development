package com.example.barcodescanner.ui.store;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;
import com.example.barcodescanner.ui.login.ValidityChecker;

public class StoreDatabaseViewModel extends ViewModel {

    // MutableLiveData for storing the form state of the store database
    private final MutableLiveData<StoreDatabaseFormState> storeDatabaseFormState = new MutableLiveData<>();

    // Instance of ValidityChecker to validate input data
    private final ValidityChecker validityChecker = new ValidityChecker();

    /**
     * Get the LiveData object containing the form state of the store database.
     *
     * @return LiveData object containing the form state
     */
    LiveData<StoreDatabaseFormState> getStoreDatabaseFormState() {
        return storeDatabaseFormState;
    }

    /**
     * Update the store database form state based on the changes in input data.
     *
     * @param name    Name of the item
     * @param barcode Barcode of the item
     * @param amount  Amount of the item
     * @param price   Price of the item
     */
    public void storeDatabaseDataChanged(String name, String barcode, String amount, double price) {
        Integer nameError;
        Integer barcodeError;
        Integer amountError;
        Integer priceError;

        // Check if name is valid
        if (!validityChecker.isStringValid(name)) {
            nameError = R.string.invalid_item_name;
        } else {
            nameError = null;
        }

        // Check if barcode is valid
        if (!validityChecker.isBarcodeValid(barcode)) {
            barcodeError = R.string.invalid_barcode;
        } else {
            barcodeError = null;
        }

        // Check if amount is valid
        if (!validityChecker.isStringValid(amount)) {
            amountError = R.string.invalid_amount;
        } else {
            amountError = null;
        }

        // Check if price is valid
        if (!validityChecker.isPriceValid(price)) {
            priceError = R.string.invalid_price;
        } else {
            priceError = null;
        }

        // Update the MutableLiveData with the new form state
        storeDatabaseFormState.setValue(new StoreDatabaseFormState(nameError, barcodeError, amountError, priceError));
    }
}

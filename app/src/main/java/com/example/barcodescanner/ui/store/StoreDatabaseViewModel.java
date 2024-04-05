package com.example.barcodescanner.ui.store;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;
import com.example.barcodescanner.ui.login.ValidityChecker;

public class StoreDatabaseViewModel extends ViewModel {

    private final MutableLiveData<StoreDatabaseFormState> storeDatabaseFormState = new MutableLiveData<>();

    // Checks if data is valid
    private final ValidityChecker validityChecker = new ValidityChecker();

    LiveData<StoreDatabaseFormState> getStoreDatabaseFormState() {
        return storeDatabaseFormState;
    }

    public void storeDatabaseDataChanged(String name, String barcode, String amount, double price) {
        Integer nameError;
        Integer barcodeError;
        Integer amountError;
        Integer priceError;
        if (!validityChecker.isStringValid(name)) {
            nameError = R.string.invalid_item_name;
        } else {
            nameError = null;
        }
        if (!validityChecker.isBarcodeValid(barcode)) {
            barcodeError = R.string.invalid_barcode;
        } else {
            barcodeError = null;
        }
        if (!validityChecker.isStringValid(amount)) {
            amountError = R.string.invalid_amount;
        } else {
            amountError = null;
        }
        if (!validityChecker.isPriceValid(price)) {
            priceError = R.string.invalid_price;
        } else {
            priceError = null;
        }
        storeDatabaseFormState.setValue(new StoreDatabaseFormState(nameError, barcodeError, amountError, priceError));
    }
}
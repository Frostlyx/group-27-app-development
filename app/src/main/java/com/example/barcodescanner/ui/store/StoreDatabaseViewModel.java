package com.example.barcodescanner.ui.store;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;

public class StoreDatabaseViewModel extends ViewModel {

    private final MutableLiveData<StoreDatabaseFormState> storeDatabaseFormState = new MutableLiveData<>();

    LiveData<StoreDatabaseFormState> getStoreDatabaseFormState() {
        return storeDatabaseFormState;
    }

    public void storeDatabaseDataChanged(String name, String category, String barcode, double amount, double price) {
        Integer nameError;
        Integer categoryError;
        Integer barcodeError;
        Integer amountError;
        Integer priceError;
        if (!isNameValid(name)) {
            nameError = R.string.invalid_item_name;
        } else {
            nameError = null;
        }
        if (!isCategoryValid(category)) {
            categoryError = R.string.invalid_category_name;
        } else {
            categoryError = null;
        }
        if (!isBarcodeValid(barcode)) {
            barcodeError = R.string.invalid_barcode;
        } else {
            barcodeError = null;
        }
        if (!isAmountValid(amount)) {
            amountError = R.string.invalid_amount;
        } else {
            amountError = null;
        }
        if (!isPriceValid(price)) {
            priceError = R.string.invalid_price;
        } else {
            priceError = null;
        }
        storeDatabaseFormState.setValue(new StoreDatabaseFormState(nameError, categoryError, barcodeError, amountError, priceError));
    }

    // A placeholder name validation check
    private boolean isNameValid(String name) {
        return name != null && !name.trim().isEmpty();
    }

    // A placeholder category validation check
    private boolean isCategoryValid(String category) {
        return category != null && !category.trim().isEmpty();
    }

    // A placeholder barcode validation check
    private boolean isBarcodeValid(String barcode) {
        return barcode != null && barcode.length() == 13;
    }

    // A placeholder amount validation check
    private boolean isAmountValid(Double amount) {
        return amount != null && amount > 0;
    }

    // A placeholder price validation check
    private boolean isPriceValid(Double price) {
        if (price == null || price <= 0) {
            return false;
        }
        String[] div = price.toString().split("\\.");
        return div[1].length() <= 2;
    }
}
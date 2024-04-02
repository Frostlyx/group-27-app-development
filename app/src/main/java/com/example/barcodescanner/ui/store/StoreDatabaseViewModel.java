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

    public void storeDatabaseDataChanged(String name, String barcode, String amount, double price, int discount) {
        Integer nameError;
        Integer barcodeError;
        Integer amountError;
        Integer priceError;
        Integer discountError;
        if (!isNameValid(name)) {
            nameError = R.string.invalid_item_name;
        } else {
            nameError = null;
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
        if (!isDiscountValid(discount)) {
            discountError = R.string.invalid_discount;
        } else {
            discountError = null;
        }
        storeDatabaseFormState.setValue(new StoreDatabaseFormState(nameError, barcodeError, amountError, priceError, discountError));
    }

    // A placeholder name validation check
    private boolean isNameValid(String name) {
        return name != null && !name.trim().isEmpty();
    }

    // A placeholder barcode validation check
    private boolean isBarcodeValid(String barcode) {
        return barcode != null && barcode.length() == 13;
    }

    // A placeholder amount validation check
    private boolean isAmountValid(String amount) {
        return amount != null && !amount.trim().isEmpty();
    }

    // A placeholder price validation check
    private boolean isPriceValid(Double price) {
        if (price == null || price <= 0) {
            return false;
        }
        String[] div = price.toString().split("\\.");
        return div[1].length() <= 2;
    }

    // A placeholder discount validation check
    private boolean isDiscountValid(Integer discount) {
        return discount != null && discount >= 0;
    }
}
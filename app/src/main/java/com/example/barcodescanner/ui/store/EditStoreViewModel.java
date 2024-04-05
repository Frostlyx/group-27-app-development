package com.example.barcodescanner.ui.store;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;
import com.example.barcodescanner.ui.login.ValidityChecker;

public class EditStoreViewModel extends ViewModel {

    private final MutableLiveData<EditStoreFormState> editStoreFormState = new MutableLiveData<>();

    // Checks if data is valid
    private final ValidityChecker validityChecker = new ValidityChecker();

    LiveData<EditStoreFormState> getEditStoreFormState() {
        return editStoreFormState;
    }

    public void editStoreDataChanged(int kvk) {
        Integer kvkError;
        if (!validityChecker.isKvkValid(kvk)) {
            kvkError = R.string.invalid_kvk;
        } else {
            kvkError = null;
        }
        editStoreFormState.setValue(new EditStoreFormState(kvkError));
    }
}


package com.example.barcodescanner.ui.store;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;

public class EditStoreViewModel extends ViewModel {

    private final MutableLiveData<EditStoreFormState> editStoreFormState = new MutableLiveData<>();

    LiveData<EditStoreFormState> getEditStoreFormState() {
        return editStoreFormState;
    }

    public void editStoreDataChanged(int kvk) {
        Integer kvkError;
        if (!isKvkValid(kvk)) {
            kvkError = R.string.invalid_kvk;
        } else {
            kvkError = null;
        }
        editStoreFormState.setValue(new EditStoreFormState(kvkError));
    }

    // A placeholder name validation check
    private boolean isKvkValid(Integer kvk) {
        return kvk != null && Integer.toString(kvk).length() == 8;
    }

}


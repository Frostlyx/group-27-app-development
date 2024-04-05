package com.example.barcodescanner.ui.store;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;
import com.example.barcodescanner.ui.login.ValidityChecker;

/**
 * ViewModel for editing store information.
 */
public class EditStoreViewModel extends ViewModel {

    // MutableLiveData to hold the state of the edit store form
    private final MutableLiveData<EditStoreFormState> editStoreFormState = new MutableLiveData<>();

    // Instance of ValidityChecker to check the validity of data
    private final ValidityChecker validityChecker = new ValidityChecker();

    /**
     * Gets the LiveData object representing the state of the edit store form.
     *
     * @return LiveData object representing the state of the edit store form.
     */
    LiveData<EditStoreFormState> getEditStoreFormState() {
        return editStoreFormState;
    }

    /**
     * Notifies the ViewModel that the store data has changed, and updates the form state accordingly.
     *
     * @param kvk The KvK number entered by the user.
     */
    public void editStoreDataChanged(int kvk) {
        Integer kvkError;

        // Check if the entered KvK number is valid using ValidityChecker
        if (!validityChecker.isKvkValid(kvk)) {
            // If KvK number is invalid, set the error message resource ID
            kvkError = R.string.invalid_kvk;
        } else {
            // If KvK number is valid, set error message to null
            kvkError = null;
        }

        // Update the LiveData with the new form state
        editStoreFormState.setValue(new EditStoreFormState(kvkError));
    }
}

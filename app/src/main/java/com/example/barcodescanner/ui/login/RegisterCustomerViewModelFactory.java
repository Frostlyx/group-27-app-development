package com.example.barcodescanner.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

/**
 * ViewModel provider factory to instantiate RegisterCustomerViewModel.
 * Required given RegisterCustomerViewModel has a non-empty constructor
 */
public class RegisterCustomerViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterCustomerViewModel.class)) {
            return (T) new RegisterCustomerViewModel();
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
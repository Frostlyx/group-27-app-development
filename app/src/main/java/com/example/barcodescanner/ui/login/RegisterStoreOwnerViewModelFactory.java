package com.example.barcodescanner.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

/**
 * ViewModel provider factory to instantiate RegisterStoreOwnerViewModel.
 * Required given RegisterStoreOwnerViewModel has a non-empty constructor
 */
public class RegisterStoreOwnerViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterStoreOwnerViewModel.class)) {
            return (T) new RegisterStoreOwnerViewModel();
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
package com.example.barcodescanner.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.barcodescanner.data.RegisterStoreOwnerDataSource;
import com.example.barcodescanner.data.RegisterStoreOwnerRepository;

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
            return (T) new RegisterStoreOwnerViewModel(RegisterStoreOwnerRepository.getInstance(new RegisterStoreOwnerDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
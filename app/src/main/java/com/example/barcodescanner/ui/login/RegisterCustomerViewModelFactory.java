package com.example.barcodescanner.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.barcodescanner.data.RegisterCustomerDataSource;
import com.example.barcodescanner.data.RegisterCustomerRepository;

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
            return (T) new RegisterCustomerViewModel(RegisterCustomerRepository.getInstance(new RegisterCustomerDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
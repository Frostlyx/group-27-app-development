package com.example.barcodescanner.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * This class is responsible for creating instances of LoginViewModel.
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    // Method to create ViewModel instances
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // Check if the requested ViewModel is LoginViewModel
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            // If yes, create a new instance of LoginViewModel and return
            return (T) new LoginViewModel();
        } else {
            // If not, throw an IllegalArgumentException
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

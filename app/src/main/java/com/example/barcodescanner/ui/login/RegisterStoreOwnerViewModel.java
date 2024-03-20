package com.example.barcodescanner.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.barcodescanner.data.RegisterStoreOwnerRepository;
import com.example.barcodescanner.data.Result;
import com.example.barcodescanner.data.model.User;
import com.example.barcodescanner.R;

public class RegisterStoreOwnerViewModel extends ViewModel {

    private MutableLiveData<RegisterStoreOwnerFormState> registerStoreOwnerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterStoreOwnerResult> registerStoreOwnerResult = new MutableLiveData<>();
    private RegisterStoreOwnerRepository registerStoreOwnerRepository;

    RegisterStoreOwnerViewModel(RegisterStoreOwnerRepository registerStoreOwnerRepository) {
        this.registerStoreOwnerRepository = registerStoreOwnerRepository;
    }

    LiveData<RegisterStoreOwnerFormState> getRegisterStoreOwnerFormState() {
        return registerStoreOwnerFormState;
    }

    LiveData<RegisterStoreOwnerResult> getRegisterStoreOwnerResult() {
        return registerStoreOwnerResult;
    }

    public void register(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<User> result = registerStoreOwnerRepository.register(username, password);

        if (result instanceof Result.Success) {
            User data = ((Result.Success<User>) result).getData();
            registerStoreOwnerResult.setValue(new RegisterStoreOwnerResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            registerStoreOwnerResult.setValue(new RegisterStoreOwnerResult(R.string.login_failed));
        }
    }

    public void registerStoreOwnerDataChanged(String username, String storeName, String email, String confirmEmail,
                                            String location, int kvk, String password, String confirmPassword) {
        if (!isUserNameValid(username)) {
            registerStoreOwnerFormState.setValue(new RegisterStoreOwnerFormState(R.string.invalid_username, null, null, null, null, null, null, null));

        } else if (!isStoreNameValid(storeName)) {
            registerStoreOwnerFormState.setValue(new RegisterStoreOwnerFormState(null, R.string.invalid_store_name, null, null, null, null, null, null));
        } else if (!isEmailValid(email)) {
            registerStoreOwnerFormState.setValue(new RegisterStoreOwnerFormState(null, null, R.string.invalid_email, null, null, null, null, null));
        } else if (!isConfirmValid(email, confirmEmail)) {
            registerStoreOwnerFormState.setValue(new RegisterStoreOwnerFormState(null, null, null, R.string.invalid_email_confirm, null, null, null, null));
        } else if (!isLocationValid(location)) {
            registerStoreOwnerFormState.setValue(new RegisterStoreOwnerFormState(null, null, null, null, R.string.invalid_location, null, null, null));
        } else if (!isKvkValid(kvk)) {
            registerStoreOwnerFormState.setValue(new RegisterStoreOwnerFormState(null, null, null, null, null, R.string.invalid_kvk, null, null));
        } else if (!isPasswordValid(password)) {
            registerStoreOwnerFormState.setValue(new RegisterStoreOwnerFormState(null, null, null, null, null, null, R.string.invalid_password, null));
        } else if (!isConfirmValid(password, confirmPassword)) {
            registerStoreOwnerFormState.setValue(new RegisterStoreOwnerFormState(null, null, null, null, null, null, null, R.string.invalid_password_confirm));
        } else {
            registerStoreOwnerFormState.setValue(new RegisterStoreOwnerFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        return username != null && !username.trim().isEmpty();
    }

    // A placeholder password validation check
    private boolean isStoreNameValid(String storeName) {
        return storeName != null && !storeName.trim().isEmpty();
    }

    // A placeholder email validation check
    private boolean isEmailValid(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // A placeholder password validation check
    private boolean isLocationValid(String location) {
        return location != null && !location.trim().isEmpty();
    }

    // A placeholder password validation check
    private boolean isKvkValid(Integer kvk) {
        return kvk != null && Integer.toString(kvk).length() == 8;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isConfirmValid(String string1, String string2) {
        return string1.equals(string2);
    }
}
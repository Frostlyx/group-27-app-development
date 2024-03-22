package com.example.barcodescanner.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.barcodescanner.data.RegisterCustomerRepository;
import com.example.barcodescanner.data.Result;
import com.example.barcodescanner.data.model.User;
import com.example.barcodescanner.R;

public class RegisterCustomerViewModel extends ViewModel {

    private MutableLiveData<RegisterCustomerFormState> registerCustomerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterCustomerResult> registerCustomerResult = new MutableLiveData<>();
    private RegisterCustomerRepository registerCustomerRepository;

    RegisterCustomerViewModel(RegisterCustomerRepository registerCustomerRepository) {
        this.registerCustomerRepository = registerCustomerRepository;
    }

    LiveData<RegisterCustomerFormState> getRegisterCustomerFormState() {
        return registerCustomerFormState;
    }

    LiveData<RegisterCustomerResult> getRegisterCustomerResult() {
        return registerCustomerResult;
    }

    public void register(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<User> result = registerCustomerRepository.register(username, password);

        if (result instanceof Result.Success) {
            User data = ((Result.Success<User>) result).getData();
            registerCustomerResult.setValue(new RegisterCustomerResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            registerCustomerResult.setValue(new RegisterCustomerResult(R.string.login_failed));
        }
    }

    public void registerCustomerDataChanged(String username, String email, String confirmEmail,
                                            String password, String confirmPassword) {
        if (!isUserNameValid(username)) {
            registerCustomerFormState.setValue(new RegisterCustomerFormState(R.string.invalid_username, null, null, null, null));
        } else if (!isEmailValid(email)) {
            registerCustomerFormState.setValue(new RegisterCustomerFormState(null, R.string.invalid_email, null, null, null));
        } else if (!isConfirmValid(email, confirmEmail)) {
            registerCustomerFormState.setValue(new RegisterCustomerFormState(null, null, R.string.invalid_email_confirm, null, null));
        } else if (!isPasswordValid(password)) {
            registerCustomerFormState.setValue(new RegisterCustomerFormState(null, null, null, R.string.invalid_password, null));
        } else if (!isConfirmValid(password, confirmPassword)) {
            registerCustomerFormState.setValue(new RegisterCustomerFormState(null, null, null, null, R.string.invalid_password_confirm));
        } else {
            registerCustomerFormState.setValue(new RegisterCustomerFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        return username != null && !username.trim().isEmpty();
    }

    // A placeholder email validation check
    private boolean isEmailValid(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        if (password == null || password.trim().length() < 8) {
            return false;
        }
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            }
        }
        return (hasUpperCase && hasLowerCase);
    }

    private boolean isConfirmValid(String string1, String string2) {
        return string1.equals(string2);
    }
}
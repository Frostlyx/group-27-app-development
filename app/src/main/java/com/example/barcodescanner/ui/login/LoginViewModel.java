package com.example.barcodescanner.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public void loginDataChanged(String email, String password) {
        Integer emailError;
        Integer passwordError;
        if (!isEmailValid(email)) {
            emailError = R.string.invalid_email;
        } else {
            emailError = null;
        }
        if (!isPasswordValid(password)) {
            passwordError = R.string.invalid_password;
        } else {
                passwordError = null;
        }
        loginFormState.setValue(new LoginFormState(emailError, passwordError));
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
}
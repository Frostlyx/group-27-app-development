package com.example.barcodescanner.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.barcodescanner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterCustomerViewModel extends ViewModel {

    private final MutableLiveData<RegisterCustomerFormState> registerCustomerFormState = new MutableLiveData<>();

    LiveData<RegisterCustomerFormState> getRegisterCustomerFormState() {
        return registerCustomerFormState;
    }

    public void registerCustomerDataChanged(String username, String email, String confirmEmail,
                                            String password, String confirmPassword) {
        Integer usernameError;
        Integer emailError;
        Integer confirmEmailError;
        Integer passwordError;
        Integer confirmPasswordError;
        if (!isUserNameValid(username)) {
            usernameError = R.string.invalid_username;
        } else {
            usernameError = null;
        }
        if (!isEmailValid(email)) {
            emailError = R.string.invalid_email;
        } else {
            emailError = null;
        }
        if (!isConfirmValid(email, confirmEmail)) {
            confirmEmailError = R.string.invalid_email_confirm;
        } else {
            confirmEmailError = null;
        }
        if (!isPasswordValid(password)) {
            passwordError = R.string.invalid_password;
        } else {
            passwordError = null;
        }
        if (!isConfirmValid(password, confirmPassword)) {
            confirmPasswordError = R.string.invalid_password_confirm;
        } else {
            confirmPasswordError = null;
        }
        registerCustomerFormState.setValue(new RegisterCustomerFormState(usernameError, emailError, confirmEmailError, passwordError, confirmPasswordError));
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

    // Checks if two strings have the same value
    private boolean isConfirmValid(String string1, String string2) {
        return string1.equals(string2);
    }
}
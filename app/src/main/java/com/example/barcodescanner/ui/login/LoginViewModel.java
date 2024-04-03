package com.example.barcodescanner.ui.login;

import android.util.Patterns;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    boolean alreadyExists = false;

    public void loginDataChanged(String username, String password) {
        Integer usernameError;
        Integer passwordError;
        if (!isUserNameValid(username)) {
            usernameError = R.string.invalid_username;
        } else {
            usernameError = null;
        }
        if (!isPasswordValid(password)) {
            passwordError = R.string.invalid_password;
        } else {
                passwordError = null;
        }
        loginFormState.setValue(new LoginFormState(usernameError, passwordError));
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
        referenceProfile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                outer : for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        if(username.equalsIgnoreCase(userSnapshot.child("username").getValue(String.class))) {
                            alreadyExists = true;
                            break outer;
                        }
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return username != null && !username.trim().isEmpty() && !alreadyExists;
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
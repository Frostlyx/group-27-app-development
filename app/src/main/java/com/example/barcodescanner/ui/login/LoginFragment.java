package com.example.barcodescanner.ui.login;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.example.barcodescanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Login side fragment that allows the user to login
 * as a customer or store owner.
 */
public class LoginFragment extends Fragment {

    FirebaseAuth mAuth;
    private LoginViewModel loginViewModel;
    private boolean isDataValid;
    // Placeholder value to not crash the app when a nonexistent username gets filled in
    String email = "not true";

    // Default constructor
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets up data validation model
        loginViewModel = new LoginViewModel();
        isDataValid = false;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Sets up buttons
        final Button backButton = view.findViewById(R.id.back);
        final Button loginButton = view.findViewById(R.id.login);
        final Button forgotPasswordButton = view.findViewById(R.id.forgotPassword);
        final EditText usernameEditText = view.findViewById(R.id.username);
        final EditText passwordEditText = view.findViewById(R.id.password);
        final ProgressBar loadingProgressBar = view.findViewById(R.id.loading);

        // Observing login form state
        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            isDataValid = loginFormState.isDataValid();
            // If an invalid username is filled in, show an error
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            // If an incorrect password is filled in, show an error
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        // TextWatcher for login data changes
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        // Back button click listener
        backButton.setOnClickListener(v -> {
            if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                // Sends you back to the welcome pag
                ((WelcomeActivity) getActivity()).welcomeActivity();
            }
        });

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference referenceCustomers= referenceProfile.child("Customers");

        // Login button click listener
        loginButton.setOnClickListener(v -> {
            if (!isDataValid) {
                return;
            }
            // Shows a loading bar while trying to login
            loadingProgressBar.setVisibility(View.VISIBLE);
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Checking user credentials
            referenceProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    outer : for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            if(username.equalsIgnoreCase(userSnapshot.child("username").getValue().toString())) {
                                email = userSnapshot.child("email").getValue(String.class);
                                break outer;
                            }
                        }
                    }
                    // Authenticating user with Firebase
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if (mAuth.getCurrentUser() != null) {
                                referenceCustomers.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(username).build();
                                        mAuth.getCurrentUser().updateProfile(profileUpdates);
                                        // Sends the user to the correct activity
                                        if (dataSnapshot.hasChild(mAuth.getCurrentUser().getUid()) &&getActivity() != null && getActivity() instanceof WelcomeActivity){
                                            // Sends the customer to the customer activity
                                            ((WelcomeActivity) getActivity()).customerActivity();
                                        } else if (!dataSnapshot.hasChild(mAuth.getCurrentUser().getUid()) && getActivity() != null && getActivity() instanceof WelcomeActivity) {
                                            // Sends the store owner to the store owner activity
                                            ((WelcomeActivity) getActivity()).storeActivity();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        } else {
                            // Shows exception if credentials are incorrect or if login failed
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                showLoginFailed(R.string.wrong_username_password);
                            } else {
                                showLoginFailed(R.string.login_failed);
                            }
                            loadingProgressBar.setVisibility(View.GONE);
                        }
                    });
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        });

        // Forgot password button click listener
        forgotPasswordButton.setOnClickListener(v -> {
            if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                // Sends you to the forgot password page
                ((WelcomeActivity) getActivity()).replaceFragment(new ForgotPasswordFragment());
            }
        });
    }

    // Method to show login failed message
    private void showLoginFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }

    // Method to handle configuration changes
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Check if the orientation has changed
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Replace the current fragment with the landscape version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.login_container, new LoginFragment())
                    .commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Replace the current fragment with the portrait version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.login_container, new LoginFragment())
                    .commit();
        }
    }

}

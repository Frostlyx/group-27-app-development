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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Login side fragment to allow store owners to register an account.
 */
public class RegisterStoreOwnerFragment extends Fragment {

    FirebaseAuth mAuth;
    private RegisterStoreOwnerViewModel registerStoreOwnerViewModel;
    private boolean isDataValid;
    private boolean alreadyExists;

    public RegisterStoreOwnerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets up a new store owner view model
        registerStoreOwnerViewModel = new RegisterStoreOwnerViewModel();
        isDataValid = false;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_store_owner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Getting references to UI elements
        final Button backButton = view.findViewById(R.id.back);
        final Button registerButton = view.findViewById(R.id.register);
        final EditText usernameEditText = view.findViewById(R.id.username);
        final EditText storeNameEditText = view.findViewById(R.id.store_name);
        final EditText emailEditText = view.findViewById(R.id.email);
        final EditText confirmEmailEditText = view.findViewById(R.id.confirm_email);
        final EditText locationEditText = view.findViewById(R.id.location);
        final EditText kvkEditText = view.findViewById(R.id.kvk);
        final EditText passwordEditText = view.findViewById(R.id.password);
        final EditText confirmPasswordEditText = view.findViewById(R.id.confirm_password);
        final ProgressBar loadingProgressBar = view.findViewById(R.id.loading);

        // Observing changes in register form state
        registerStoreOwnerViewModel.getRegisterStoreOwnerFormState().observe(getViewLifecycleOwner(), registerStoreOwnerFormState -> {
            if (registerStoreOwnerFormState == null) {
                return;
            }
            // Checks if credentials are valid
            isDataValid = registerStoreOwnerFormState.isDataValid();

            // Setting error message for an invalid username
            if (registerStoreOwnerFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(registerStoreOwnerFormState.getUsernameError()));
            }
            // Setting error message for an invalid store name
            if (registerStoreOwnerFormState.getStoreNameError() != null) {
                storeNameEditText.setError(getString(registerStoreOwnerFormState.getStoreNameError()));
            }
            // Setting error message for an incorrect email
            if (registerStoreOwnerFormState.getEmailError() != null) {
                emailEditText.setError(getString(registerStoreOwnerFormState.getEmailError()));
            }
            // Setting error message when email and confirm email do not match
            if (registerStoreOwnerFormState.getConfirmEmailError() != null) {
                confirmEmailEditText.setError(getString(registerStoreOwnerFormState.getConfirmEmailError()));
            }
            // Setting error message for an invalid location
            if (registerStoreOwnerFormState.getLocationError() != null) {
                locationEditText.setError(getString(registerStoreOwnerFormState.getLocationError()));
            }
            // Setting error message for an incorrect KvK number
            if (registerStoreOwnerFormState.getKvkError() != null) {
                kvkEditText.setError(getString(registerStoreOwnerFormState.getKvkError()));
            }
            // Setting error message for an invalid password
            if (registerStoreOwnerFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(registerStoreOwnerFormState.getPasswordError()));
            }
            // Setting error message when password and confirm password do not match
            if (registerStoreOwnerFormState.getConfirmPasswordError() != null) {
                confirmPasswordEditText.setError(getString(registerStoreOwnerFormState.getConfirmPasswordError()));
            }
        });

        // TextWatcher to observe changes in form fields
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
                // Validate form data and check if username already exists
                int kvkText;
                try{
                    kvkText = Integer.parseInt(kvkEditText.getText().toString());
                } catch (NumberFormatException e) {
                    kvkText = 0;
                }
                registerStoreOwnerViewModel.registerStoreOwnerDataChanged(usernameEditText.getText().toString(), storeNameEditText.getText().toString(),
                        emailEditText.getText().toString(), confirmEmailEditText.getText().toString(),
                        locationEditText.getText().toString(), kvkText,
                        passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString());
                String username = usernameEditText.getText().toString();
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
                // Listener to check if username already exists when filled in
                referenceProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        outer : for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                if(username.equalsIgnoreCase(userSnapshot.child("username").getValue(String.class))) {
                                    alreadyExists = true;
                                    break outer;
                                } else {
                                    alreadyExists = false;
                                }
                            }
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        };
        // Adding TextWatcher to each form field
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        storeNameEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        confirmEmailEditText.addTextChangedListener(afterTextChangedListener);
        locationEditText.addTextChangedListener(afterTextChangedListener);
        kvkEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener);

        // Back button click listener
        backButton.setOnClickListener(v -> {
            if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                // Sends you back to the welcome page
                ((WelcomeActivity) getActivity()).welcomeActivity();
            }
        });

        // Register button click listener
        registerButton.setOnClickListener(v -> {
            // Check if form data is valid and if username already exists
            if (!isDataValid) {
                showRegisterStoreOwnerFailed(R.string.data_failed);
                return;
            } else if (alreadyExists) {
                usernameEditText.setError("This username is taken. Please enter another.");
                return;
            }
            loadingProgressBar.setVisibility(View.VISIBLE);
            // Gets filled in username and password
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Create user with email and password
            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // If user creation is successful
                    if (mAuth.getCurrentUser() != null) {
                        // Write store owner details to Firebase database
                        ReadWriteUserDetails writeCustomerDetails = new ReadWriteUserDetails(usernameEditText.getText().toString(),emailEditText.getText().toString(), passwordEditText.getText().toString(), kvkEditText.getText().toString(), locationEditText.getText().toString(), storeNameEditText.getText().toString());
                        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
                        DatabaseReference referenceStoreOwners= referenceProfile.child("Store Owners");
                        DatabaseReference referenceStore = FirebaseDatabase.getInstance().getReference("Stores");
                        referenceStore.child(mAuth.getCurrentUser().getUid());
                        referenceStoreOwners.child(mAuth.getCurrentUser().getUid()).setValue(writeCustomerDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // Navigate to store owner activity upon successful registration
                                if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                                    ((WelcomeActivity) getActivity()).storeActivity();
                                }
                            }
                        });

                    }
                } else {
                    // If user creation fails, show appropriate error message
                    Exception exception = task.getException();
                    if (exception instanceof FirebaseAuthUserCollisionException) { // email taken
                        showRegisterStoreOwnerFailed(R.string.email_exists);
                    } else if (exception instanceof FirebaseAuthInvalidCredentialsException) { // invalid email
                        showRegisterStoreOwnerFailed(R.string.invalid_email);
                    } else { // generic error message
                        showRegisterStoreOwnerFailed(R.string.register_failed);
                    }
                    loadingProgressBar.setVisibility(View.GONE);
                }
            });
        });
    }

    // Method to show registration failed message
    private void showRegisterStoreOwnerFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }

    // Method to handle orientation change
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if the orientation has changed
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Replace the current fragment with the landscape version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.layout_default, new RegisterStoreOwnerFragment())
                    .commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Replace the current fragment with the portrait version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.layout_default, new RegisterStoreOwnerFragment())
                    .commit();
        }
    }
}
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
import androidx.lifecycle.ViewModelProvider;
import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.ProductModel;
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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Fragment responsible for registering new customers.
 */
public class RegisterCustomerFragment extends Fragment {

    FirebaseAuth mAuth;
    private RegisterCustomerViewModel registerCustomerViewModel;
    private boolean isDataValid;
    private boolean alreadyExists;

    private List<ProductModel> ShoppingList;

    private List<ProductModel> FavouriteList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerCustomerViewModel = new ViewModelProvider(this, new RegisterCustomerViewModelFactory())
                .get(RegisterCustomerViewModel.class);
        isDataValid = false;
        alreadyExists = false;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Getting references to UI elements
        final Button backButton = view.findViewById(R.id.back);
        final Button registerButton = view.findViewById(R.id.register);
        final EditText usernameEditText = view.findViewById(R.id.username);
        final EditText emailEditText = view.findViewById(R.id.email);
        final EditText confirmEmailEditText = view.findViewById(R.id.confirm_email);
        final EditText passwordEditText = view.findViewById(R.id.password);
        final EditText confirmPasswordEditText = view.findViewById(R.id.confirm_password);
        final ProgressBar loadingProgressBar = view.findViewById(R.id.loading);

        // Observing changes in register form state
        registerCustomerViewModel.getRegisterCustomerFormState().observe(getViewLifecycleOwner(), registerCustomerFormState -> {
            if (registerCustomerFormState == null) {
                return;
            }
            isDataValid = registerCustomerFormState.isDataValid();

            // Setting error messages for each form field if applicable
            if (registerCustomerFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(registerCustomerFormState.getUsernameError()));
            }
            if (registerCustomerFormState.getEmailError() != null) {
                emailEditText.setError(getString(registerCustomerFormState.getEmailError()));
            }
            if (registerCustomerFormState.getConfirmEmailError() != null) {
                confirmEmailEditText.setError(getString(registerCustomerFormState.getConfirmEmailError()));
            }
            if (registerCustomerFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(registerCustomerFormState.getPasswordError()));
            }
            if (registerCustomerFormState.getConfirmPasswordError() != null) {
                confirmPasswordEditText.setError(getString(registerCustomerFormState.getConfirmPasswordError()));
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
                registerCustomerViewModel.registerCustomerDataChanged(usernameEditText.getText().toString(),
                        emailEditText.getText().toString(), confirmEmailEditText.getText().toString(),
                        passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString());
                String username = usernameEditText.getText().toString();
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
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
        emailEditText.addTextChangedListener(afterTextChangedListener);
        confirmEmailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener);

        // Back button click listener
        backButton.setOnClickListener(v -> {
            if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                ((WelcomeActivity) getActivity()).welcomeActivity();
            }
        });

        // Register button click listener
        registerButton.setOnClickListener(v -> {
            // Check if form data is valid and if username already exists
            if (!isDataValid) {
                showRegisterCustomerFailed(R.string.data_failed);
                return;
            } else if (alreadyExists) {
                usernameEditText.setError("This username is taken. Please enter another.");
                return;
            }
            loadingProgressBar.setVisibility(View.VISIBLE);
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Create user with email and password
            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // If user creation is successful
                    if (mAuth.getCurrentUser() != null) {
                        // Write customer details to Firebase database
                        ReadWriteCustomerDetails writeCustomerDetails = new ReadWriteCustomerDetails(usernameEditText.getText().toString(), emailEditText.getText().toString(), passwordEditText.getText().toString(),ShoppingList, FavouriteList);
                        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
                        DatabaseReference referenceCustomers = referenceProfile.child("Customers");
                        referenceCustomers.child(mAuth.getCurrentUser().getUid()).setValue(writeCustomerDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // Navigate to customer activity upon successful registration
                                if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                                    ((WelcomeActivity) getActivity()).customerActivity();
                                }
                            }
                        });

                    }
                } else {
                    // If user creation fails, show appropriate error message
                    Exception exception = task.getException();
                    if (exception instanceof FirebaseAuthUserCollisionException) {
                        showRegisterCustomerFailed(R.string.email_exists);
                    } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                        showRegisterCustomerFailed(R.string.invalid_email);
                    } else {
                        showRegisterCustomerFailed(R.string.register_failed);
                    }
                    loadingProgressBar.setVisibility(View.GONE);
                }
            });
        });
    }

    // Method to show registration failed message
    private void showRegisterCustomerFailed(@StringRes Integer errorString) {
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
                    .replace(R.id.login_container, new RegisterCustomerFragment())
                    .commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Replace the current fragment with the portrait version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.login_container, new RegisterCustomerFragment())
                    .commit();
        }
    }

}

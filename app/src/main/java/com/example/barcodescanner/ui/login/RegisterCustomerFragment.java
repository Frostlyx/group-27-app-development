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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterCustomerFragment extends Fragment {

    FirebaseAuth mAuth;
    private RegisterCustomerViewModel registerCustomerViewModel;
    private boolean isDataValid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerCustomerViewModel = new ViewModelProvider(this, new RegisterCustomerViewModelFactory())
                .get(RegisterCustomerViewModel.class);
        isDataValid = false;
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

        final Button backButton = view.findViewById(R.id.back);
        final Button registerButton = view.findViewById(R.id.register);
        final EditText usernameEditText = view.findViewById(R.id.username);
        final EditText emailEditText = view.findViewById(R.id.email);
        final EditText confirmEmailEditText = view.findViewById(R.id.confirm_email);
        final EditText passwordEditText = view.findViewById(R.id.password);
        final EditText confirmPasswordEditText = view.findViewById(R.id.confirm_password);
        final ProgressBar loadingProgressBar = view.findViewById(R.id.loading);

        registerCustomerViewModel.getRegisterCustomerFormState().observe(getViewLifecycleOwner(), registerCustomerFormState -> {
            if (registerCustomerFormState == null) {
                return;
            }
            isDataValid = registerCustomerFormState.isDataValid();

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
                registerCustomerViewModel.registerCustomerDataChanged(usernameEditText.getText().toString(),
                        emailEditText.getText().toString(), confirmEmailEditText.getText().toString(),
                        passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        confirmEmailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener);

        backButton.setOnClickListener(v -> {
            if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                ((WelcomeActivity) getActivity()).welcomeActivity();
            }
        });

        registerButton.setOnClickListener(v -> {
            if (!isDataValid) {
                return;
            }
            loadingProgressBar.setVisibility(View.VISIBLE);
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (mAuth.getCurrentUser() != null) {
                        updateUiWithUser(mAuth.getCurrentUser());
                        if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                            ((WelcomeActivity) getActivity()).customerActivity();
                        }
                    }
                } else {
                    Exception exception = task.getException();
                    if (exception instanceof FirebaseAuthUserCollisionException) {
                        showRegisterCustomerFailed(R.string.email_exists);
                    } else {
                        showRegisterCustomerFailed(R.string.register_failed);
                    }
                    loadingProgressBar.setVisibility(View.GONE);
                }
            });
        });
    }

    private void updateUiWithUser(FirebaseUser user) {
        String welcome = getString(R.string.welcome) + user.getDisplayName();
        // TODO : initiate successful logged in experience
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(getContext().getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        }
    }

    private void showRegisterCustomerFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }

    //changing rotation
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
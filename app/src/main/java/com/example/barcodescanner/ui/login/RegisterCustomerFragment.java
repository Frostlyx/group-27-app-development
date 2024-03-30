package com.example.barcodescanner.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.barcodescanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

        registerCustomerViewModel.getRegisterCustomerFormState().observe(getViewLifecycleOwner(), new Observer<RegisterCustomerFormState>() {
            @Override
            public void onChanged(@Nullable RegisterCustomerFormState registerCustomerFormState) {
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
            }
        });

        registerCustomerViewModel.getRegisterCustomerResult().observe(getViewLifecycleOwner(), new Observer<RegisterCustomerResult>() {
            @Override
            public void onChanged(@Nullable RegisterCustomerResult registerCustomerResult) {
                if (registerCustomerResult == null || !isDataValid) {
                    return;
                }
                if (registerCustomerResult.getError() != null) {
                    showRegisterCustomerFailed(registerCustomerResult.getError());
                }
                if (registerCustomerResult.getSuccess() != null) {
                    updateUiWithUser(registerCustomerResult.getSuccess());
                    if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                        ((WelcomeActivity) getActivity()).customerActivity();
                    }
                }
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
        confirmPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registerCustomerViewModel.register(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                    ((WelcomeActivity) getActivity()).welcomeActivity();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCustomerViewModel.register(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                String email, password;
                email = String.valueOf(emailEditText.getText());
                password = String.valueOf(passwordEditText.getText());
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                                        ((WelcomeActivity) getActivity()).customerActivity();
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                }
                            }
                        });
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
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
}
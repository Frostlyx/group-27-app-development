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
public class RegisterStoreOwnerFragment extends Fragment {

    FirebaseAuth mAuth;
    private RegisterStoreOwnerViewModel registerStoreOwnerViewModel;
    private boolean isDataValid;

    public RegisterStoreOwnerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerStoreOwnerViewModel = new ViewModelProvider(this, new RegisterStoreOwnerViewModelFactory())
                .get(RegisterStoreOwnerViewModel.class);
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

        registerStoreOwnerViewModel.getRegisterStoreOwnerFormState().observe(getViewLifecycleOwner(), new Observer<RegisterStoreOwnerFormState>() {
            @Override
            public void onChanged(@Nullable RegisterStoreOwnerFormState registerStoreOwnerFormState) {
                if (registerStoreOwnerFormState == null) {
                    return;
                }
                isDataValid = registerStoreOwnerFormState.isDataValid();

                if (registerStoreOwnerFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registerStoreOwnerFormState.getUsernameError()));
                }
                if (registerStoreOwnerFormState.getStoreNameError() != null) {
                    storeNameEditText.setError(getString(registerStoreOwnerFormState.getStoreNameError()));
                }
                if (registerStoreOwnerFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registerStoreOwnerFormState.getEmailError()));
                }
                if (registerStoreOwnerFormState.getConfirmEmailError() != null) {
                    confirmEmailEditText.setError(getString(registerStoreOwnerFormState.getConfirmEmailError()));
                }
                if (registerStoreOwnerFormState.getLocationError() != null) {
                    locationEditText.setError(getString(registerStoreOwnerFormState.getLocationError()));
                }
                if (registerStoreOwnerFormState.getKvkError() != null) {
                    kvkEditText.setError(getString(registerStoreOwnerFormState.getKvkError()));
                }
                if (registerStoreOwnerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerStoreOwnerFormState.getPasswordError()));
                }
                if (registerStoreOwnerFormState.getConfirmPasswordError() != null) {
                    confirmPasswordEditText.setError(getString(registerStoreOwnerFormState.getConfirmPasswordError()));
                }
            }
        });

        registerStoreOwnerViewModel.getRegisterStoreOwnerResult().observe(getViewLifecycleOwner(), new Observer<RegisterStoreOwnerResult>() {
            @Override
            public void onChanged(@Nullable RegisterStoreOwnerResult registerStoreOwnerResult) {
                if (registerStoreOwnerResult == null || !isDataValid) {
                    return;
                }
                if (registerStoreOwnerResult.getError() != null) {
                    showRegisterStoreOwnerFailed(registerStoreOwnerResult.getError());
                }
                if (registerStoreOwnerResult.getSuccess() != null) {
                    updateUiWithUser(registerStoreOwnerResult.getSuccess());
                    if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                        ((WelcomeActivity) getActivity()).storeActivity();
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
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        storeNameEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        confirmEmailEditText.addTextChangedListener(afterTextChangedListener);
        locationEditText.addTextChangedListener(afterTextChangedListener);
        kvkEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registerStoreOwnerViewModel.register(usernameEditText.getText().toString(),
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
                registerStoreOwnerViewModel.register(usernameEditText.getText().toString(),
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
                                        ((WelcomeActivity) getActivity()).storeActivity();
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

    private void showRegisterStoreOwnerFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }
}
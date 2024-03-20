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

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.barcodescanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterStoreOwnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterStoreOwnerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RegisterStoreOwnerViewModel registerStoreOwnerViewModel;
    private boolean isDataValid;

    public RegisterStoreOwnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterStoreOwnerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterStoreOwnerFragment newInstance(String param1, String param2) {
        RegisterStoreOwnerFragment fragment = new RegisterStoreOwnerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        registerStoreOwnerViewModel = new ViewModelProvider(this, new RegisterStoreOwnerViewModelFactory())
                .get(RegisterStoreOwnerViewModel.class);
        isDataValid = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_store_owner, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
                if (registerStoreOwnerResult == null) {
                    return;
                }
                if (!isDataValid) {
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
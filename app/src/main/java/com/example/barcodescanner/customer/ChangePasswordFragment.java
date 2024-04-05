package com.example.barcodescanner.customer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.barcodescanner.R;
import com.example.barcodescanner.ui.store.StoreActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {

    FirebaseAuth mAuth;
    private ChangePasswordViewModel changePasswordViewModel;
    private boolean isDataValid;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        changePasswordViewModel = new ChangePasswordViewModel();
        isDataValid = false;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button backButton = view.findViewById(R.id.back);
        final Button changePasswordButton = view.findViewById(R.id.change_password);
        final EditText passwordEditText = view.findViewById(R.id.password);
        final EditText confirmPasswordEditText = view.findViewById(R.id.confirm_password);
        DatabaseReference referenceCustomer = FirebaseDatabase.getInstance().getReference("Users").child("Customers").child(mAuth.getCurrentUser().getUid()).child("password");
        DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Users").child("Store Owners").child(mAuth.getCurrentUser().getUid()).child("password");


        changePasswordViewModel.getChangePasswordFormState().observe(getViewLifecycleOwner(), changePasswordFormState -> {
            if (changePasswordFormState == null) {
                return;
            }
            isDataValid = changePasswordFormState.isDataValid();
            if (changePasswordFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(changePasswordFormState.getPasswordError()));
            }
            if (changePasswordFormState.getConfirmPasswordError() != null) {
                confirmPasswordEditText.setError(getString(changePasswordFormState.getConfirmPasswordError()));
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
                changePasswordViewModel.changePasswordDataChanged(
                        passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString());
            }
        };
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null && getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).replaceFragment(new ProfileFragment(), "Profile fragment");
                } else if (getActivity() != null && getActivity() instanceof StoreActivity) {
                    ((StoreActivity) getActivity()).replaceFragment(new ProfileFragment(), "Profile fragment");
                }
            }
        });


        changePasswordButton.setOnClickListener(v -> {
            if (!isDataValid) {
                return;
            }
            String password = passwordEditText.getText().toString();
            mAuth.getCurrentUser().updatePassword(password);
            Toast.makeText(getContext().getApplicationContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
            if (getActivity() != null && getActivity() instanceof MainActivity) {
                referenceCustomer.setValue(password);
                ((MainActivity) getActivity()).replaceFragment(new ProfileFragment(), "Profile fragment");
            } else if (getActivity() != null && getActivity() instanceof StoreActivity) {
                referenceStores.setValue(password);
                ((StoreActivity) getActivity()).replaceFragment(new ProfileFragment(), "Profile fragment");
            }
        });
    }
}
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
 * Customer side fragment to allow users to change their password.
 */
public class ChangePasswordFragment extends Fragment {

    // Firebase authentication instance
    FirebaseAuth mAuth;
    // ViewModel for handling change password logic
    private ChangePasswordViewModel changePasswordViewModel;
    // Flag to track if the form data is valid
    private boolean isDataValid;

    // Constructor
    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    // Called when the fragment is created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewModel and Firebase authentication
        changePasswordViewModel = new ChangePasswordViewModel();
        isDataValid = false;
        mAuth = FirebaseAuth.getInstance();
    }

    // Called to create the fragment's view hierarchy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    // Called immediately after onCreateView()
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find views and Firebase database references
        final Button backButton = view.findViewById(R.id.back);
        final Button changePasswordButton = view.findViewById(R.id.change_password);
        final EditText passwordEditText = view.findViewById(R.id.password);
        final EditText confirmPasswordEditText = view.findViewById(R.id.confirm_password);
        DatabaseReference referenceCustomer = FirebaseDatabase.getInstance().getReference("Users").child("Customers").child(mAuth.getCurrentUser().getUid()).child("password");
        DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Users").child("Store Owners").child(mAuth.getCurrentUser().getUid()).child("password");

        // Observe form state changes
        changePasswordViewModel.getChangePasswordFormState().observe(getViewLifecycleOwner(), changePasswordFormState -> {
            if (changePasswordFormState == null) {
                return;
            }
            isDataValid = changePasswordFormState.isDataValid();
            // Set error messages for password and confirm password fields
            if (changePasswordFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(changePasswordFormState.getPasswordError()));
            }
            if (changePasswordFormState.getConfirmPasswordError() != null) {
                confirmPasswordEditText.setError(getString(changePasswordFormState.getConfirmPasswordError()));
            }
        });

        // TextWatcher to listen for text changes in password and confirm password fields
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
                // Notify ViewModel of data changes
                changePasswordViewModel.changePasswordDataChanged(
                        passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString());
            }
        };
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener);

        // Back button click listener
        backButton.setOnClickListener(v -> {
            // Navigate back to profile fragment based on activity type
            if (getActivity() != null && getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).replaceFragment(new ProfileFragment(), "Profile fragment");
            } else if (getActivity() != null && getActivity() instanceof StoreActivity) {
                ((StoreActivity) getActivity()).replaceFragment(new ProfileFragment(), "Profile fragment");
            }
        });

        // Change password button click listener
        changePasswordButton.setOnClickListener(v -> {
            // Check if form data is valid
            if (!isDataValid) {
                return;
            }
            // Update password in Firebase authentication
            String password = passwordEditText.getText().toString();
            mAuth.getCurrentUser().updatePassword(password);
            Toast.makeText(requireContext().getApplicationContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
            // Update password in Firebase Realtime Database and navigate back to profile fragment
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

package com.example.barcodescanner.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import com.example.barcodescanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * login side fragment to allow the user to reset their password when forgotten.
 */
public class ForgotPasswordFragment extends Fragment {

    // Email that receives a link to reset password
    String email;

    // Default constructor
    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Getting references to UI elements
        final Button backButton = view.findViewById(R.id.back);
        final Button passwordResetButton = view.findViewById(R.id.send_code);
        final EditText editEmail = view.findViewById(R.id.email);
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");

        // Back button click listener
        backButton.setOnClickListener(v -> {
            if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                // Returns back to login page
                ((WelcomeActivity) getActivity()).replaceFragment(new LoginFragment());
            }
        });

        // Password reset button click listener
        passwordResetButton.setOnClickListener(v ->
                referenceProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        outer : for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                // Checking if email matches the provided username
                                if(editEmail.getText().toString().equalsIgnoreCase(userSnapshot.child("username").getValue(String.class))) {
                                    email = userSnapshot.child("email").getValue(String.class);
                                    break outer;
                                }
                            }
                        }
                        // Sending password reset email
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        if (getContext() != null && getContext().getApplicationContext() != null) {
                                            // Showing success message
                                            Toast.makeText(getContext().getApplicationContext(), "A link has been sent your mail", Toast.LENGTH_LONG).show();
                                        }
                                        if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                                            // Navigating back to login fragment
                                            ((WelcomeActivity) getActivity()).replaceFragment(new LoginFragment());
                                        }
                                    } else {
                                        Exception exception = task.getException();
                                        if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                            // Showing error message for invalid email
                                            showForgotPasswordFailed(R.string.invalid_email);
                                        } else {
                                            // Showing generic error message
                                            showForgotPasswordFailed(R.string.code_failed);
                                        }
                                    }
                                });

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }));
    }

    // Method to show error message
    private void showForgotPasswordFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            // Toast to show error message
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }
}

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

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {

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

        final Button backButton = view.findViewById(R.id.back);
        final Button passwordResetButton = view.findViewById(R.id.send_code);
        final EditText editEmail = view.findViewById(R.id.email);

        backButton.setOnClickListener(v -> {
            if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                ((WelcomeActivity) getActivity()).replaceFragment(new LoginFragment());
            }
        });

        passwordResetButton.setOnClickListener(v -> FirebaseAuth.getInstance().sendPasswordResetEmail(editEmail.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (getContext() != null && getContext().getApplicationContext() != null) {
                            Toast.makeText(getContext().getApplicationContext(), "A link has been sent your mail", Toast.LENGTH_LONG).show();
                        }
                        if (getActivity() != null && getActivity() instanceof WelcomeActivity) {
                            ((WelcomeActivity) getActivity()).replaceFragment(new LoginFragment());
                        }
                    } else {
                        Exception exception = task.getException();
                        if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                            showForgotPasswordFailed(R.string.invalid_email);
                        } else {
                            showForgotPasswordFailed(R.string.code_failed);
                        }
                    }
                }));
    }

    private void showForgotPasswordFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }
}
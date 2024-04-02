package com.example.barcodescanner.customer;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.barcodescanner.R;
import com.example.barcodescanner.ui.store.StoreActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;
    Dialog logoutDialog;
    Button btnLogoutCancel;
    Button btnLogoutConfirm;
    Dialog deleteAccDialog;
    Button btnDeleteAccCancel;
    Button btnDeleteAccConfirm;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        final Button changePassword = view.findViewById(R.id.changePassword);
        final Button deleteAccount = view.findViewById(R.id.deleteAccount);
        final Button logout = view.findViewById(R.id.logout_profile_page);
        TextView textView = view.findViewById(R.id.customer_email);

        // Initializing logout dialog
        logoutDialog = new Dialog(getContext());
        logoutDialog.setContentView(R.layout.dialog_logout_box);
        logoutDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable background = ContextCompat.getDrawable(getContext(), R.drawable.dialog_background);
        logoutDialog.getWindow().setBackgroundDrawable(background);
        logoutDialog.setCancelable(true);
        btnLogoutConfirm = logoutDialog.findViewById(R.id.logout_dialog_button_confirm);
        btnLogoutCancel = logoutDialog.findViewById(R.id.logout_dialog_button_cancel);

        // Initializing delete account dialog
        deleteAccDialog = new Dialog(getContext());
        deleteAccDialog.setContentView(R.layout.dialog_delete_account_box);
        deleteAccDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        deleteAccDialog.getWindow().setBackgroundDrawable(background);
        deleteAccDialog.setCancelable(true);
        btnDeleteAccConfirm = deleteAccDialog.findViewById(R.id.delete_account_dialog_button_confirm);
        btnDeleteAccCancel = deleteAccDialog.findViewById(R.id.delete_account_dialog_button_cancel);


        if (user == null) {
            if (getActivity() != null && getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).replaceActivity();
            } else if (getActivity() != null && getActivity() instanceof StoreActivity) {
                ((StoreActivity) getActivity()).replaceActivity();
            }
        } else {
            textView.setText(user.getEmail());
        }

        //Change password screen has to be made.

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.updatePassword("allahyok");
                if (getActivity() != null && getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).replaceFragment(new ChangePasswordFragment(), "Change Password");
                } else if (getActivity() != null && getActivity() instanceof StoreActivity) {
                    ((StoreActivity) getActivity()).replaceFragment(new ChangePasswordFragment(), "Change Password");
                }
            }
        });

        btnLogoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog.dismiss();
            }
        });

        btnLogoutConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog.dismiss();
                FirebaseAuth.getInstance().signOut();
                if (getActivity() != null && getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).replaceActivity();
                } else if (getActivity() != null && getActivity() instanceof StoreActivity) {
                    ((StoreActivity) getActivity()).replaceActivity();
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog.show();
            }
        });

        btnDeleteAccCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccDialog.dismiss();
            }
        });

        btnDeleteAccConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccDialog.dismiss();
                user.delete();
                FirebaseAuth.getInstance().signOut();
                if (getActivity() != null && getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).replaceActivity();
                } else if (getActivity() != null && getActivity() instanceof StoreActivity) {
                    ((StoreActivity) getActivity()).replaceActivity();
                }
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccDialog.show();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
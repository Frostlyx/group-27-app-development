package com.example.barcodescanner.customer;

import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.barcodescanner.R;
import com.example.barcodescanner.ui.store.StoreActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
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

        final TextView changePassword = view.findViewById(R.id.changePassword);
        final TextView deleteAccount = view.findViewById(R.id.deleteAccount);
        final TextView logout = view.findViewById(R.id.logout_profile_page);
        TextView username_text = view.findViewById(R.id.textView4);
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
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("  ");


        if (user == null) {
            if (getActivity() != null && getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).replaceActivity();
            } else if (getActivity() != null && getActivity() instanceof StoreActivity) {
                ((StoreActivity) getActivity()).replaceActivity();
            }
        } else {
            textView.setText(user.getEmail());
            username_text.setText(user.getDisplayName());
        }

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");
                DatabaseReference removalStore = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                removalStore.getRef().removeValue();

                DatabaseReference referenceCustomer = FirebaseDatabase.getInstance().getReference("Users/Customers");
                DatabaseReference removalCustomer = referenceCustomer.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                removalCustomer.getRef().removeValue();

                DatabaseReference referenceStoreOwner = FirebaseDatabase.getInstance().getReference("Users/Store Owners");
                DatabaseReference removalStoreOwner = referenceStoreOwner.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                removalStoreOwner.getRef().removeValue();

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

    //changing rotation
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if the orientation has changed
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Replace the current fragment with the landscape version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.layout_default, new ProfileFragment())
                    .commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Replace the current fragment with the portrait version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.layout_default, new ProfileFragment())
                    .commit();
        }
    }
}
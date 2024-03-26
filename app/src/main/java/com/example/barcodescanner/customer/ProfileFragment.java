package com.example.barcodescanner.customer;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.barcodescanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Dialog logoutDialog;
    Button btnLogoutCancel;
    Button btnLogoutConfirm;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final Button changePassword = view.findViewById(R.id.changePassword);
        final Button logout = view.findViewById(R.id.logout_profile_page);

        // Initializing logout dialog
        logoutDialog = new Dialog(getContext());
        logoutDialog.setContentView(R.layout.logout_dialog_box);
        logoutDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable logoutBackground = ContextCompat.getDrawable(getContext(), R.drawable.logout_dialog_background);
        logoutDialog.getWindow().setBackgroundDrawable(logoutBackground);
        logoutDialog.setCancelable(true);
        btnLogoutConfirm = logoutDialog.findViewById(R.id.logout_dialog_button_confirm);
        btnLogoutCancel = logoutDialog.findViewById(R.id.logout_dialog_button_cancel);




        //Change password screen has to be made.

        // On click listeners
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null && getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).replaceFragment(new ChangePasswordFragment(), "Change password");
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
                ((MainActivity) getActivity()).replaceActivity();
                logoutDialog.dismiss();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog.show();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
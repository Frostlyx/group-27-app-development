package com.example.barcodescanner.customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.ui.login.ForgotPasswordFragment;
import com.example.barcodescanner.ui.login.LoginFragment;
import com.example.barcodescanner.ui.login.WelcomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        final Button changePassword = view.findViewById(R.id.changePassword);
        final Button deleteAccount = view.findViewById(R.id.deleteAccount);
        final Button logout = view.findViewById(R.id.logout_profile_page);
        TextView textView = view.findViewById(R.id.customer_email);


        if (user == null) {
            ((MainActivity) getActivity()).replaceActivity();
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

                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                ((MainActivity) getActivity()).replaceActivity();
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.delete();
                FirebaseAuth.getInstance().signOut();
                ((MainActivity) getActivity()).replaceActivity();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
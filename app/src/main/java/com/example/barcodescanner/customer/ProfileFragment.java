package com.example.barcodescanner.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.barcodescanner.R;


public class ProfileFragment extends Fragment {



    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final Button changePassword = view.findViewById(R.id.changePassword);
        final Button logout = view.findViewById(R.id.logout_profile_page);


        //Change password screen has to be made.

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null && getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).replaceFragment(new ChangePasswordFragment(), "Change password");
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceActivity();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
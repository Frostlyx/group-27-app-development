package com.example.barcodescanner.ui.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.ProfileFragment;
import com.example.barcodescanner.databinding.FragmentMystoreBinding;

/**
 * Fragment for displaying options related to the user's store.
 */
public class MyStoreFragment extends Fragment {

    private FragmentMystoreBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentMystoreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set click listeners for buttons to navigate to different fragments
        binding.buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the current fragment with the ProfileFragment
                if (getActivity() != null && getActivity() instanceof StoreActivity) {
                    // Sends you to profile fragment
                    ((StoreActivity) getActivity()).replaceFragment(new ProfileFragment(), getResources().getString(R.string.profile_title));
                }
            }
        });

        // Set click listener for profile page
        binding.buttonItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the current fragment with the StoreDatabaseFragment
                if (getActivity() != null && getActivity() instanceof StoreActivity) {
                    // Sends you to store item list fragment
                    ((StoreActivity) getActivity()).replaceFragment(new StoreDatabaseFragment(), getResources().getString(R.string.store_database_title));
                }
            }
        });

        // Set click listener for store item view page
        binding.buttonStorePageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with the EditStoreFragment
                if (getActivity() != null && getActivity() instanceof StoreActivity) {
                    // Sends you to edit store page fragment
                    ((StoreActivity) getActivity()).replaceFragment(new EditStoreFragment(), getResources().getString(R.string.edit_store_title));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

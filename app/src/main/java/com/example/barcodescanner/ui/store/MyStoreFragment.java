package com.example.barcodescanner.ui.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.ChangePasswordFragment;
import com.example.barcodescanner.customer.MainActivity;
import com.example.barcodescanner.customer.ProfileFragment;
import com.example.barcodescanner.databinding.FragmentMystoreBinding;

public class MyStoreFragment extends Fragment {

    private FragmentMystoreBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMystoreBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null && getActivity() instanceof StoreActivity) {
                    ((StoreActivity) getActivity()).replaceFragment(new ProfileFragment());
                }
            }
        });

        binding.buttonItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null && getActivity() instanceof StoreActivity) {
                    ((StoreActivity) getActivity()).replaceFragment(new StoreDatabaseFragment());
                }
            }
        });

        binding.buttonStorePageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null && getActivity() instanceof StoreActivity) {
                    ((StoreActivity) getActivity()).replaceFragment(new EditStoreFragment());
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

package com.example.barcodescanner.ui.store;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.databinding.FragmentStoreDatabaseBinding;
import com.example.barcodescanner.ui.login.LoginFragment;

public class StoreDatabaseFragment extends Fragment {

    ImageButton addProductButton;
    private FragmentStoreDatabaseBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentStoreDatabaseBinding.inflate(inflater, container, false);

        String[] test = new String[10];
        test[0] = "hi";
        test[1] = "bye";
        RecyclerView recyclerView = binding.recyclerView;
        DatabaseListAdapter databaseListAdapter = new DatabaseListAdapter(test);
        recyclerView.setAdapter(databaseListAdapter);

        addProductButton = binding.getRoot().findViewById(R.id.floating_button_add_product);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return binding.getRoot();

        }

        public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }



        private void showDialog(){
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.store_database_plus_popup);
            dialog.show();
        }
}

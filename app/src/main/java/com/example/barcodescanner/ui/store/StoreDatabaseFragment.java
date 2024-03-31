package com.example.barcodescanner.ui.store;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.Item;
import com.example.barcodescanner.customer.ProductModel;
import com.example.barcodescanner.databinding.FragmentStoreDatabaseBinding;
import com.example.barcodescanner.ui.login.LoginFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class StoreDatabaseFragment extends Fragment {

    List<ProductModel> itemList;
    Dialog plusDialog;
    Dialog addDialog;
    Button buttonAddDatabase;
    Button buttonAddProduct;
    ImageButton buttonCloseAdd;
    Button buttonSaveAdd;

    private FragmentStoreDatabaseBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentStoreDatabaseBinding.inflate(inflater, container, false);

        itemList = generateItems();

        RecyclerView recyclerView = binding.recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DatabaseListAdapter databaseListAdapter = new DatabaseListAdapter(itemList);
        recyclerView.setAdapter(databaseListAdapter);

        // Initializing plus dialog
        plusDialog = new Dialog(getContext());
        plusDialog.setContentView(R.layout.store_database_plus_popup);
        plusDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        plusDialog.setCancelable(true);
        buttonAddDatabase = plusDialog.findViewById(R.id.button_upload_database);
        buttonAddProduct = plusDialog.findViewById(R.id.button_add_item);

        // Initializing add dialog
        addDialog = new Dialog(getContext());
        addDialog.setContentView(R.layout.store_database_add_popup);
        addDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addDialog.setCancelable(true);
        buttonCloseAdd =  addDialog.findViewById(R.id.button_close_add);
        buttonSaveAdd =  addDialog.findViewById(R.id.button_save_popup);

        binding.floatingButtonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusDialog.show();;
            }
        });

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDialog.show();;
            }
        });

        buttonCloseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusDialog.dismiss();
                addDialog.dismiss();
            }
        });

        buttonSaveAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusDialog.dismiss();
                addDialog.dismiss();
                Toast.makeText(getContext(), R.string.success_add_item, Toast.LENGTH_SHORT).show();

                // Tried to use personal layout. Did not work.
                // Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.activity_store_container), "@string/success_add_item", Snackbar.LENGTH_SHORT);
                // snackbar.setBackgroundTint(Color.parseColor("@color/success_color_green"));
                // snackbar.show();
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

        private List<ProductModel> generateItems(){
            List<ProductModel> item = new ArrayList<>();
            item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
            item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
            item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
            item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
            item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
            item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
            item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
            item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
            item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
            item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
            item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
            item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
            return item;
        }

        private void showDialog(){
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.store_database_plus_popup);
            dialog.show();
        }
}

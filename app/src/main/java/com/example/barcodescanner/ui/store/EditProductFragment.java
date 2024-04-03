package com.example.barcodescanner.ui.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.ProductModel;
import com.example.barcodescanner.databinding.FragmentEditProductBinding;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EditProductFragment extends Fragment {

    ProductModel item;
    List<Integer> imageList;

    private FragmentEditProductBinding binding;

    public EditProductFragment(ProductModel item) {
        this.item = item;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentEditProductBinding.inflate(inflater, container, false);

        binding.imageMain.setImageResource(item.getProductImage(0));

        imageList = item.getProductImageList();
        RecyclerView recyclerView = binding.editProductImages;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ImageListAdapter imageListAdapter = new ImageListAdapter(imageList);
        recyclerView.setAdapter(imageListAdapter);

        binding.storeProductName.setText(item.getProductName());
        binding.storeProductPrice.setText(item.getProductPrice());
        binding.storeProductCategory.setText(item.getCategory());

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getContext() != null && view.getContext() instanceof StoreActivity) {
                    ((StoreActivity) view.getContext()).replaceFragment(new StoreDatabaseFragment(), view.getContext().getString(R.string.edit_product_title));
                }
            }
        });

        binding.productDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getContext() != null && view.getContext() instanceof StoreActivity) {
                    ((StoreActivity) view.getContext()).replaceFragment(new StoreDatabaseFragment(), view.getContext().getString(R.string.edit_product_title));
                }
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

}

package com.example.barcodescanner.ui.store;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.ProductModel;
import com.example.barcodescanner.databinding.FragmentEditProductBinding;

import java.util.ArrayList;
import java.util.List;

public class EditProductFragment extends Fragment {

    List<ProductModel> itemList;

    private FragmentEditProductBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentEditProductBinding.inflate(inflater, container, false);

        itemList = generateItems();

        RecyclerView recyclerView = binding.editProductImages;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        EditProductListAdapter editProductListAdapter = new EditProductListAdapter(itemList);
        recyclerView.setAdapter(editProductListAdapter);

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

    //changing rotation
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if the orientation has changed
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Replace the current fragment with the landscape version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.layout_default, new EditProductFragment())
                    .commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Replace the current fragment with the portrait version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.layout_default, new EditStoreFragment())
                    .commit();
        }
    }

}

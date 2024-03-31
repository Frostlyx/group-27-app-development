package com.example.barcodescanner.ui.store;

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
import com.example.barcodescanner.databinding.FragmentEditStoreBinding;

import java.util.ArrayList;
import java.util.List;

public class EditStoreFragment extends Fragment {

    List<ProductModel> itemList;

    private FragmentEditStoreBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentEditStoreBinding.inflate(inflater, container, false);

        itemList = generateItems();

        RecyclerView recyclerView = binding.recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        StoreListAdapter storeListAdapter = new StoreListAdapter(itemList);
        recyclerView.setAdapter(storeListAdapter);

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
        item.add(new ProductModel("name", "price", R.mipmap.supermarket_inside1_foreground, "category", "discount"));
        item.add(new ProductModel("name", "price", R.mipmap.supermarket_inside2_foreground, "category", "discount"));
        item.add(new ProductModel("name", "price", R.mipmap.supermarket_inside3_foreground, "category", "discount"));
        item.add(new ProductModel("name", "price", R.mipmap.supermarket_baklava_foreground, "category", "discount"));
        item.add(new ProductModel("name", "price", R.mipmap.supermarket_outside1_foreground, "category", "discount"));
        item.add(new ProductModel("name", "price", R.mipmap.supermarket_inside2_foreground, "category", "discount"));
        item.add(new ProductModel("name", "price", R.mipmap.supermarket_inside1_foreground, "category", "discount"));
        item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
        item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
        item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
        item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
        item.add(new ProductModel("name", "price", R.drawable.bread, "category", "discount"));
        return item;
    }
}

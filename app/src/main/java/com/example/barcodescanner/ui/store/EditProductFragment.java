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
import com.example.barcodescanner.databinding.FragmentEditProductBinding;

import java.util.ArrayList;
import java.util.List;

public class EditProductFragment extends Fragment {

    ProductModel item;
    List<Integer> imageList;

    private FragmentEditProductBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentEditProductBinding.inflate(inflater, container, false);

        item = new ProductModel("name", "price", generateImages(), "category", "discount","s","s");
        imageList = item.getProductImageList();

        RecyclerView recyclerView = binding.editProductImages;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ImageListAdapter imageListAdapter = new ImageListAdapter(imageList);
        recyclerView.setAdapter(imageListAdapter);

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

    private List<Integer> generateImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        return images;
    }

}

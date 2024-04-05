package com.example.barcodescanner.customer;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.databinding.FragmentStorePageBinding;
import com.example.barcodescanner.ui.store.ImageListAdapter;

import java.util.ArrayList;
import java.util.List;

public class StoreFragment extends Fragment {

    StoreModel store;
    int position;
    List<Integer> imageList;

    private FragmentStorePageBinding binding;

    public StoreFragment(StoreModel store, int position) {
        this.store = store;
        this.position = position;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentStorePageBinding.inflate(inflater, container, false);

        imageList = store.getStoreImageList();

        RecyclerView recyclerView = binding.customerRecyclerview;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ImageListAdapter imageListAdapter = new ImageListAdapter(imageList);
        recyclerView.setAdapter(imageListAdapter);

        binding.customerTextviewStorename.setText(store.getStoreName());
        binding.customerTextviewLocation.setText(store.getStoreLocation());

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}


package com.example.barcodescanner.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.databinding.FragmentStorePageBinding;
import com.example.barcodescanner.ui.store.ImageListAdapter;

import java.util.List;

/**
 * The StoreFragment displays information about a store when clicked on within a Product Page.
 * Contains store information: store name, store location and store images.
 */
public class StoreFragment extends Fragment {

    // Holds information about the store displayed
    StoreModel store;
    int position;
    List<Integer> imageList;

    private FragmentStorePageBinding binding;

    //Constructor
    public StoreFragment(StoreModel store, int position) {
        this.store = store;
        this.position = position;
    }

    // Creates the view of the store page
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentStorePageBinding.inflate(inflater, container, false);

        // Gets images of the store
        imageList = store.getStoreImageList();

        //Setting up of RecyclerView for displaying the fragment page with regards to the list of images
        RecyclerView recyclerView = binding.customerRecyclerview;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        // Setting up adapter for displaying images
        ImageListAdapter imageListAdapter = new ImageListAdapter(imageList);
        recyclerView.setAdapter(imageListAdapter);

        // Sets store name and location
        binding.customerTextviewStorename.setText(store.getStoreName());
        binding.customerTextviewLocation.setText(store.getStoreLocation());

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // Destroy view
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}


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
import com.example.barcodescanner.customer.StoreModel;
import com.example.barcodescanner.databinding.FragmentEditStoreBinding;

import java.util.ArrayList;
import java.util.List;

public class EditStoreFragment extends Fragment {

    StoreModel store;
    List<Integer> imageList;

    private FragmentEditStoreBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentEditStoreBinding.inflate(inflater, container, false);

        store = new StoreModel("name", "location", generateImages());
        imageList = store.getStoreImageList();

        RecyclerView recyclerView = binding.recyclerView;
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
        images.add(R.mipmap.supermarket_outside_foreground);
        images.add(R.mipmap.supermarket_outside1_foreground);
        images.add(R.mipmap.supermarket_inside1_foreground);
        images.add(R.mipmap.supermarket_inside2_foreground);
        images.add(R.mipmap.supermarket_inside3_foreground);
        images.add(R.mipmap.supermarket_baklava_foreground);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        return images;
    }
}

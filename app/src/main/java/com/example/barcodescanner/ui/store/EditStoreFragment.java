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
import com.example.barcodescanner.customer.StoreModel;
import com.example.barcodescanner.databinding.FragmentEditStoreBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditStoreFragment extends Fragment {

    StoreModel store;
    List<Integer> imageList;
    String storeName;
    String location;

    private FragmentEditStoreBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentEditStoreBinding.inflate(inflater, container, false);

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference referenceStores = referenceProfile.child("Store Owners");
        DatabaseReference referenceCurrentStore = referenceStores.child("uM2MLZDIxaRTtHnP0s0VQOLjGhB3");
        referenceCurrentStore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                storeName = dataSnapshot.child("Storename").getValue().toString();
                location = dataSnapshot.child("location").getValue().toString();

                store = new StoreModel(storeName,location, generateImages());
                imageList = store.getStoreImageList();
                RecyclerView recyclerView = binding.recyclerView;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                ImageListAdapter imageListAdapter = new ImageListAdapter(imageList);
                recyclerView.setAdapter(imageListAdapter);

                binding.textviewStorename.setText(store.getStoreName());
                binding.textviewLocation.setText(store.getStoreLocation());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
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

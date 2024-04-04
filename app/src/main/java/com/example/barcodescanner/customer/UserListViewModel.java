package com.example.barcodescanner.customer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.barcodescanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListViewModel {

    private MutableLiveData<ArrayList<ProductModel>> productModels = new MutableLiveData<>();
    private MutableLiveData<Map<ProductModel, Integer>> shoppingList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ProductModel>> favouritesList = new MutableLiveData<>();

    public UserListViewModel(Context context) {
        List<Integer> imageList = generateImages();

        ArrayList<ProductModel> item = new ArrayList<>();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Stores");
        DatabaseReference referenceCurrentStore = referenceProfile.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        referenceCurrentStore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Toast.makeText(getContext().getApplicationContext(), "ss", Toast.LENGTH_SHORT).show();
                    item.add(new ProductModel(snapshot.child("productName").getValue().toString(), snapshot.child("productName").getValue().toString(), imageList, snapshot.child("productName").getValue().toString(),snapshot.child("productName").getValue().toString(),snapshot.child("productName").getValue().toString(),snapshot.child("productName").getValue().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        productModels.setValue(item);
        shoppingList.setValue(new HashMap<>());
        favouritesList.setValue(new ArrayList<>());
    }

    public void setProductModels(ArrayList<ProductModel> newProductModels) {
        productModels.setValue(newProductModels);
    }

    public LiveData<ArrayList<ProductModel>> getProductModels() {
        return productModels;
    }

    public void setShoppingList(Map<ProductModel, Integer> newProductModels) {
        shoppingList.setValue(newProductModels);
    }

    public LiveData<Map<ProductModel, Integer>> getShoppingList() {
        return shoppingList;
    }

    public void setFavouritesList(ArrayList<ProductModel> newProductModels) {
        favouritesList.setValue(newProductModels);
    }

    public LiveData<ArrayList<ProductModel>> getFavouritesList() {
        return favouritesList;
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
        return images;
    }
}

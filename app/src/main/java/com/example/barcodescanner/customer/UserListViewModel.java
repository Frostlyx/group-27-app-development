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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListViewModel {

    //Livedata ProductModel objects to hold general product models, the shopping list and the favourites list
    private MutableLiveData<ArrayList<ProductModel>> productModels = new MutableLiveData<>();
    private MutableLiveData<Map<ProductModel, Integer>> shoppingList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ProductModel>> favouritesList = new MutableLiveData<>();

    public UserListViewModel(Context context) {
        //Generates a list of images for the products
        List<Integer> imageList = generateImages();

        //Initialize an Arraylist to hold the product models
        ArrayList<ProductModel> item = new ArrayList<>();

        //Firebase referencing for the current user's profile and store
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Stores");
        DatabaseReference referenceCurrentStore = referenceProfile.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //Added Listener to fetch the database data
        referenceCurrentStore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterates through each product in the database to add it to the item list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Toast.makeText(getContext().getApplicationContext(), "ss", Toast.LENGTH_SHORT).show();
                    item.add(new ProductModel(snapshot.child("productName").getValue().toString(), snapshot.child("productName").getValue().toString(), imageList, snapshot.child("productName").getValue().toString(),snapshot.child("productName").getValue().toString(),snapshot.child("productName").getValue().toString(),snapshot.child("productName").getValue().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //Set the Livedata ProductModels to the fetched data (update)
        productModels.setValue(item);
        shoppingList.setValue(new HashMap<>());
        favouritesList.setValue(new ArrayList<>());
    }

    //Method for setting or updating product models
    public void setProductModels(ArrayList<ProductModel> newProductModels) {
        productModels.setValue(newProductModels);
    }

    //Getter for product models
    public LiveData<ArrayList<ProductModel>> getProductModels() {
        return productModels;
    }

    //Setter for setting/updating the shopping list
    public void setShoppingList(Map<ProductModel, Integer> newProductModels) {
        shoppingList.setValue(newProductModels);
    }

    //Getter for shopping list
    public LiveData<Map<ProductModel, Integer>> getShoppingList() {
        return shoppingList;
    }

    //Setter for setting/updating favourites list
    public void setFavouritesList(ArrayList<ProductModel> newProductModels) {
        favouritesList.setValue(newProductModels);
    }

    //Getter for favourites list
    public LiveData<ArrayList<ProductModel>> getFavouritesList() {
        return favouritesList;
    }

    //List of images stored in the database for each item
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

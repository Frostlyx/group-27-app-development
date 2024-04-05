package com.example.barcodescanner.ui.store;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel class responsible for managing store product data.
 */
public class StoreProductViewModel {

    // MutableLiveData to hold the list of product models
    private MutableLiveData<ArrayList<ProductModel>> productModels = new MutableLiveData<>();
    // MutableLiveData to hold the filtered list of product models
    private MutableLiveData<ArrayList<ProductModel>> filteredProductModels = new MutableLiveData<>();

    /**
     * Constructor to initialize the ViewModel.
     *
     * @param context The context of the calling activity or fragment
     */
    public StoreProductViewModel(Context context) {
        // Generate a list of dummy images for products
        List<Integer> imageList = generateImages();

        // Retrieve product data from Firebase database
        ArrayList<ProductModel> item = new ArrayList<>();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Stores");
        DatabaseReference referenceCurrentStore = referenceProfile.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        referenceCurrentStore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Loop through the data snapshot to extract product information
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Create ProductModel objects and add them to the list
                    item.add(new ProductModel(
                            snapshot.child("productName").getValue().toString(),
                            snapshot.child("productPrice").getValue().toString(),
                            imageList,
                            snapshot.child("category").getValue().toString(),
                            snapshot.child("discount").getValue().toString(),
                            snapshot.child("productAmount").getValue().toString(),
                            snapshot.child("productBarcode").getValue().toString())
                    );
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle onCancelled event
            }
        });
        // Set the retrieved product models to the MutableLiveData
        productModels.setValue(item);
    }

    /**
     * Method to set the list of product models.
     *
     * @param newProductModels The new list of product models
     */
    public void setProductModels(ArrayList<ProductModel> newProductModels) {
        productModels.setValue(newProductModels);
    }

    /**
     * Method to get the LiveData of the list of product models.
     *
     * @return LiveData of ArrayList<ProductModel>
     */
    public LiveData<ArrayList<ProductModel>> getProductModels() {
        return productModels;
    }

    /**
     * Method to set a specific product model at a given position.
     *
     * @param position     The position of the product model to be replaced
     * @param productModel The new product model
     */
    public void setProductModel(int position, ProductModel productModel) {
        ArrayList<ProductModel> tempProductModels = productModels.getValue();
        if (tempProductModels != null) {
            tempProductModels.set(position, productModel);
            productModels.setValue(tempProductModels);
        }
    }

    /**
     * Method to reset the filtered list of product models.
     */
    public void resetFilter() {
        ArrayList<ProductModel> tempProductModels = productModels.getValue();
        ArrayList<ProductModel> tempFilteredProductModels = filteredProductModels.getValue();

        tempProductModels.addAll(tempFilteredProductModels);
        tempFilteredProductModels.clear();

        productModels.setValue(tempProductModels);
        filteredProductModels.setValue(tempFilteredProductModels);
    }

    /**
     * Method to generate a list of dummy images for products.
     *
     * @return List of dummy image resources
     */
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

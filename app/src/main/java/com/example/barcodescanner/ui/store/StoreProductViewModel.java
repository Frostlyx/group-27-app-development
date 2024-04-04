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

public class StoreProductViewModel {

    private MutableLiveData<ArrayList<ProductModel>> productModels = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ProductModel>> filteredProductModels = new MutableLiveData<>();

    public StoreProductViewModel(Context context) {
        List<Integer> imageList = generateImages();

        ArrayList<ProductModel> item = new ArrayList<>();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Stores");
        DatabaseReference referenceCurrentStore = referenceProfile.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        referenceCurrentStore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Toast.makeText(getContext().getApplicationContext(), "ss", Toast.LENGTH_SHORT).show();
                    item.add(new ProductModel(snapshot.child("productName").getValue().toString(), snapshot.child("productPrice").getValue().toString(), imageList, snapshot.child("category").getValue().toString(),snapshot.child("discount").getValue().toString(),snapshot.child("productAmount").getValue().toString(),snapshot.child("productBarcode").getValue().toString()));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        productModels.setValue(item);
    }

    public void setProductModels(ArrayList<ProductModel> newProductModels) {
        productModels.setValue(newProductModels);
    }

    public LiveData<ArrayList<ProductModel>> getProductModels() {
        return productModels;
    }

    public void setProductModel(int position, ProductModel productModel) {
        ArrayList<ProductModel> tempProductModels = productModels.getValue();
        if (tempProductModels != null) {
            tempProductModels.set(position, productModel);
            productModels.setValue(tempProductModels);
        }
    }

    public void resetFilter() {
        ArrayList<ProductModel> tempProductModels = productModels.getValue();
        ArrayList<ProductModel> tempFilteredProductModels = filteredProductModels.getValue();

        tempProductModels.addAll(tempFilteredProductModels);
        tempFilteredProductModels.clear();

        productModels.setValue(tempProductModels);
        filteredProductModels.setValue(tempFilteredProductModels);
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

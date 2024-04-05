package com.example.barcodescanner.customer;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ProductModel>> productModels = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ProductModel>> filteredProductModels = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFetched = new MutableLiveData<>(false);
    public boolean isFiltered = false;

    public SharedViewModel(Context context) {
        fetchDatabase();
    }

    public void fetchDatabase(){

        DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");

        referenceStores.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ProductModel> initProductModels = new ArrayList<>();
                for (DataSnapshot Stores : snapshot.getChildren()){
                    for (DataSnapshot products : Stores.getChildren()){
                        String productName = products.child("productName").getValue().toString();
                        String productCategory = products.child("category").getValue().toString();
                        String productPrice = products.child("productPrice").getValue().toString();
                        String productBarcode = products.child("productBarcode").getValue().toString();
                        String productAmount = products.child("productAmount").getValue().toString();
                        String productDiscount;
                        if (products.child("discount").getValue().toString().isEmpty()){
                            productDiscount = " ";
                        } else {
                            productDiscount = products.child("discount").getValue().toString();
                        }
                        initProductModels.add(new ProductModel(
                                productName,
                                productPrice,
                                generateImages(),
                                productCategory,
                                productDiscount,
                                productAmount,
                                productBarcode
                        ));
                    }
                }
                productModels.setValue(initProductModels);
                filteredProductModels.setValue(new ArrayList<>());
                isFetched.setValue(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public LiveData<ArrayList<ProductModel>> getProductModels() {
        return productModels;
    }

    public LiveData<Boolean> isDataFetched() {
        return isFetched;
    }

    public void resetFilter() {
        ArrayList<ProductModel> tempProductModels = productModels.getValue();
        ArrayList<ProductModel> tempFilteredProductModels = filteredProductModels.getValue();

        tempProductModels.addAll(tempFilteredProductModels);
        tempFilteredProductModels.clear();

        productModels.setValue(tempProductModels);
        filteredProductModels.setValue(tempFilteredProductModels);
    }

    public void filterBy(String criteria) {
        ArrayList<ProductModel> tempProductModels = productModels.getValue();
        ArrayList<ProductModel> tempFilteredProductModels = filteredProductModels.getValue();

        tempProductModels.addAll(tempFilteredProductModels);
        tempFilteredProductModels.clear();

        Iterator<ProductModel> iter = tempProductModels.iterator();

        while (iter.hasNext()) {
            ProductModel product = iter.next();
            if (!criteria.equals(product.getCategory())) {
                tempFilteredProductModels.add(product);
                iter.remove();
            }
        }

        Log.i("Filter", criteria);
        for (ProductModel productModel : tempProductModels) {
            Log.i("Filter", productModel.getProductName() + ": " + productModel.getCategory());
        }

        productModels.setValue(tempProductModels);
        filteredProductModels.setValue(tempFilteredProductModels);

        isFiltered = true;
    }
    private List<Integer> generateImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        return images;
    }
}

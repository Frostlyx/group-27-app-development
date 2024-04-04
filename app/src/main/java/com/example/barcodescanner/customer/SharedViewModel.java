package com.example.barcodescanner.customer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
    // TODO: placeholder for images, to replace with actual images
    int[] productImage = {R.drawable.bread};

    boolean onGoing = true;

    public SharedViewModel(Context context) {
        fetchDatabase();
    }

    public void fetchDatabase(){

        DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");

        String[] productNames = new String[5];
        String[] productPrices = new String[5];
        String[] productCategories = new String[5];
        String[] productBarcode = new String[5];
        String[] productAmount = new String[5];
        String[] productDiscount = new String[5];
        referenceStores.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot Stores : snapshot.getChildren()){
                    int i = 0;
                    for (DataSnapshot products : Stores.getChildren()){
                        productNames[i] = products.child("productName").getValue().toString();
                        productCategories[i] = products.child("category").getValue().toString();
                        productPrices[i] = products.child("productPrice").getValue().toString();
                        productBarcode[i] = products.child("productPrice").getValue().toString();
                        productAmount[i] = products.child("productPrice").getValue().toString();
                        if (products.child("productPrice").getValue().toString().isEmpty()){
                            productDiscount[i] = " ";
                        } else {
                            productDiscount[i] = products.child("productPrice").getValue().toString();
                            i++;
                        }
                    }
                }






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        ArrayList<ProductModel> initProductModels = new ArrayList<>();
        for (int i = 0; i < productNames.length; i++) {
            initProductModels.add(new ProductModel(
                           /* productNames[i],
                            productPrices[i],
                            generateImages(),
                            productCategories[i],
                            productDiscount[i],
                            productAmount[i],
                            productBarcode[i]*/
                    "s","s",generateImages(),"s","s","s","s"));
        }
        productModels.setValue(initProductModels);
        filteredProductModels.setValue(new ArrayList<>());
    }

    public void setProductModels(ArrayList<ProductModel> newProductModels) {
        productModels.setValue(newProductModels);
    }

    public LiveData<ArrayList<ProductModel>> getProductModels() {
        return productModels;
    }

    public void setFilteredProductModels(ArrayList<ProductModel> newFilteredProductModels) {
        productModels.setValue(newFilteredProductModels);
    }

    public LiveData<ArrayList<ProductModel>> getFilteredProductModels() {
        return filteredProductModels;
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

        switch (criteria) {
            case "Food":
                while (iter.hasNext()) {
                    ProductModel product = iter.next();
                    if (!"Food".equals(product.getCategory())) {
                        tempFilteredProductModels.add(product);
                        iter.remove();
                    }
                }

                Log.i("Filter", "Food");
                for (ProductModel productModel : tempProductModels) {
                    Log.i("Filter", productModel.getProductName() + ": " + productModel.getCategory());
                }

                break;

            case "Drink":
                while (iter.hasNext()) {
                    ProductModel product = iter.next();
                    if (!"Drink".equals(product.getCategory())) {
                        tempFilteredProductModels.add(product);
                        iter.remove();
                    }
                }

                Log.i("Filter", "Drink");
                for (ProductModel productModel : tempProductModels) {
                    Log.i("Filter", productModel.getProductName() + ": " + productModel.getCategory());
                }

                break;
        }

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
        return images;
    }
}

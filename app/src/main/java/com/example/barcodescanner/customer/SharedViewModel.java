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

/**
 * SharedViewModel is a ViewModel class that manages product data fetched from Firebase.
 * It is mainly used for filtering products on category and displaying this on the main page.
 */
public class SharedViewModel extends ViewModel {
    // LiveData for holding the list of all product models
    private MutableLiveData<ArrayList<ProductModel>> productModels = new MutableLiveData<>();
    // LiveData for holding the list of filtered on category product models
    private MutableLiveData<ArrayList<ProductModel>> filteredProductModels = new MutableLiveData<>();
    // LiveData for indicating whether the data has been fetched from Firebase
    private MutableLiveData<Boolean> isFetched = new MutableLiveData<>(false);
    // Flag indicating whether the list is currently filtered
    public boolean isFiltered = false;

    /**
     * Constructor for SharedViewModel.
     * Initializes the ViewModel and fetches data from Firebase.
     *
     * @param context The context in which the ViewModel is created.
     */
    public SharedViewModel(Context context) {
        fetchDatabase();
    }

    /**
     * Fetches product data from Firebase and updates LiveData objects.
     * This method is called in the constructor to initialize the ViewModel.
     */
    public void fetchDatabase() {
        DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");

        referenceStores.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ProductModel> initProductModels = new ArrayList<>();
                for (DataSnapshot Stores : snapshot.getChildren()) {
                    for (DataSnapshot products : Stores.getChildren()) {
                        // Extract product details from the snapshot
                        String productName = products.child("productName").getValue().toString();
                        String productCategory = products.child("category").getValue().toString();
                        String productPrice = products.child("productPrice").getValue().toString();
                        String productBarcode = products.child("productBarcode").getValue().toString();
                        String productAmount = products.child("productAmount").getValue().toString();
                        String productDiscount = products.child("discount").getValue().toString().isEmpty() ? " " : products.child("discount").getValue().toString();

                        // Create a new ProductModel and add it to the list
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
                // Update LiveData objects with the fetched data
                productModels.setValue(initProductModels);
                filteredProductModels.setValue(new ArrayList<>());
                isFetched.setValue(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    /**
     * Getter for the LiveData object holding the list of all product models.
     *
     * @return LiveData object containing the list of all product models.
     */
    public LiveData<ArrayList<ProductModel>> getProductModels() {
        return productModels;
    }

    /**
     * Getter for the LiveData object indicating whether the data has been fetched from Firebase.
     *
     * @return LiveData object indicating whether the data has been fetched.
     */
    public LiveData<Boolean> isDataFetched() {
        return isFetched;
    }

    /**
     * Resets the filter by merging the filtered list back into the main list and clearing the filtered list.
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
     * Filters the product list based on a given criteria and updates the LiveData objects accordingly.
     *
     * @param criteria The criteria to filter the products by.
     */
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

    /**
     * Generates a list of image resource IDs for products.
     * This method is used for testing purposes and should be replaced with actual image data fetched from Firebase.
     *
     * @return A list of image resource IDs.
     */
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

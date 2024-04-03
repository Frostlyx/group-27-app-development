package com.example.barcodescanner.customer;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barcodescanner.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ProductModel>> productModels = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ProductModel>> filteredProductModels = new MutableLiveData<>();
    // TODO: placeholder for images, to replace with actual images
    int[] productImage = {R.drawable.bread};

    public SharedViewModel(Context context) {
        String[] productNames = context.getResources().getStringArray(R.array.placeholder_main_page_product);
        String[] productPrices = context.getResources().getStringArray(R.array.placeholder_main_page_price);
        String[] productCategories = context.getResources().getStringArray(R.array.placeholder_main_page_category);
        ArrayList<ProductModel> initProductModels = new ArrayList<>();
        for (int i = 0; i < productNames.length; i++) {
            initProductModels.add(new ProductModel(productNames[i],
                    productPrices[i],
                    generateImages(),
                    productCategories[i],
                    "10%", "S", "S"));
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
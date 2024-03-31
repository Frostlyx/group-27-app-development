package com.example.barcodescanner.customer;

import android.util.Log;

import java.util.Iterator;

public class ProductFilterer {


    public ProductFilterer() {

    }

    public void resetFilter() {
        MainActivity.productModels.addAll(MainActivity.filteredProductModels);
        MainActivity.filteredProductModels.clear();
    }

    public void filterBy(String criteria) {

        MainActivity.productModels.addAll(MainActivity.filteredProductModels);
        MainActivity.filteredProductModels.clear();

        Iterator<ProductModel> iter = MainActivity.productModels.iterator();

        switch (criteria) {
            case "Food":
                while (iter.hasNext()) {
                    ProductModel product = iter.next();
                    if (!"Food".equals(product.getCategory())) {
                        MainActivity.filteredProductModels.add(product);
                        iter.remove();
                    }
                }

                Log.i("Filter", "Food");
                for (ProductModel productModel : MainActivity.productModels) {
                    Log.i("Filter", productModel.getProductName() + ": " + productModel.getCategory());
                }

                break;

            case "Drink":
                while (iter.hasNext()) {
                    ProductModel product = iter.next();
                    if (!"Drink".equals(product.getCategory())) {
                        MainActivity.filteredProductModels.add(product);
                        iter.remove();
                    }
                }

                Log.i("Filter", "Drink");
                for (ProductModel productModel : MainActivity.productModels) {
                    Log.i("Filter", productModel.getProductName() + ": " + productModel.getCategory());
                }

                break;
        }
    }
}

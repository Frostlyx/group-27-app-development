package com.example.barcodescanner.customer;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ProductShopAdapterTest extends TestCase {

    // Mock implementation of ProductRecyclerViewInterface for testing
    private static class MockRecyclerViewInterface implements ProductRecyclerViewInterface {
        @Override
        public void onItemClick(int position) {
            // Do nothing in the mock implementation
        }

        @Override
        public void onFavouritesClick(int position) {

        }

        @Override
        public void onShoppingListClick(int position) {

        }

        @Override
        public void increment(ProductModel item) {

        }

        @Override
        public void decrement(ProductModel item) {

        }

        @Override
        public void check(ProductModel item) {

        }
    }


    public void testGetItemCount() {
        // Initialize the storeList with some dummy data
        List<StoreModel> storeList = new ArrayList<>();
        storeList.add(new StoreModel("Test Store", "123 Test Address", new ArrayList<>()));

        // Initialize the adapter with the dummy data and mocked interface
        ProductShopAdapter adapter = new ProductShopAdapter(storeList, new MockRecyclerViewInterface());

        // Test the getItemCount method
        assertEquals(1, adapter.getItemCount());
    }
}

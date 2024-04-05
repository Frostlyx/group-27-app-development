package com.example.barcodescanner.customer;

import static org.junit.Assert.*;
import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.StoreModel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StoreModelTest {

    private StoreModel storeModel;
    private List<Integer> expectedImageResId;

    @Before
    public void setUp() throws Exception {
        // Initialize the list as mutable ArrayList
        expectedImageResId = new ArrayList<>();
        expectedImageResId.add(R.drawable.bread); // ID for bread.jpg in drawable

        // Initialize your StoreModel with test data
        storeModel = new StoreModel("Test Store", "123 Test Address", expectedImageResId);
    }

    @Test
    public void testStoreName() {
        // Test getting the store name
        assertEquals("Test Store", storeModel.getStoreName());

        // Test setting a new store name
        storeModel.setStoreName("New Test Store");
        assertEquals("New Test Store", storeModel.getStoreName());
    }

    @Test
    public void testStoreAddress() {
        // Test getting the store address
        assertEquals("123 Test Address", storeModel.getStoreLocation());

        // Test setting a new store address
        storeModel.setStoreLocation("321 New Test Address");
        assertEquals("321 New Test Address", storeModel.getStoreLocation());
    }
}

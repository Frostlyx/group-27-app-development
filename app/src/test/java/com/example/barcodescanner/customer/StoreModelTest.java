package com.example.barcodescanner.customer;

import com.example.barcodescanner.R;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StoreModelTest extends TestCase {

    private StoreModel storeModel;
    private List<Integer> expectedImageResId;
    private List<Integer> expectedImageResId2;

    private int expectedImageResId3 = R.drawable.grocery_app_welcome_icon;

    public void setUp() throws Exception {
        // Initialize the list as mutable ArrayList
        expectedImageResId = new ArrayList<>();
        expectedImageResId.add(R.drawable.bread); // ID for bread.jpg in drawable

        expectedImageResId2 = new ArrayList<>();
        expectedImageResId2.add(R.drawable.grocery_app_welcome_icon); // ID for bread.jpg in drawabl




        // Initialize your StoreModel with test data
        storeModel = new StoreModel("Test Store", "123 Test Address", expectedImageResId);
    }

    public void testStoreName() {
        // Test getting the store name
        assertEquals("Test Store", storeModel.getStoreName());

        // Test setting a new store name
        storeModel.setStoreName("New Test Store");
        assertEquals("New Test Store", storeModel.getStoreName());
    }

    public void testStoreLocation() {
        // Test getting the store address
        assertEquals("123 Test Address", storeModel.getStoreLocation());

        // Test setting a new store address
        storeModel.setStoreLocation("321 New Test Address");
        assertEquals("321 New Test Address", storeModel.getStoreLocation());
    }

    public void testGetStoreImageList() {
        assertEquals(expectedImageResId, storeModel.getStoreImageList());

        storeModel.setStoreImage(0, expectedImageResId3);
        assertEquals(expectedImageResId3, storeModel.getStoreImage(0));
    }

}
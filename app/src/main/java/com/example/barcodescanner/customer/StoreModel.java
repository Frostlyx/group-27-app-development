package com.example.barcodescanner.customer;

import java.util.List;
/**
 * StoreModel represents a store with its name, location, and a list of images.
 * This model is used to save store (owner) information for easy management.
 */

public class StoreModel {

    //Name of store
    String storeName;
    //Location of store
    String storeLocation;
    //List of IDs (for drawables) with images of the store
    List<Integer> storeImageList;


    /**
     * Constructor for StoreModel.
     * Initializes the store with a name, location, and a list of image resource IDs.
     *
     * @param storeName        The name of the store.
     * @param storeLocation    The location of the store.
     * @param storeImageList   A list of image resource IDs for the store.
     */
    public StoreModel(String storeName, String storeLocation, List<Integer> storeImageList) {
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.storeImageList = storeImageList;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public List<Integer> getStoreImageList() {
        return storeImageList;
    }

    public int getStoreImage(final int position) {
        return storeImageList.get(position);
    }

    public void setStoreImage(final int position, int storeImage) {
        this.storeImageList.set(position, storeImage);
    }
}

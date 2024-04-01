package com.example.barcodescanner.customer;

import java.util.List;

public class StoreModel {

    String storeName;
    String storeLocation;
    List<Integer> storeImageList;

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

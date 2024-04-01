package com.example.barcodescanner.customer;

import java.util.List;

public class ProductModel {
    String productName;
    String productPrice;
    List<Integer> productImageList;
    String category;
    String discount;
    // other stuff idk yet

    public ProductModel(String productName, String productPrice, List<Integer> productImageList, String category, String discount) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageList = productImageList;
        this.category = category;
        this.discount = discount;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public List<Integer> getProductImageList() {
        return productImageList;
    }

    public int getProductImage(final int position) {
        return productImageList.get(position);
    }

    public String getCategory() {
        return category;
    }

    public String getDiscount() {
        return discount;
    }
}

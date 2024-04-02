package com.example.barcodescanner.customer;

import java.util.List;

public class ProductModel {
    String productName;
    String productPrice;
    List<Integer> productImageList;
    String category;
    String discount;


    String productBarcode;

    String productAmount;
    // other stuff idk yet

    public ProductModel(String productName, String productPrice, List<Integer> productImageList, String category, String discount, String productAmount, String productBarcode) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageList = productImageList;
        this.category = category;
        this.discount = discount;
        this.productAmount = productAmount;
        this.productBarcode = productBarcode;
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

    public String getProductAmount() {
        return productAmount;
    }

    public String getProductBarcode() {
        return productBarcode;
    }
}

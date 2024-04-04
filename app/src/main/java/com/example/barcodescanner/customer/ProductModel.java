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

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
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

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }
}

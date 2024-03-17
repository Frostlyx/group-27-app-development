package com.example.barcodescanner.customer;

public class ProductModel {
    String productName;
    String productPrice;
    int productImage;
    String discount;
    // other stuff idk yet

    public ProductModel(String productName, String productPrice, int productImage, String discount) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.discount = discount;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public int getProductImage() {
        return productImage;
    }

    public String getDiscount() {
        return discount;
    }
}

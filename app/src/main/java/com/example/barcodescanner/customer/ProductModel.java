package com.example.barcodescanner.customer;

import java.util.List;

/**
 * Represents a product model containing information about a specific product.
 */
public class ProductModel {
    // Product name
    String productName;
    // Product price
    String productPrice;
    // List of product images
    List<Integer> productImageList;
    // Product category
    String category;
    // Discount information
    String discount;
    // Product barcode
    String productBarcode;
    // Product amount
    String productAmount;

    // Constructor to initialize a ProductModel object with provided details.
    public ProductModel(String productName, String productPrice, List<Integer> productImageList, String category, String discount, String productAmount, String productBarcode) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageList = productImageList;
        this.category = category;
        this.discount = discount;
        this.productAmount = productAmount;
        this.productBarcode = productBarcode;
    }

    // Retrieves the product name.
    public String getProductName() {
        return productName;
    }

    // Sets the product name.
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Retrieves the product price.
    public String getProductPrice() {
        return productPrice;
    }

    // Sets the product price.
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    // Retrieves the list of product images.
    public List<Integer> getProductImageList() {
        return productImageList;
    }

    // Retrieves a specific product image from the list.
    public int getProductImage(final int position) {
        return productImageList.get(position);
    }

    // Retrieves the product category.
    public String getCategory() {
        return category;
    }

    // Sets the product category.
    public void setCategory(String category) {
        this.category = category;
    }

    // Retrieves the discount information for the product.
    public String getDiscount() {
        return discount;
    }

    // Sets the discount information for the product.
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    // Retrieves the amount of the product.
    public String getProductAmount() {
        return productAmount;
    }

    // Sets the amount of the product.
    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    // Retrieves the product barcode.
    public String getProductBarcode() {
        return productBarcode;
    }

    // Sets the product barcode.
    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }
}

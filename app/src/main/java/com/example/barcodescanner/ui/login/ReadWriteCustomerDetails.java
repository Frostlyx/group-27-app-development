package com.example.barcodescanner.ui.login;

import com.example.barcodescanner.customer.ProductModel;

import java.util.List;

public class ReadWriteCustomerDetails {
    public String username, email, password;
    public List<ProductModel> ShoppingList, FavouriteList;

    public ReadWriteCustomerDetails(String username, String email, String password, List<ProductModel> ShoppingList, List<ProductModel> FavouriteList){
        this.username = username;
        this.email = email;
        this.password = password;
        this.FavouriteList = FavouriteList;
        this.ShoppingList = ShoppingList;
    }
}

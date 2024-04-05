package com.example.barcodescanner.ui.login;

import com.example.barcodescanner.customer.ProductModel;
import java.util.List;

/**
 * Class representing customer details for reading and writing.
 * This class holds information such as username, email, password, shopping list, and favorite list.
 */
public class ReadWriteCustomerDetails {
    // Fields to store customer details
    public String username, email, password;
    public List<ProductModel> ShoppingList, FavouriteList;

    // Constructor to initialize customer details
    public ReadWriteCustomerDetails(String username, String email, String password, List<ProductModel> ShoppingList, List<ProductModel> FavouriteList){
        this.username = username;
        this.email = email;
        this.password = password;
        this.FavouriteList = FavouriteList;
        this.ShoppingList = ShoppingList;
    }
}

package com.example.barcodescanner.customer;

/**
 * Interface for handling RecyclerView item clicks and interactions
 */
public interface ProductRecyclerViewInterface {

    // Method to handle click event on a RecyclerView item
    void onItemClick(int position);

    // Method to handle click event on the favorites button of a RecyclerView item
    void onFavouritesClick(int position);

    // Method to handle click event on the shopping list button of a RecyclerView item
    void onShoppingListClick(int position);

    // Method to handle increment action for a product item
    void increment(ProductModel item);

    // Method to handle decrement action for a product item
    void decrement(ProductModel item);

    // Method to handle checkbox check event for a product item
    void check(ProductModel item);
}

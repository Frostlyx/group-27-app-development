package com.example.barcodescanner.customer;

public interface ProductRecyclerViewInterface {
    void onItemClick(int position);
    void onFavouritesClick(int position);
    void onShoppingListClick(int position);

    void increment(ProductModel item);

    void decrement(ProductModel item);
}

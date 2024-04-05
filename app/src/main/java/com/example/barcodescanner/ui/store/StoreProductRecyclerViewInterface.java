package com.example.barcodescanner.ui.store;

/**
 * Interface for handling item click events in the RecyclerView of store products.
 */
public interface StoreProductRecyclerViewInterface {

    /**
     * Callback method invoked when an item in the RecyclerView is clicked.
     *
     * @param position The position of the clicked item in the RecyclerView
     */
    void onItemClick(int position);

    /**
     * Callback method invoked when the delete action is triggered for an item in the RecyclerView.
     *
     * @param position The position of the item to be deleted in the RecyclerView
     */
    void onDeleteClick(int position);

    /**
     * Callback method invoked when the edit action is triggered for an item in the RecyclerView.
     *
     * @param position The position of the item to be edited in the RecyclerView
     */
    void onEditClick(int position);
}

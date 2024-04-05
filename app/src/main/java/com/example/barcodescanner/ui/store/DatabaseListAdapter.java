package com.example.barcodescanner.ui.store;

import android.content.Context; // Importing necessary Android libraries
import android.view.LayoutInflater; // Importing necessary Android libraries
import android.view.View; // Importing necessary Android libraries
import android.view.ViewGroup; // Importing necessary Android libraries
import android.widget.ImageButton; // Importing necessary Android libraries
import android.widget.ImageView; // Importing necessary Android libraries
import android.widget.TextView; // Importing necessary Android libraries

import androidx.recyclerview.widget.RecyclerView; // Importing necessary Android libraries

import com.example.barcodescanner.R; // Importing custom R class
import com.example.barcodescanner.customer.ProductModel; // Importing custom ProductModel class

import java.util.ArrayList; // Importing necessary Java libraries

public class DatabaseListAdapter extends RecyclerView.Adapter<DatabaseListAdapter.ViewHolder> {

    private StoreProductRecyclerViewInterface storeProductRecyclerViewInterface; // Interface for interaction with StoreProductRecyclerView
    Context context; // Context of the application
    StoreProductViewModel storeProductViewModel; // ViewModel for StoreProduct
    ArrayList<ProductModel> productModels; // List of ProductModel objects

    /**
     * Constructor for DatabaseListAdapter
     *
     * @param context Context of the application
     * @param storeProductRecyclerViewInterface Interface for interaction with StoreProductRecyclerView
     */
    public DatabaseListAdapter(Context context, StoreProductRecyclerViewInterface storeProductRecyclerViewInterface) {
        this.context = context; // Initializing context
        this.storeProductRecyclerViewInterface = storeProductRecyclerViewInterface; // Initializing interface
        this.storeProductViewModel = ((StoreActivity) context).getStoreProductViewModel(); // Getting ViewModel from StoreActivity
        this.productModels = storeProductViewModel.getProductModels().getValue(); // Getting list of ProductModels
    }

    /**
     * Create new views (invoked by the layout manager)
     *
     * @param viewGroup Parent view group
     * @param viewType Type of view
     * @return ViewHolder object
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Inflate the layout for the row item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.store_database_row_item, viewGroup, false);

        return new ViewHolder(view, storeProductRecyclerViewInterface); // Return a new ViewHolder
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     *
     * @param viewHolder ViewHolder object
     * @param position Position of the item
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Bind data to the views
        ProductModel item = productModels.get(position);
        viewHolder.itemImage.setImageResource(item.getProductImage(0));
        viewHolder.itemName.setText(item.getProductName());
        viewHolder.itemCategory.setText(item.getCategory());
        viewHolder.itemPrice.setText(item.getProductPrice());
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     *
     * @return Size of the dataset
     */
    @Override
    public int getItemCount() {
        return productModels.size(); // Return the number of items in the list
    }

    /**
     * Custom ViewHolder class
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage; // ImageView for item image
        TextView itemName; // TextView for item name
        TextView itemCategory; // TextView for item category
        TextView itemPrice; // TextView for item price
        ImageButton buttonEdit; // ImageButton for editing
        ImageButton buttonBin; // ImageButton for deleting

        /**
         * Constructor for ViewHolder
         *
         * @param itemView View for each item
         * @param storeProductRecyclerViewInterface Interface for interaction with StoreProductRecyclerView
         */
        public ViewHolder(View itemView, StoreProductRecyclerViewInterface storeProductRecyclerViewInterface) {
            super(itemView);
            // Initialize views
            itemImage = itemView.findViewById(R.id.image_item);
            itemName = itemView.findViewById(R.id.text_item_name);
            itemCategory = itemView.findViewById(R.id.text_item_category);
            itemPrice = itemView.findViewById(R.id.text_item_price);
            buttonEdit = itemView.findViewById(R.id.image_button_edit);
            buttonBin = itemView.findViewById(R.id.image_button_bin);

            // Set click listener for item click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (storeProductRecyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            storeProductRecyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });

            // Set click listener for edit button click
            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (storeProductRecyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            storeProductRecyclerViewInterface.onEditClick(position);
                        }
                    }
                }
            });

            // Set click listener for delete button click
            buttonBin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (storeProductRecyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            storeProductRecyclerViewInterface.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}

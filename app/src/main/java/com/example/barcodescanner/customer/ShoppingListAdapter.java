package com.example.barcodescanner.customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ShoppingListAdapter is a custom RecyclerView adapter for the items displayed on the shopping list.
 * It binds data from a Map of ProductModel and quantity pairs to the RecyclerView items.
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.VideoViewHolder> {

    // Map representing the shopping list
    // (pairs of ProductModels (Products) and quantity (Amount of products added to shopping list by user)
    static Map<ProductModel, Integer> shoppingList;
    // List of ProductModel objects for the items in the shopping list
    List<ProductModel> itemList;
    // RecyclerViewInterface for handling clicking on the product and increment/decrement actions
    private ProductRecyclerViewInterface productRecyclerViewInterface;

    /**
     * Constructor for ShoppingListAdapter.
     * Initializes the adapter with a shopping list map and a ProductRecyclerViewInterface.
     */
    public ShoppingListAdapter(Map<ProductModel, Integer> shoppingList, ProductRecyclerViewInterface productRecyclerViewInterface) {
        this.shoppingList = shoppingList;
        this.productRecyclerViewInterface = productRecyclerViewInterface;
        itemList = new ArrayList<>();
        // Populate the itemList with ProductModel objects from the shopping list map
        for (ProductModel pm : shoppingList.keySet()) {
            itemList.add(pm);
        }
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoplist, parent, false);
        return new VideoViewHolder(itemView, productRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        ProductModel video_item = itemList.get(holder.getAdapterPosition());
        holder.image_view.setImageResource(video_item.getProductImage(0));
        holder.product_name.setText(video_item.getProductName());
        holder.bottom_name.setText(video_item.getCategory());
        holder.position = holder.getAdapterPosition();
        holder.item = video_item;
        holder.count = shoppingList.get(video_item);
        holder.value.setText(String.valueOf(holder.count));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * ViewHolder for the shopping list items.
     * Contains views for displaying product information and handling user interactions.
     */
    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view;
        TextView product_name;
        TextView bottom_name;
        int position;
        ProductModel item;
        TextView value;
        int count;

        /**
         * Constructor for VideoViewHolder.
         * Initializes the ViewHolder with the itemView and sets up click listeners for item interactions.
         *
         * @param itemView                 The View for one item in the RecyclerView.
         * @param productRecyclerViewInterface The interface for handling item clicks and increment/decrement actions.
         */
        public VideoViewHolder(@NonNull View itemView, ProductRecyclerViewInterface productRecyclerViewInterface) {
            super(itemView);
            image_view = itemView.findViewById(R.id.image_view);
            product_name = itemView.findViewById(R.id.product_name);
            bottom_name = itemView.findViewById(R.id.bottom_name);
            value = itemView.findViewById(R.id.counter);
            value.setText(String.valueOf(count));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productRecyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            productRecyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });

            itemView.findViewById(R.id.incBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productRecyclerViewInterface.increment(item);
                }
            });
            itemView.findViewById(R.id.decBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productRecyclerViewInterface.decrement(item);
                }
            });
        }
    }
}

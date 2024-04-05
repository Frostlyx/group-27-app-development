package com.example.barcodescanner.customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.List;

/**
 * RecyclerView adapter for displaying a list of stores.
 * It binds data from a list of StoreModel objects to the RecyclerView items.
 */
public class ProductShopAdapter extends RecyclerView.Adapter<ProductShopAdapter.VideoViewHolder> {

    // Interface for handling item clicks
    private final ProductRecyclerViewInterface recyclerViewInterface;
    // List of StoreModel objects representing the stores to display
    List<StoreModel> storeList;

    /**
     * Constructor for ProductShopAdapter.
     * Initializes the adapter with a list of StoreModel objects and a ProductRecyclerViewInterface.
     */
    public ProductShopAdapter(List<StoreModel> storeList, ProductRecyclerViewInterface recyclerViewInterface) {
        this.storeList = storeList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productstore, parent, false);
        return new VideoViewHolder(itemView, recyclerViewInterface);
    }

    /**
     * Replaces the contents of a ViewHolder with data from the store list.
     */
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        StoreModel store_item = storeList.get(position);
        holder.image_view.setImageResource(store_item.getStoreImage(0));
        holder.store_name.setText(store_item.getStoreName());
        holder.location_name.setText(store_item.getStoreLocation());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return storeList.size();
    }

    /**
     * ViewHolder for the store items.
     * Contains views for displaying store information and handling user interactions.
     */
    public class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view;
        TextView store_name;
        TextView location_name;

        /**
         * Constructor for VideoViewHolder.
         * Initializes the ViewHolder with the itemView and sets up a click listener for item interactions.
         *
         * @param itemView                 The View for one item in the RecyclerView.
         * @param recyclerViewInterface The interface for handling item clicks.
         */
        public VideoViewHolder(@NonNull View itemView, ProductRecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            image_view = itemView.findViewById(R.id.store_img);
            store_name = itemView.findViewById(R.id.sName);
            location_name = itemView.findViewById(R.id.priName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

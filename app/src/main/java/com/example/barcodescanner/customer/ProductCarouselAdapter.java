package com.example.barcodescanner.customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.List;

/**
 * Adapter class for displaying products in a carousel.
 */
public class ProductCarouselAdapter extends RecyclerView.Adapter<ProductCarouselAdapter.VideoViewHolder> {

    // List to hold the store items
    List<StoreModel> storeList;

    // Constructor to initialize the adapter with a list of store items.
    public ProductCarouselAdapter(List<StoreModel> storeList) {
        this.storeList = storeList;
    }

    // Inflate the item layout for the carousel
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new VideoViewHolder(itemView);
    }

    // Bind the store item to the view holder
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        StoreModel store_item = storeList.get(position);
        holder.imageView.setImageResource(store_item.getStoreImage(0));
    }

    // Gets the number of items in the store list
    @Override
    public int getItemCount() {
        return storeList.size();
    }

    /**
     * View holder class to hold the views of each item in the carousel.
     */
    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        // Constructor to initialize the view holder with the item view
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the image view
            imageView = itemView.findViewById(R.id.product_img);
        }
    }
}

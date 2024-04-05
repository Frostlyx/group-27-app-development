package com.example.barcodescanner.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter for displaying a list of favorite products.
 */
public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.VideoViewHolder> {

    // List of favorite products
    List<ProductModel> itemList;
    // Interface for item click and favorite check events
    private final ProductRecyclerViewInterface productRecyclerViewInterface;
    // ViewModel for accessing user's favorite list
    UserListViewModel userListViewModel;

    // Constructor
    public FavouritesAdapter(Context context, List<ProductModel> itemList, ProductRecyclerViewInterface productRecyclerViewInterface) {
        this.itemList = itemList;
        this.productRecyclerViewInterface = productRecyclerViewInterface;
        // Initialize ViewModel
        this.userListViewModel = ((MainActivity) context).getUserListViewModel();
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the item
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        // Create a new ViewHolder instance
        return new VideoViewHolder(itemView, productRecyclerViewInterface);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        // Get the ProductModel at the current position
        // Sets its image, name, category
        ProductModel video_item = itemList.get(position);
        holder.imageView.setImageResource(video_item.getProductImage(0));
        holder.productName.setText(video_item.getProductName());
        holder.bottomName.setText(video_item.getCategory());
        holder.item = video_item;
        holder.favouritesButton.setChecked(isFavourite(video_item));
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // Method to check if the given product is in the user's favorites list
    private boolean isFavourite(ProductModel item) {
        ArrayList<ProductModel> favouritesList = userListViewModel.getFavouritesList().getValue();
        for (ProductModel pm : favouritesList) {
            if (pm.getProductName().equals(item.getProductName())) {
                return true;
            }
        }
        return false;
    }

    // ViewHolder class to hold references to views in the item layout
    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView productName;
        TextView bottomName;
        ProductModel item;
        CheckBox favouritesButton;

        // Constructor
        public VideoViewHolder(@NonNull View itemView, ProductRecyclerViewInterface productRecyclerViewInterface) {
            super(itemView);
            // Initialize views
            imageView = itemView.findViewById(R.id.image_view);
            productName = itemView.findViewById(R.id.product_name);
            bottomName = itemView.findViewById(R.id.bottom_name);
            favouritesButton = itemView.findViewById(R.id.cbHeart);

            // Set click listener for item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productRecyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        // Notify interface of item click event
                        if (position != RecyclerView.NO_POSITION) {
                            productRecyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });

            // Set click listener for favorites button
            favouritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Notify interface of favorite button click event
                    productRecyclerViewInterface.check(item);
                }
            });
        }
    }
}

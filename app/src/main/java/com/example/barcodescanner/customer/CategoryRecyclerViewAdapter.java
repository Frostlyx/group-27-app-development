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
 * Takes the given list of categories and displays it on the recycler view in CategoriesFragment.
 */
public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.VideoViewHolder> {

    // Interface for defining onClickListeners
    private final CategoryRecyclerViewInterface categoryRecyclerViewInterface;
    // List of categories
    List<StoreModel> marketList;

    // Constructor
    public CategoryRecyclerViewAdapter(List<StoreModel> marketList, CategoryRecyclerViewInterface categoryRecyclerViewInterface) {
        this.categoryRecyclerViewInterface = categoryRecyclerViewInterface;
        this.marketList = marketList;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new VideoViewHolder(itemView, categoryRecyclerViewInterface);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        // Get the category at the current position, set image resource, set category name
        StoreModel market_item = marketList.get(position);
        holder.imageView.setImageResource(market_item.getStoreImage(5));
        holder.storeName.setText(market_item.getStoreName());
    }

    // Return the size of categories list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return marketList.size();
    }

    // ViewHolder class to hold references to views in the item layout
    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView storeName;

        // Constructor
        public VideoViewHolder(@NonNull View itemView, CategoryRecyclerViewInterface categoryRecyclerViewInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.store_img);
            storeName = itemView.findViewById(R.id.sName);

            // Set onClickListener for the item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (categoryRecyclerViewInterface != null) {
                        // Get the position of the clicked item
                        int position = getAdapterPosition();
                        categoryRecyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }

}

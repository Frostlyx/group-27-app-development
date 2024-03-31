package com.example.barcodescanner.ui.store;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.ProductModel;

import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder> {

    List<ProductModel> itemList;

    /**
     * Initialize the dataset of the Adapter
     *
     * @param itemList List<Item> containing the data to populate views to be used
     * by RecyclerView
     */
    public StoreListAdapter(List<ProductModel> itemList) {
        this.itemList = itemList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.edit_store_column_image, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        ProductModel item = itemList.get(position);
        viewHolder.itemImage.setImageResource(item.getProductImage());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;

        public ViewHolder(View itemView) {
            super(itemView);
            // Define click listener for the ViewHolder's View

            itemImage = itemView.findViewById(R.id.image_substore);
        }
    }
}
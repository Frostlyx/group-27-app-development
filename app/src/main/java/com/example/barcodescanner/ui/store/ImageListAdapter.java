package com.example.barcodescanner.ui.store;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    List<Integer> imageList;

    /**
     * Initialize the dataset of the Adapter
     *
     * @param imageList List<Integer> containing the data to populate views to be used
     * by RecyclerView
     */
    public ImageListAdapter(List<Integer> imageList) {
        this.imageList = imageList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ImageListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.edit_store_column_image, viewGroup, false);

        return new ImageListAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ImageListAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Integer image = imageList.get(position);
        viewHolder.itemImage.setImageResource(image);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return imageList.size();
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

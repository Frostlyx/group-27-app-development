package com.example.barcodescanner.ui.store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.ProductModel;

import java.util.ArrayList;

public class DatabaseListAdapter extends RecyclerView.Adapter<DatabaseListAdapter.ViewHolder> {

    private StoreProductRecyclerViewInterface storeProductRecyclerViewInterface;
    Context context;
    StoreProductViewModel storeProductViewModel;
    ArrayList<ProductModel> productModels;

    public DatabaseListAdapter(Context context, StoreProductRecyclerViewInterface storeProductRecyclerViewInterface) {
        this.context = context;
        this.storeProductRecyclerViewInterface = storeProductRecyclerViewInterface;
        this.storeProductViewModel = ((StoreActivity) context).getStoreProductViewModel();
        this.productModels = storeProductViewModel.getProductModels().getValue();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.store_database_row_item, viewGroup, false);

        return new ViewHolder(view, storeProductRecyclerViewInterface);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        ProductModel item = productModels.get(position);
        viewHolder.itemImage.setImageResource(item.getProductImage(0));
        viewHolder.itemName.setText(item.getProductName());
        viewHolder.itemCategory.setText(item.getCategory());
        viewHolder.itemPrice.setText(item.getProductPrice());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productModels.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemCategory;
        TextView itemPrice;
        ImageButton buttonEdit;
        ImageButton buttonBin;

        public ViewHolder(View itemView, StoreProductRecyclerViewInterface storeProductRecyclerViewInterface) {
            super(itemView);
            // Define click listener for the ViewHolder's View

            itemImage = itemView.findViewById(R.id.image_item);
            itemName = itemView.findViewById(R.id.text_item_name);
            itemCategory = itemView.findViewById(R.id.text_item_category);
            itemPrice = itemView.findViewById(R.id.text_item_price);
            buttonEdit = itemView.findViewById(R.id.image_button_edit);
            buttonBin = itemView.findViewById(R.id.image_button_bin);

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
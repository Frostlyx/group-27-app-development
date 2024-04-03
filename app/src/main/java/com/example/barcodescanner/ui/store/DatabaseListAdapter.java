package com.example.barcodescanner.ui.store;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DatabaseListAdapter extends RecyclerView.Adapter<DatabaseListAdapter.ViewHolder> {

    List<ProductModel> itemList;

    /**
     * Initialize the dataset of the Adapter
     *
     * @param itemList List<Item> containing the data to populate views to be used
     * by RecyclerView
     */
    public DatabaseListAdapter(List<ProductModel> itemList) {
        this.itemList = itemList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.store_database_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        ProductModel item = itemList.get(position);
        viewHolder.itemImage.setImageResource(item.getProductImage(0));
        viewHolder.itemName.setText(item.getProductName());
        viewHolder.itemCategory.setText(item.getCategory());
        viewHolder.itemPrice.setText(item.getProductPrice());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getContext() != null && view.getContext() instanceof StoreActivity) {
                    ((StoreActivity) view.getContext()).replaceFragment(new EditProductFragment(item), view.getContext().getString(R.string.edit_product_title));
                }
            }
        });

        viewHolder.buttonBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Stores");
                DatabaseReference removalProduct = referenceProfile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(item.getProductName());
                removalProduct.getRef().removeValue();
            }
        });
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
        TextView itemName;
        TextView itemCategory;
        TextView itemPrice;
        ImageButton buttonEdit;
        ImageButton buttonBin;

        public ViewHolder(View itemView) {
            super(itemView);
            // Define click listener for the ViewHolder's View

            itemImage = itemView.findViewById(R.id.image_item);
            itemName = itemView.findViewById(R.id.text_item_name);
            itemCategory = itemView.findViewById(R.id.text_item_category);
            itemPrice = itemView.findViewById(R.id.text_price);
            buttonEdit = itemView.findViewById(R.id.image_button_edit);
            buttonBin = itemView.findViewById(R.id.image_button_bin);

        }
    }
}
package com.example.barcodescanner.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.ArrayList;

/**
 * Adapter class for displaying products on the main page for customers.
 */
public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder> {

    // Interface to define onClickListeners
    private final ProductRecyclerViewInterface recyclerViewInterface;
    Context context;
    // Shared model of products to display
    SharedViewModel sharedViewModel;
    UserListViewModel userListViewModel;
    ArrayList<ProductModel> productModels;

    // Constructor
    public ProductRecyclerViewAdapter(Context context,
                                      ProductRecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
        // Accessing SharedViewModel and UserListViewModel instances from MainActivity
        this.sharedViewModel = ((MainActivity) context).getSharedViewModel();
        this.userListViewModel = ((MainActivity) context).getUserListViewModel();
        // Getting the list of product models from SharedViewModel
        this.productModels = sharedViewModel.getProductModels().getValue();
    }

    @NonNull
    @Override
    public ProductRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout for individual rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_page_recycler_view_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewAdapter.MyViewHolder holder, int position) {
        // Binds data to views in each row based on position
        holder.productImage.setImageResource(productModels.get(position).getProductImage(0));
        holder.productName.setText(productModels.get(position).getProductName());
        holder.productPrice.setText(productModels.get(position).getProductPrice());
        holder.favouritesButton.setChecked(isFavourite(productModels.get(position)));
        // Set margin for the item view
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
        layoutParams.bottomMargin = context.getResources().getDimensionPixelSize(R.dimen.item_vertical_spacing);
        holder.itemView.setLayoutParams(layoutParams);
    }

    // Gets the item count in the shared model of products
    @Override
    public int getItemCount() {
        return productModels.size();
    }

    // Method to check if a product is in the user's favorites list
    private boolean isFavourite(ProductModel item) {
        ArrayList<ProductModel> favouritesList = userListViewModel.getFavouritesList().getValue();
        for (ProductModel pm : favouritesList) {
            if (pm.getProductName().equals(item.getProductName())) {
                return true;
            }
        }
        return false;
    }

    // Method to filter products based on search input
    public void searchProduct(String input) {
        ArrayList<ProductModel> filteredProductList = new ArrayList<>();
        for (ProductModel product : productModels) {
            if (product.getProductName().toLowerCase().contains(input.toLowerCase())) {
                filteredProductList.add(product);
            }
        }
        productModels = filteredProductList;
        notifyDataSetChanged();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Views in each row
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        CheckBox favouritesButton;
        ImageButton shoppingListButton;

        // Constructor
        public MyViewHolder(@NonNull View itemView, ProductRecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            // Initializing views
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            favouritesButton = itemView.findViewById(R.id.favourites_button);
            shoppingListButton = itemView.findViewById(R.id.shopping_list_button);

            // Click listener for item
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

            // Click listener for favourites button
            favouritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onFavouritesClick(position);
                        }
                    }
                }
            });

            // Click listener for shopping list button
            shoppingListButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onShoppingListClick(position);
                        }
                    }
                }
            });
        }
    }
}

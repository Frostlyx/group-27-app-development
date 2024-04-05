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

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder> {
    private final ProductRecyclerViewInterface recyclerViewInterface;
    Context context;
    SharedViewModel sharedViewModel;
    UserListViewModel userListViewModel;
    ArrayList<ProductModel> productModels;

    public ProductRecyclerViewAdapter(Context context,
                                      ProductRecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
        this.sharedViewModel = ((MainActivity) context).getSharedViewModel();
        this.userListViewModel = ((MainActivity) context).getUserListViewModel();
        this.productModels = sharedViewModel.getProductModels().getValue();
    }
    @NonNull
    @Override
    public ProductRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout (giving a look to the rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_page_recycler_view_row, parent, false);

        return new ProductRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assigns values to the views in the main_page_recycler_view_row.xml file
        // based on position of the recycler view
        holder.productImage.setImageResource(productModels.get(position).getProductImage(0));
        holder.productName.setText(productModels.get(position).getProductName());
        holder.productPrice.setText(productModels.get(position).getProductPrice());
        holder.favouritesButton.setChecked(isFavourite(productModels.get(position)));
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
        layoutParams.bottomMargin = context.getResources().getDimensionPixelSize(R.dimen.item_vertical_spacing);
        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    private boolean isFavourite(ProductModel item) {
        ArrayList<ProductModel> favouritesList = userListViewModel.getFavouritesList().getValue();
        for (ProductModel pm : favouritesList) {
            if (pm.getProductName().equals(item.getProductName())) {
                return true;
            }
        }
        return false;
    }

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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grabbing the views from main_page_recycler_view_row.xml
        // works similar to onCreate method
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        CheckBox favouritesButton;
        ImageButton shoppingListButton;
        public MyViewHolder(@NonNull View itemView, ProductRecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            favouritesButton = itemView.findViewById(R.id.favourites_button);
            shoppingListButton = itemView.findViewById(R.id.shopping_list_button);

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

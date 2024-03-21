package com.example.barcodescanner.customer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder> {
    private final ProductRecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<ProductModel> productModels;

    public ProductRecyclerViewAdapter(Context context, ArrayList<ProductModel> productModels,
                                      ProductRecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.productModels = productModels;
        this.recyclerViewInterface = recyclerViewInterface;

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

        holder.productImage.setImageResource(productModels.get(position).getProductImage());
        holder.productName.setText(productModels.get(position).getProductName());
        holder.productPrice.setText(productModels.get(position).getProductPrice());

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
        layoutParams.bottomMargin = context.getResources().getDimensionPixelSize(R.dimen.item_vertical_spacing);
        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public void sortBy(String criteria) {
        switch (criteria) {
            case "name_ascending":
                Collections.sort(productModels, new Comparator<ProductModel>() {
                    @Override
                    public int compare(ProductModel item1, ProductModel item2) {
                        return item1.getProductName().compareTo(item2.getProductName());
                    }
                });

                Log.i("Sort Name Ascending", "Name");
                for (ProductModel productModel : productModels) {
                    Log.i("Sort", "Name: " + productModel.getProductName());
                }
                break;
            case "name_descending":
                Collections.sort(productModels, new Comparator<ProductModel>() {
                    @Override
                    public int compare(ProductModel item1, ProductModel item2) {
                        return item1.getProductName().compareTo(item2.getProductName());
                    }
                });
                Collections.reverse(productModels);

                Log.i("Sort Name Descending", "Name");
                for (ProductModel productModel : productModels) {
                    Log.i("Sort", "Name: " + productModel.getProductName());
                }
                break;
            case "price_ascending":
                Collections.sort(productModels, new Comparator<ProductModel>() {
                    @Override
                    public int compare(ProductModel item1, ProductModel item2) {
                        return item1.getProductPrice().compareTo(item2.getProductPrice());
                    }
                });

                Log.i("Sort Price Ascending", "Price");
                for (ProductModel productModel : productModels) {
                    Log.i("Sort", "Price: " + productModel.getProductPrice());
                }
                break;
            case "price_descending":
                Collections.sort(productModels, new Comparator<ProductModel>() {
                    @Override
                    public int compare(ProductModel item1, ProductModel item2) {
                        return item1.getProductPrice().compareTo(item2.getProductPrice());
                    }
                });
                Collections.reverse(productModels);

                Log.i("Sort Price Descending", "Price");
                for (ProductModel productModel : productModels) {
                    Log.i("Sort", "Price: " + productModel.getProductPrice());
                }
                break;
        }
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grabbing the views from main_page_recycler_view_row.xml
        // works similar to onCreate method

        ImageView productImage;
        TextView productName;
        TextView productPrice;
        ImageButton favouritesButton;
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

            // Use this if you only want it to react when text or image is clicked

//            productImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (recyclerViewInterface != null) {
//                        int position = getAdapterPosition();
//
//                        if (position != RecyclerView.NO_POSITION) {
//                            recyclerViewInterface.onItemClick(position);
//                        }
//                    }
//                }
//            });
//
//            productName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (recyclerViewInterface != null) {
//                        int position = getAdapterPosition();
//
//                        if (position != RecyclerView.NO_POSITION) {
//                            recyclerViewInterface.onItemClick(position);
//                        }
//                    }
//                }
//            });
        }
    }
}

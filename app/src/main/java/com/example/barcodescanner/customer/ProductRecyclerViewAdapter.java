package com.example.barcodescanner.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.ArrayList;

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
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grabbing the views from main_page_recycler_view_row.xml
        // works similar to onCreate method

        ImageView productImage;
        TextView productName;
        TextView productPrice;
        public MyViewHolder(@NonNull View itemView, ProductRecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);

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

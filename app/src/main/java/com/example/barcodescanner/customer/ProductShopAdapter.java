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

public class ProductShopAdapter extends RecyclerView.Adapter<ProductShopAdapter.VideoViewHolder> {

    private final ProductRecyclerViewInterface recyclerViewInterface;
    List<StoreModel> storeList;

    public ProductShopAdapter(List<StoreModel> storeList, ProductRecyclerViewInterface recyclerViewInterface) {
        this.storeList = storeList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productstore, parent, false);
        return new VideoViewHolder(itemView, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        StoreModel store_item = storeList.get(position);
        holder.image_view.setImageResource(store_item.getStoreImage(0));
        holder.store_name.setText(store_item.getStoreName());
        holder.location_name.setText(store_item.getStoreLocation());
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        ImageView image_view;
        TextView store_name;
        TextView location_name;

        public VideoViewHolder(@NonNull View itemView, ProductRecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            image_view = itemView.findViewById(R.id.store_img);
            store_name = itemView.findViewById(R.id.sName);
            location_name = itemView.findViewById(R.id.priName);

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
        }


    }

}

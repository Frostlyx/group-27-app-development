package com.example.barcodescanner.customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.List;

public class ProductCarouselAdapter extends RecyclerView.Adapter<ProductCarouselAdapter.VideoViewHolder> {


    List<StoreModel> storeList;

    public ProductCarouselAdapter(List<StoreModel> storeList) {
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        StoreModel store_item = storeList.get(position);
        holder.image_view.setImageResource(store_item.getStoreImage(0));
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        ImageView image_view;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            image_view = itemView.findViewById(R.id.product_img);
        }
    }

}

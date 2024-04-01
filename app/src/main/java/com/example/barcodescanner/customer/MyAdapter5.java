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

public class MyAdapter5 extends RecyclerView.Adapter<MyAdapter5.VideoViewHolder> {


    List<StoreModel> storeList;

    public MyAdapter5(List<StoreModel> storeList) {
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prouctstore, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        StoreModel store_item = storeList.get(position);
        holder.image_view.setImageResource(store_item.getStoreImage(0));
        holder.store_name.setText(store_item.getStoreName());
        holder.price_name.setText(store_item.getStoreLocation());
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        ImageView image_view;
        TextView store_name;
        TextView price_name;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            image_view = itemView.findViewById(R.id.store_img);
            store_name = itemView.findViewById(R.id.sName);
            price_name = itemView.findViewById(R.id.priName);


        }
    }

}

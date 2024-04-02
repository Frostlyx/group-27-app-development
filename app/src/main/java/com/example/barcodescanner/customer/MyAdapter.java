package com.example.barcodescanner.customer;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VideoViewHolder> {


    private  List<ProductModel> itemList;

    public MyAdapter(List<ProductModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        ProductModel video_item = itemList.get(position);
        holder.image_view.setImageResource(video_item.getProductImage(0));
        holder.product_name.setText(video_item.getProductName());
        holder.bottom_name.setText(video_item.getCategory());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        ImageView image_view;
        TextView product_name;
        TextView bottom_name;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            image_view = itemView.findViewById(R.id.image_view);
            product_name = itemView.findViewById(R.id.product_name);
            bottom_name = itemView.findViewById(R.id.bottom_name);
        }
    }

}

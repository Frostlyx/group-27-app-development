package com.example.barcodescanner;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter4 extends RecyclerView.Adapter<MyAdapter4.VideoViewHolder> {


    List<Market> marketList;

    public MyAdapter4(List<Market> marketList) {
        this.marketList = marketList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Market market_item = marketList.get(position);
        holder.image_view.setImageResource(market_item.getImage());
    }

    @Override
    public int getItemCount() {
        return marketList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        ImageView image_view;
        TextView product_name;
        TextView bottom_name;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            image_view = itemView.findViewById(R.id.product_img);
        }
    }

}

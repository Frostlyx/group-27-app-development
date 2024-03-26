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

public class MyAdapter6 extends RecyclerView.Adapter<MyAdapter6.VideoViewHolder> {


    List<Market> marketList;

    public MyAdapter6(List<Market> marketList) {
        this.marketList = marketList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prouctstore, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Market market_item = marketList.get(position);
        holder.image_view.setImageResource(market_item.getImage());
        holder.store_name.setText(market_item.getName());
        holder.price_name.setText(market_item.getLocation());
    }

    @Override
    public int getItemCount() {
        return marketList.size();
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

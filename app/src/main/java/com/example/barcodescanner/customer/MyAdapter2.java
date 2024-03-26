package com.example.barcodescanner.customer;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.VideoViewHolder> {


    List<Item> itemList;

    public MyAdapter2(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoplist, parent, false);


        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Item video_item = itemList.get(position);
        holder.image_view.setImageResource(video_item.getImage());
        holder.product_name.setText(video_item.getName());
        holder.bottom_name.setText(video_item.getCategory());
        holder.position = position;
        holder.item = video_item;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        ImageView image_view;
        TextView product_name;
        TextView bottom_name;
        ImageView dec_btn;
        int position;
        Item item;
        TextView value;
        int count = 1;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            image_view = itemView.findViewById(R.id.image_view);
            product_name = itemView.findViewById(R.id.product_name);
            bottom_name = itemView.findViewById(R.id.bottom_name);
            dec_btn = itemView.findViewById(R.id.decBtn);
            value = itemView.findViewById(R.id.counter);
            value.setText(String.valueOf(count));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo", "onClick: Item Clciked " + position + " item " + item.name);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    ProductFragment categoriesFragment = new ProductFragment();
                    FragmentTransaction fm = activity.getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frame_layout, categoriesFragment).commit();
                }
            });

            itemView.findViewById(R.id.incBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    value.setText(String.valueOf(count));

                    if (count > 1){
                        dec_btn.setImageResource(R.drawable.baseline_add_white);
                    }

                }
            });
            itemView.findViewById(R.id.decBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count>1){
                        count--;
                        value.setText(String.valueOf(count));
                    }
                    if (count == 1){
                        dec_btn.setImageResource(R.mipmap.store_database_bin_foreground);
                    }
                }
            });

            if (count == 1){
                dec_btn.setImageResource(R.mipmap.store_database_bin_foreground);
            }



        }



    }

}

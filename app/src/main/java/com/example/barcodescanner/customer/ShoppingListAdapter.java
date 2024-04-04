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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.VideoViewHolder> {

    static Map<ProductModel, Integer> shoppingList;
    List<ProductModel> itemList;
    private ProductRecyclerViewInterface productRecyclerViewInterface;

    public ShoppingListAdapter(Map<ProductModel, Integer> shoppingList, ProductRecyclerViewInterface productRecyclerViewInterface) {

        this.shoppingList = shoppingList;
        this.productRecyclerViewInterface = productRecyclerViewInterface;
        itemList = new ArrayList<>();
        for (Map.Entry<ProductModel, Integer> entry : shoppingList.entrySet()) {
            itemList.add(entry.getKey());
        }
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoplist, parent, false);


        return new VideoViewHolder(itemView, productRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        ProductModel video_item = itemList.get(position);
        holder.image_view.setImageResource(video_item.getProductImage(0));
        holder.product_name.setText(video_item.getProductName());
        holder.bottom_name.setText(video_item.getCategory());
        holder.position = position;
        holder.item = video_item;
        holder.count = shoppingList.get(video_item);
        holder.value.setText(String.valueOf(holder.count));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        ImageView image_view;
        TextView product_name;
        TextView bottom_name;
        int position;
        ProductModel item;
        TextView value;
        int count;

        public VideoViewHolder(@NonNull View itemView, ProductRecyclerViewInterface productRecyclerViewInterface) {
            super(itemView);
            image_view = itemView.findViewById(R.id.image_view);
            product_name = itemView.findViewById(R.id.product_name);
            bottom_name = itemView.findViewById(R.id.bottom_name);
            value = itemView.findViewById(R.id.counter);
            value.setText(String.valueOf(count));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo", "onClick: Item Clciked " + position + " item " + item.productName);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    ProfileFragment categoriesFragment = new ProfileFragment();
                    FragmentTransaction fm = activity.getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.remzi, categoriesFragment).commit();
                }
            });

            itemView.findViewById(R.id.incBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productRecyclerViewInterface.increment(item);
                }
            });
            itemView.findViewById(R.id.decBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productRecyclerViewInterface.decrement(item);
                }
            });

        }



    }

}

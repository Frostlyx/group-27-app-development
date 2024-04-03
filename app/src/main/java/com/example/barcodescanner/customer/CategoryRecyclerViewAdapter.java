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

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.VideoViewHolder> {

    private final CategoryRecyclerViewInterface categoryRecyclerViewInterface;
    List<StoreModel> marketList;

    public CategoryRecyclerViewAdapter(List<StoreModel> marketList, CategoryRecyclerViewInterface categoryRecyclerViewInterface) {
        this.categoryRecyclerViewInterface = categoryRecyclerViewInterface;
        this.marketList = marketList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new VideoViewHolder(itemView, categoryRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        StoreModel market_item = marketList.get(position);
        holder.image_view.setImageResource(market_item.getStoreImage(5));
        holder.store_name.setText(market_item.getStoreName());
    }

    @Override
    public int getItemCount() {
        return marketList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        ImageView image_view;
        TextView store_name;

        public VideoViewHolder(@NonNull View itemView, CategoryRecyclerViewInterface categoryRecyclerViewInterface) {
            super(itemView);
            image_view = itemView.findViewById(R.id.store_img);
            store_name = itemView.findViewById(R.id.sName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (categoryRecyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        categoryRecyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }

}

package com.example.barcodescanner.customer;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.VideoViewHolder> {

    List<ProductModel> itemList;
    private ProductRecyclerViewInterface productRecyclerViewInterface;
    UserListViewModel userListViewModel;

    public FavouritesAdapter(Context context, List<ProductModel> itemList, ProductRecyclerViewInterface productRecyclerViewInterface) {
        this.itemList = itemList;
        this.productRecyclerViewInterface = productRecyclerViewInterface;
        this.userListViewModel = ((MainActivity) context).getUserListViewModel();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new VideoViewHolder(itemView, productRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        ProductModel video_item = itemList.get(position);
        holder.image_view.setImageResource(video_item.getProductImage(0));
        holder.product_name.setText(video_item.getProductName());
        holder.bottom_name.setText(video_item.getCategory());
        holder.item = video_item;
        holder.favouritesButton.setChecked(isFavourite(video_item));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private boolean isFavourite(ProductModel item) {
        ArrayList<ProductModel> favouritesList = userListViewModel.getFavouritesList().getValue();
        for (ProductModel pm : favouritesList) {
            if (pm.getProductName().equals(item.getProductName())) {
                return true;
            }
        }
        return false;
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        ImageView image_view;
        TextView product_name;
        TextView bottom_name;
        ProductModel item;
        CheckBox favouritesButton;

        public VideoViewHolder(@NonNull View itemView, ProductRecyclerViewInterface productRecyclerViewInterface) {
            super(itemView);
            image_view = itemView.findViewById(R.id.image_view);
            product_name = itemView.findViewById(R.id.product_name);
            bottom_name = itemView.findViewById(R.id.bottom_name);
            favouritesButton = itemView.findViewById(R.id.cbHeart);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    ProductFragment categoriesFragment = new ProductFragment();
                    FragmentTransaction fm = activity.getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frame_layout, categoriesFragment).commit();
                }
            });

            favouritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productRecyclerViewInterface.check(item);
                }
            });
        }
    }
}

package com.example.barcodescanner.customer;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barcodescanner.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FavouritesFragment extends Fragment implements ProductRecyclerViewInterface{


    private List<ProductModel> notFavouritesList;
    private RecyclerView favRecView;
    private FavouritesAdapter myAdapter;
    private UserListViewModel userListViewModel;


    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
        container.clearDisappearingChildren();
        userListViewModel = ((MainActivity) getActivity()).getUserListViewModel();
        favRecView = rootView.findViewById(R.id.fav_recyclerview);
        favRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notFavouritesList = new ArrayList<>();
        refresh();

        userListViewModel.getFavouritesList().observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> favouritesList) {
                refresh();
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ArrayList<ProductModel> tempFavouritesList = userListViewModel.getFavouritesList().getValue();
        for (ProductModel pm : notFavouritesList) {
            tempFavouritesList.remove(pm);
        }
        userListViewModel.setFavouritesList(tempFavouritesList);
    }

    private void refresh() {
        List<ProductModel> tempFavouritesList = userListViewModel.getFavouritesList().getValue();
        myAdapter = new FavouritesAdapter(getContext(), tempFavouritesList, this);
        favRecView.setAdapter(myAdapter);
    }

    //changing rotation
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if the orientation has changed
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Replace the current fragment with the landscape version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.remzi, new FavouritesFragment())
                    .commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Replace the current fragment with the portrait version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.remzi, new FavouritesFragment())
                    .commit();
        }
    }

    @Override
    public void onItemClick(int position) {
        ProductModel item = userListViewModel.getFavouritesList().getValue().get(position);
        FavouritesAdapter.VideoViewHolder viewHolder = (FavouritesAdapter.VideoViewHolder) favRecView.findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            viewHolder.image_view.setImageResource(item.getProductImage(0));
            viewHolder.product_name.setText(item.getProductName());
            viewHolder.bottom_name.setText(item.getCategory());
        }
        if (getContext() != null && getContext() instanceof MainActivity) {
            ((MainActivity) getContext()).replaceFragment(new ProductFragment(item, position), getContext().getString(R.string.product_page_title));
        }
    }

    @Override
    public void onFavouritesClick(int position) {
    }

    @Override
    public void onShoppingListClick(int position) {
    }

    @Override
    public void increment(ProductModel item) {
    }

    @Override
    public void decrement(ProductModel item) {
    }

    @Override
    public void check(ProductModel item) {
        ArrayList<ProductModel> tempFavouritesList = userListViewModel.getFavouritesList().getValue();
        if (notFavouritesList.contains(item)) {
            notFavouritesList.remove(item);
        } else {
            notFavouritesList.add(item);
        }
    }
}
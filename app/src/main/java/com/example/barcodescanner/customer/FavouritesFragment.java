package com.example.barcodescanner.customer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to display a list of favorite products.
 */
public class FavouritesFragment extends Fragment implements ProductRecyclerViewInterface {

    // List to store products that are not in favorites
    private List<ProductModel> notFavouritesList;
    // RecyclerView to display favorite products
    private RecyclerView favRecView;
    // Adapter for the RecyclerView
    private FavouritesAdapter favouritesAdapter;
    // ViewModel for accessing user's favorite list
    private UserListViewModel userListViewModel;

    // Required empty public constructor
    public FavouritesFragment() {}

    // Called to create the view hierarchy associated with the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
        container.clearDisappearingChildren();

        // Initialize ViewModel
        userListViewModel = ((MainActivity) getActivity()).getUserListViewModel();
        // Initialize RecyclerView
        favRecView = rootView.findViewById(R.id.fav_recyclerview);
        favRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notFavouritesList = new ArrayList<>();
        refresh();

        // Observe changes in favorite list
        userListViewModel.getFavouritesList().observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> favouritesList) {
                refresh();
            }
        });

        return rootView;
    }

    // Called when the fragment is destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();

        // Update the user's favorites list in ViewModel
        ArrayList<ProductModel> tempFavouritesList = userListViewModel.getFavouritesList().getValue();
        for (ProductModel pm : notFavouritesList) {
            tempFavouritesList.remove(pm);
        }
        userListViewModel.setFavouritesList(tempFavouritesList);
    }

    // Method to refresh the RecyclerView with updated data
    private void refresh() {
        List<ProductModel> tempFavouritesList = userListViewModel.getFavouritesList().getValue();
        favouritesAdapter = new FavouritesAdapter(getContext(), tempFavouritesList, this);
        favRecView.setAdapter(favouritesAdapter);
    }

    // Called when device configuration changes (e.g., screen orientation)
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if the orientation has changed
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ||
                newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Replace the current fragment with the new instance
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.remzi, new FavouritesFragment())
                    .commit();
        }
    }

    // Click listener for item click event
    @Override
    public void onItemClick(int position) {
        ProductModel item = userListViewModel.getFavouritesList().getValue().get(position);
        FavouritesAdapter.VideoViewHolder viewHolder = (FavouritesAdapter.VideoViewHolder) favRecView.findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            viewHolder.imageView.setImageResource(item.getProductImage(0));
            viewHolder.productName.setText(item.getProductName());
            viewHolder.bottomName.setText(item.getCategory());
        }
        // Navigate to ProductFragment
        if (getContext() != null && getContext() instanceof MainActivity) {
            ((MainActivity) getContext()).replaceFragment(new ProductFragment(item, position), getContext().getString(R.string.product_page_title));
        }
    }

    // Methods for other click events (not implemented)
    @Override
    public void onFavouritesClick(int position) {}
    @Override
    public void onShoppingListClick(int position) {}
    @Override
    public void increment(ProductModel item) {}
    @Override
    public void decrement(ProductModel item) {}

    // Click listener for favorite button click event
    @Override
    public void check(ProductModel item) {
        DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference referenceCustomers = referenceUsers.child("Customers");
        DatabaseReference referenceCurrentUser = referenceCustomers.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference referenceFavouritesList = referenceCurrentUser.child("Favourites List");

        if (notFavouritesList.contains(item)) {
            notFavouritesList.remove(item);
            referenceFavouritesList.child(item.getProductBarcode()).setValue(item);
        } else {
            notFavouritesList.add(item);
            referenceFavouritesList.child(item.getProductBarcode()).removeValue();
        }
    }
}

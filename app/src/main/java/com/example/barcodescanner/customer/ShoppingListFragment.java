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
import java.util.Map;

/**
 * Customer side fragment to display their shopping list items.
 */
public class ShoppingListFragment extends Fragment implements ProductRecyclerViewInterface {

    // recycler view to display the items
    RecyclerView shopRecView;
    ShoppingListAdapter shoppingListAdapter;
    private UserListViewModel userListViewModel;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        // Clear disappearing children
        container.clearDisappearingChildren();

        // Initialize ViewModel
        userListViewModel = ((MainActivity) getActivity()).getUserListViewModel();

        // Initialize RecyclerView
        shopRecView = rootView.findViewById(R.id.shop_recyclerview);
        shopRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Refresh shopping list
        refresh();

        // Observing changes in the shopping list
        userListViewModel.getShoppingList().observe(getViewLifecycleOwner(), new Observer<Map<ProductModel, Integer>>() {
            @Override
            public void onChanged(Map<ProductModel, Integer> shoppingList) {
                refresh();
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Remove items with zero quantity from the shopping list and Firebase database
        DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference referenceCustomers = referenceUsers.child("Customers");
        DatabaseReference referenceCurrentUser = referenceCustomers.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference referenceShoppingList = referenceCurrentUser.child("Shopping List");

        // Retrieves current shopping list from user
        Map<ProductModel, Integer> tempShoppingList = userListViewModel.getShoppingList().getValue();
        ArrayList<ProductModel> deleteList = new ArrayList<>();
        // Remove any item with a quantity of zero or less
        for (Map.Entry<ProductModel, Integer> entry : tempShoppingList.entrySet()) {
            if (entry.getValue() <= 0) {
                deleteList.add(entry.getKey());
            }
        }
        // Remove the items from the shopping list
        for (ProductModel pm : deleteList) {
            tempShoppingList.remove(pm);
            referenceShoppingList.child(pm.getProductBarcode()).removeValue();
        }
        userListViewModel.setShoppingList(tempShoppingList);
    }

    // Refresh the RecyclerView with updated shopping list data
    private void refresh() {
        Map<ProductModel, Integer> tempShoppingList = userListViewModel.getShoppingList().getValue();
        shoppingListAdapter = new ShoppingListAdapter(tempShoppingList, this);
        shopRecView.setAdapter(shoppingListAdapter);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Handling configuration changes like orientation
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ||
                newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Replace the current fragment with itself to handle orientation change
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new ShoppingListFragment())
                    .commit();
        }
    }

    // Interface methods implementation
    @Override
    public void onItemClick(int position) {
        // Handle item click event
        // Placeholder code, still needs implementation
    }

    @Override
    public void onFavouritesClick(int position) {
        // Placeholder method, no action needed
    }

    @Override
    public void onShoppingListClick(int position) {
        // Placeholder method, no action needed
    }

    @Override
    public void check(ProductModel item) {
        // Placeholder method, no action needed
    }

    @Override
    public void increment(ProductModel item) {
        // Increment item quantity in the shopping list
        Map<ProductModel, Integer> tempShoppingList = userListViewModel.getShoppingList().getValue();
        int count = tempShoppingList.get(item);
        count++;
        tempShoppingList.put(item, count);
        userListViewModel.setShoppingList(tempShoppingList);
        refresh();
    }

    @Override
    public void decrement(ProductModel item) {
        // Decrement item quantity in the shopping list
        Map<ProductModel, Integer> tempShoppingList = userListViewModel.getShoppingList().getValue();
        int count = tempShoppingList.get(item);
        if (count > 0){
            count--;
        }
        tempShoppingList.put(item, count);
        userListViewModel.setShoppingList(tempShoppingList);
        refresh();
    }
}

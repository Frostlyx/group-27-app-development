package com.example.barcodescanner.customer;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.barcodescanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ShoppingListFragment extends Fragment implements ProductRecyclerViewInterface{

    List<ProductModel> itemList;
    RecyclerView shopRecView;
    ShoppingListAdapter myAdapter;
    ImageView imageView;
    private UserListViewModel userListViewModel;


    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        container.clearDisappearingChildren();
        userListViewModel = ((MainActivity) getActivity()).getUserListViewModel();
        shopRecView = rootView.findViewById(R.id.shop_recyclerview);
        shopRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refresh();

        userListViewModel.getShoppingList().observe(getViewLifecycleOwner(), new Observer<Map<ProductModel, Integer>>() {
            @Override
            public void onChanged(Map<ProductModel, Integer> shoppingList) {
                refresh();
            }
        });

        rootView.findViewById(R.id.cheapBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                CheapestFragment categoriesFragment = new CheapestFragment();
                FragmentTransaction fm = activity.getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_layout, categoriesFragment).commit();
            }
        });


        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference referenceCustomers = referenceUsers.child("Customers");
        DatabaseReference referenceCurrentUser = referenceCustomers.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference referenceShoppingList = referenceCurrentUser.child("Shopping List");


        Map<ProductModel, Integer> tempShoppingList = userListViewModel.getShoppingList().getValue();
        ArrayList<ProductModel> deleteList = new ArrayList<>();
        for (Map.Entry<ProductModel, Integer> entry : tempShoppingList.entrySet()) {
            if (entry.getValue() <= 0) {
                deleteList.add(entry.getKey());
            }
        }
        for (ProductModel pm : deleteList) {
            tempShoppingList.remove(pm);
            referenceShoppingList.child(pm.getProductBarcode()).removeValue();
        }
        userListViewModel.setShoppingList(tempShoppingList);
    }

    private void refresh() {
        Map<ProductModel, Integer> tempShoppingList = userListViewModel.getShoppingList().getValue();
        myAdapter = new ShoppingListAdapter(tempShoppingList, this);
        shopRecView.setAdapter(myAdapter);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if the orientation has changed
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Replace the current fragment with the landscape version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new ShoppingListFragment())
                    .commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Replace the current fragment with the portrait version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new ShoppingListFragment())
                    .commit();
        }
    }

    @Override
    public void onItemClick(int position) {
        // Still has to be implemented
        Map<ProductModel, Integer> shoppingList = userListViewModel.getShoppingList().getValue();
        itemList = new ArrayList<>();
        for (ProductModel key : shoppingList.keySet()) {
            itemList.add(key);
        }

        ProductModel item = itemList.get(position);
        ShoppingListAdapter.VideoViewHolder viewHolder = (ShoppingListAdapter.VideoViewHolder) shopRecView.findViewHolderForAdapterPosition(position);
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
    public void check(ProductModel item) {
    }

    @Override
    public void increment(ProductModel item) {
        Map<ProductModel, Integer> tempShoppingList = userListViewModel.getShoppingList().getValue();
        int count = tempShoppingList.get(item);
        count++;
        tempShoppingList.put(item, count);
        userListViewModel.setShoppingList(tempShoppingList);
        refresh();
    }

    @Override
    public void decrement(ProductModel item) {
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
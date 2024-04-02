package com.example.barcodescanner.customer;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.ui.store.EditStoreFragment;
import com.example.barcodescanner.ui.store.MyStoreFragment;

import java.util.ArrayList;
import java.util.List;


public class ShoppingListFragment extends Fragment {


    List<Item> itemList;
    RecyclerView favRecView;
    MyAdapter2 myAdapter;
    ImageView imageView;


    public ShoppingListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        container.clearDisappearingChildren();
        itemList = generateItems();

        favRecView = rootView.findViewById(R.id.recyclerview);
        favRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myAdapter = new MyAdapter2(itemList);
        favRecView.setAdapter(myAdapter);

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


    private List<Item> generateItems(){
        List<Item> item = new ArrayList<>();
        item.add(new Item("deneme", "denenmis", R.drawable.bread));
        return item;
    }


    //changing rotation
    //changing rotation
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




}
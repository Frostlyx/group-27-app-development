package com.example.barcodescanner.customer;

import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.List;


public class ShoppingListFragment extends Fragment {


    List<ProductModel> itemList;
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
                fm.replace(R.id.remzi, categoriesFragment).commit();
            }
        });


        return rootView;
    }


    private List<ProductModel> generateItems(){
        List<ProductModel> item = new ArrayList<>();
        item.add(new ProductModel("deneme", "denenmis", generateImages(), "deneme", "deneme","s","s"));
        return item;
    }

    private List<Integer> generateImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        return images;
    }

}
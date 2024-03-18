package com.example.barcodescanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ShoppingListFragment extends Fragment {

    List<Item> itemList;
    RecyclerView favRecView;
    MyAdapter2 myAdapter;


    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        itemList = generateItems();

        favRecView = rootView.findViewById(R.id.recyclerview);
        favRecView.setLayoutManager(new LinearLayoutManager(getActivity()));


        myAdapter = new MyAdapter2(itemList);
        favRecView.setAdapter(myAdapter);

        return rootView;
    }

    private List<Item> generateItems(){
        List<Item> item = new ArrayList<>();
        item.add(new Item("deneme", "denenmis", R.drawable.bread));
        item.add(new Item("Tryout", "TRYKKK", R.drawable.bread));
        item.add(new Item("deneme", "denenmis", R.drawable.bread));
        item.add(new Item("Tryout", "TRYKKK", R.drawable.bread));
        item.add(new Item("deneme", "denenmis", R.drawable.bread));
        item.add(new Item("Tryout", "TRYKKK", R.drawable.bread));
        item.add(new Item("deneme", "denenmis", R.drawable.bread));
        item.add(new Item("Tryout", "TRYKKK", R.drawable.bread));
        item.add(new Item("deneme", "denenmis", R.drawable.bread));
        item.add(new Item("Tryout", "TRYKKK", R.drawable.bread));
        item.add(new Item("deneme", "denenmis", R.drawable.bread));
        item.add(new Item("Tryout", "TRYKKK", R.drawable.bread));


        return item;
    }


}
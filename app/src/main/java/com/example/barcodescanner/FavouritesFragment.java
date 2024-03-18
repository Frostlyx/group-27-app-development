package com.example.barcodescanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FavouritesFragment extends Fragment {


    private List<Item> itemList;
    private RecyclerView favRecView;
    private MyAdapter myAdapter;


    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);

        itemList = generateItems();

        favRecView = rootView.findViewById(R.id.recyclerview);
        favRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myAdapter = new MyAdapter(itemList);
        favRecView.setAdapter(myAdapter);

        return rootView;
    }

    private List<Item> generateItems(){
        List<Item> item = new ArrayList<>();
        item.add(new Item("deneme", "denenmis", R.drawable.bread));
        item.add(new Item("Tryout", "TRYKKK", R.drawable.bread));
        item.add(new Item("Hoave Mercy", "LORdd", R.drawable.bread));
        item.add(new Item("deneme", "denenmis", R.drawable.bread));
        item.add(new Item("Tryout", "TRYKKK", R.drawable.bread));
        item.add(new Item("Hoave Mercy", "LORdd", R.drawable.bread));
        item.add(new Item("deneme", "denenmis", R.drawable.bread));
        item.add(new Item("Tryout", "TRYKKK", R.drawable.bread));
        item.add(new Item("Hoave Mercy", "LORdd", R.drawable.bread));
        item.add(new Item("deneme", "denenmis", R.drawable.bread));
        item.add(new Item("Tryout", "TRYKKK", R.drawable.bread));
        item.add(new Item("Hoave Mercy", "LORdd", R.drawable.bread));
        item.add(new Item("deneme", "denenmis", R.drawable.bread));
        item.add(new Item("Tryout", "TRYKKK", R.drawable.bread));
        item.add(new Item("Hoave Mercy", "LORdd", R.drawable.bread));

        return item;
    }


}
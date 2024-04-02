package com.example.barcodescanner.customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barcodescanner.R;

import java.util.ArrayList;
import java.util.List;


public class FavouritesFragment extends Fragment {


    private List<ProductModel> itemList;
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

    private List<ProductModel> generateItems(){
        List<ProductModel> item = new ArrayList<>();
        item.add(new ProductModel("deneme", "denenmis", generateImages(), "deneme", "deneme","s","s"));
        item.add(new ProductModel("Tryout", "TRYKKK", generateImages(), "Tryout", "TRYKKK","s","s"));
        item.add(new ProductModel("Hoave Mercy", "LORdd", generateImages(), "Hoave Mercy", "LORdd","s","s"));
        item.add(new ProductModel("deneme", "denenmis", generateImages(), "deneme", "denenmis","s","s"));
        item.add(new ProductModel("Tryout", "TRYKKK", generateImages(), "Tryout", "TRYKKK","s","s"));
        item.add(new ProductModel("Hoave Mercy", "LORdd", generateImages(), "Hoave Mercy", "LORdd","s","s"));
        item.add(new ProductModel("deneme", "denenmis", generateImages(), "deneme", "denenmis","s","s"));
        item.add(new ProductModel("Tryout", "TRYKKK", generateImages(), "Tryout", "TRYKKK","s","s"));
        item.add(new ProductModel("Hoave Mercy", "LORdd", generateImages(), "Hoave Mercy", "LORdd","s","s"));
        item.add(new ProductModel("deneme", "denenmis", generateImages(), "deneme", "denenmis","s","s"));
        item.add(new ProductModel("Tryout", "TRYKKK", generateImages(), "Tryout", "TRYKKK","s","s"));
        item.add(new ProductModel("Hoave Mercy", "LORdd", generateImages(), "Hoave Mercy","LORdd","s","s"));
        item.add(new ProductModel("deneme", "denenmis", generateImages(), "deneme", "denenmis","s","s"));
        item.add(new ProductModel("Tryout", "TRYKKK", generateImages(), "Tryout", "TRYKKK","s","s"));
        item.add(new ProductModel("Hoave Mercy", "LORdd", generateImages(), "Hoave Mercy", "LORdd","s","s"));

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
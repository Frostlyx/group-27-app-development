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


public class CheapestFragment extends Fragment {

    List<StoreModel> storeList;
    RecyclerView favRecView;
    MyAdapter3 myAdapter;


    public CheapestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cheapest, container, false);
        container.clearDisappearingChildren();
        storeList = generateMarkets();

        favRecView = rootView.findViewById(R.id.recyclerviewCheap);
        favRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myAdapter = new MyAdapter3(storeList);
        favRecView.setAdapter(myAdapter);

        return rootView;
    }


    private List<StoreModel> generateMarkets(){
        List<StoreModel> item = new ArrayList<>();
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
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
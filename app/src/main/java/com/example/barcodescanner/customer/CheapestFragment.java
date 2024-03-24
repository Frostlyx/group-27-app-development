package com.example.barcodescanner.customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.barcodescanner.R;

import java.util.ArrayList;
import java.util.List;


public class CheapestFragment extends Fragment {

    List<Market> marketList;
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
        marketList = generateMarkets();

        favRecView = rootView.findViewById(R.id.recyclerviewCheap);
        favRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myAdapter = new MyAdapter3(marketList);
        favRecView.setAdapter(myAdapter);

        return rootView;
    }


    private List<Market> generateMarkets(){
        List<Market> item = new ArrayList<>();
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        return item;
    }


}
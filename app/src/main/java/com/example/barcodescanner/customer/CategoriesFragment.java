package com.example.barcodescanner.customer;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barcodescanner.R;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    List<Market> marketList;
    RecyclerView favRecView, secondView;
    MyAdapter6 myAdapter;




    public CategoriesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        container.clearDisappearingChildren();
        marketList = generateMarkets();

        favRecView = rootView.findViewById(R.id.recyclerviewcat);

        if(getScreenWidth() > 1200) {
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
            favRecView.setLayoutManager(layoutManager);
        }else {
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            favRecView.setLayoutManager(layoutManager);
        }



        myAdapter = new MyAdapter6(marketList);
        favRecView.setAdapter(myAdapter);


        return rootView;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
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
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
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
package com.example.barcodescanner.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment implements CategoryRecyclerViewInterface {

    List<Market> marketList;
    RecyclerView favRecView, secondView;
    CategoryRecyclerViewAdapter myAdapter;



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
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        favRecView.setLayoutManager(layoutManager);

        myAdapter = new CategoryRecyclerViewAdapter(marketList, this);
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
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        item.add(new Market("deneme", "denenmis", R.drawable.bread));
        return item;
    }

    @Override
    public void onItemClick(int position) {
        String[] toastMessages = requireContext().getResources().getStringArray(R.array.placeholder_main_page_product);

        if (position >= 0 && position < toastMessages.length) {
            String message = toastMessages[position];
            String toastMessage = getString(R.string.placeholder_toast_product_format, message);
            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Invalid position", Toast.LENGTH_SHORT).show();
        }
    }
}
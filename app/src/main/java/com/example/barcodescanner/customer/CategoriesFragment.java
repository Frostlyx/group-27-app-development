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

    List<StoreModel> marketList;
    RecyclerView favRecView, secondView;
    CategoryRecyclerViewAdapter myAdapter;
    private SharedViewModel sharedViewModel;



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

        sharedViewModel = ((MainActivity) getActivity()).getSharedViewModel();

        return rootView;
    }

    private List<StoreModel> generateMarkets(){
        List<StoreModel> item = new ArrayList<>();
        item.add(new StoreModel("Food", "denenmis", generateImages()));
        item.add(new StoreModel("Drink", "denenmis", generateImages()));
        item.add(new StoreModel("Meat", "denenmis", generateImages()));
        item.add(new StoreModel("Vegetables", "denenmis", generateImages()));
        item.add(new StoreModel("Bread", "denenmis", generateImages()));
        item.add(new StoreModel("Snacks", "denenmis", generateImages()));
        item.add(new StoreModel("Food", "denenmis", generateImages()));
        item.add(new StoreModel("Food", "denenmis", generateImages()));
        item.add(new StoreModel("Food", "denenmis", generateImages()));
        item.add(new StoreModel("Food", "denenmis", generateImages()));
        item.add(new StoreModel("Food", "denenmis", generateImages()));
        item.add(new StoreModel("Food", "denenmis", generateImages()));
        return item;
    }

    @Override
    public void onItemClick(int position) {
        String[] categories = requireContext().getResources().getStringArray(R.array.placeholder_categories);

        if (position >= 0 && position < categories.length) {
            String category = categories[position];
            sharedViewModel.filterBy(category);

            // Set the home item as selected in the bottom navigation view
            ((MainActivity) requireActivity()).setBottomNavigationSelectedItem(R.id.home);

            // Replace fragment
            ((MainActivity) getActivity()).replaceFragment(new MainFragment(), getResources().getString(R.string.home_title));
        } else {
            Toast.makeText(requireContext(), "Invalid position", Toast.LENGTH_SHORT).show();
        }
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
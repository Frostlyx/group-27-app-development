package com.example.barcodescanner.customer;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment implements CategoryRecyclerViewInterface {

    ArrayList<StoreModel> categoriesList;
    RecyclerView favouritesRecyclerView;
    CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;
    private SharedViewModel sharedViewModel;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        container.clearDisappearingChildren();
        categoriesList = generateMarkets();

        favouritesRecyclerView = rootView.findViewById(R.id.recyclerviewcat);
        if(getScreenWidth() > 1200) {
            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 4);
            favouritesRecyclerView.setLayoutManager(layoutManager);
        }else {
            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
            favouritesRecyclerView.setLayoutManager(layoutManager);
        }

        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(categoriesList, this);
        favouritesRecyclerView.setAdapter(categoryRecyclerViewAdapter);

        sharedViewModel = ((MainActivity) getActivity()).getSharedViewModel();

        return rootView;
    }

    private ArrayList<StoreModel> generateMarkets(){
        ArrayList<StoreModel> item = new ArrayList<>();
        item.add(new StoreModel("Vegetables", "", generateImages()));
        item.add(new StoreModel("Fruit", "", generateImages()));
        item.add(new StoreModel("Bread", "", generateImages()));
        item.add(new StoreModel("Meat", "", generateImages()));
        item.add(new StoreModel("Snacks", "", generateImages()));
        item.add(new StoreModel("Sweets", "", generateImages()));
        item.add(new StoreModel("Drinks", "", generateImages()));
        item.add(new StoreModel("Vega(n)", "", generateImages()));
        return item;
    }

    @Override
    public void onItemClick(int position) {
        String[] categories = requireContext().getResources().getStringArray(R.array.placeholder_categories);

        if (position >= 0 && position < categories.length) {
            String category = categoriesList.get(position).getStoreName();
            //String category = categories[position];
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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if the orientation has changed
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Replace the current fragment with the landscape version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.layout_default, new CategoriesFragment())
                    .commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Replace the current fragment with the portrait version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.layout_default, new CategoriesFragment())
                    .commit();
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

}
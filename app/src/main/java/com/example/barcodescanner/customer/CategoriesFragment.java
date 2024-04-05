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

/**
 * Customer side fragment for displaying category buttons to filter the home page by.
 */
public class CategoriesFragment extends Fragment implements CategoryRecyclerViewInterface {

    // List of categories
    ArrayList<StoreModel> categoriesList;
    RecyclerView categoriesRecyclerView;
    CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;

    // Shared model of products to display on the main page
    private SharedViewModel sharedViewModel;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        container.clearDisappearingChildren();
        // Make the list of categories displayed
        categoriesList = createCategories();
        // Recyclewview to display categories
        categoriesRecyclerView = rootView.findViewById(R.id.recyclerviewcat);

        // Change amount of categories per row based on device screen width
        if(getScreenWidth() > 1200) {
            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 4);
            categoriesRecyclerView.setLayoutManager(layoutManager);
        }else {
            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
            categoriesRecyclerView.setLayoutManager(layoutManager);
        }

        // Initialize adapter for categories
        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(categoriesList, this);
        categoriesRecyclerView.setAdapter(categoryRecyclerViewAdapter);

        // Get shared model of products from MainActivity
        sharedViewModel = ((MainActivity) getActivity()).getSharedViewModel();

        return rootView;
    }

    // Creates the list of categories to display.
    private ArrayList<StoreModel> createCategories(){
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

    // When a category gets clicked, filter the shared model based on the category and open the main page.
    @Override
    public void onItemClick(int position) {
        // Defines list of categories (must be in the same order as in createCategories)
        String[] categories = requireContext().getResources().getStringArray(R.array.placeholder_categories);

        if (position >= 0 && position < categories.length) {
            // Filter shared model
            String category = categoriesList.get(position).getStoreName();
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

    // Used for screen rotation, updates the fragment accordingly.
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
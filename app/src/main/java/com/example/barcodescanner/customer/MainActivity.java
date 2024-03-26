package com.example.barcodescanner.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.barcodescanner.R;
import com.example.barcodescanner.databinding.ActivityMainBinding;
import com.example.barcodescanner.ui.login.WelcomeActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ProductRecyclerViewAdapter adapter;
    static ArrayList<ProductModel> productModels = new ArrayList<>();
    // TODO: placeholder for images, to replace with actual images
    int[] productImage = {R.drawable.bread};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup product list
        setupProductModels(getApplicationContext());

        // On startup, open main fragment
        replaceFragment(new MainFragment(), getResources().getString(R.string.home_title));

        // attempt at deselecting
        binding.bottomNavigationView.setSelectedItemId(R.id.home);
//        Menu menu = binding.bottomNavigationView.getMenu();
//        for (int i = 0; i < menu.size(); i++) {
//            MenuItem menuItem = menu.getItem(i);
//            menuItem.setChecked(false);
//        }

        // Opens fragment for the selected page in the bottom navigation bar
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.favourites:
                    replaceFragment(new FavouritesFragment(), getResources().getString(R.string.favourites_title));
                    break;

                case R.id.categories:
                    replaceFragment(new CategoriesFragment(), getResources().getString(R.string.categories_title));
                    break;

                case R.id.home:
                    replaceFragment(new MainFragment(), getResources().getString(R.string.home_title));
                    break;

                case R.id.shopping_list:
                    replaceFragment(new ShoppingListFragment(), getResources().getString(R.string.shopping_list_title));
                    break;

                case R.id.profile:
                    replaceFragment(new ProfileFragment(), getResources().getString(R.string.profile_title));
                    break;
            }

            return true;
        });

        // Opens main page when home button gets pressed.
//        binding.toolbarHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Menu menu = binding.bottomNavigationView.getMenu();
////                for (int i = 0; i < menu.size(); i++) {
////                    MenuItem menuItem = menu.getItem(i);
////                    menuItem.setChecked(false);
////                }
//                replaceFragment(new MainFragment(), getResources().getString(R.string.home_title));
//            }
//        });
    }

    // Makes the list of product models to put in the recycler view
    // currently a placeholder, to be replaced with database connection
    // TODO: move this out of activity into own class, or combine with adapter
    public void setupProductModels(Context context){
        String[] productNames = context.getResources().getStringArray(R.array.placeholder_main_page_product);
        String[] productPrices = context.getResources().getStringArray(R.array.placeholder_main_page_price);
        String[] productCategories = context.getResources().getStringArray(R.array.placeholder_main_page_category);

        for (int i = 0; i < productNames.length; i++) {
            productModels.add(new ProductModel(productNames[i],
                    productPrices[i],
                    productImage[0],
                    productCategories[i],
                    "10%"));
        }
    }

    public void replaceFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

        binding.toolbarTitle.setText(title);


    }

    public void replaceActivity() {
        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }
}
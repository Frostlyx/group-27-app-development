package com.example.barcodescanner.customer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.barcodescanner.R;
import com.example.barcodescanner.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

    private void replaceFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

        binding.toolbarTitle.setText(title);


    }
}
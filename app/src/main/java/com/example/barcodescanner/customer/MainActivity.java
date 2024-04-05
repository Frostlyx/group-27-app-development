package com.example.barcodescanner.customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.barcodescanner.R;
import com.example.barcodescanner.databinding.ActivityMainBinding;
import com.example.barcodescanner.ui.login.WelcomeActivity;

/**
 * Main activity of the application on the customer side.
 */
public class MainActivity extends AppCompatActivity {

    // View binding for the activity layout
    ActivityMainBinding binding;
    // ViewModel for shared data among fragments
    private SharedViewModel sharedViewModel;
    // ViewModel for user-specific data
    private UserListViewModel userListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the activity layout using view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize view models
        sharedViewModel = new SharedViewModel(this);
        userListViewModel = new UserListViewModel(this);

        // Observe if data is fetched from Firebase
        sharedViewModel.isDataFetched().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isFetched) {
                if (isFetched) {
                    // On startup, open main fragment
                    replaceFragment(new MainFragment(), getResources().getString(R.string.home_title));

                    // Selects the home page in the bottom navigation bar
                    binding.bottomNavigationView.setSelectedItemId(R.id.home);

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
                }
            }
        });
    }

    // Method to replace the current fragment with the specified one
    public void replaceFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

        // Set toolbar title
        binding.toolbarTitle.setText(title);
    }

    // Method to set the selected item in the bottom navigation view
    public void setBottomNavigationSelectedItem(int itemId) {
        binding.bottomNavigationView.setSelectedItemId(itemId);
    }

    // Method to replace the activity with the WelcomeActivity
    public void replaceActivity() {
        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

    // Getter method for the SharedViewModel instance
    public SharedViewModel getSharedViewModel() {
        return sharedViewModel;
    }

    // Getter method for the UserListViewModel instance
    public UserListViewModel getUserListViewModel() {
        return userListViewModel;
    }
}

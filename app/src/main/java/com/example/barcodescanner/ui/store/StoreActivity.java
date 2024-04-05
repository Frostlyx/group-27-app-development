package com.example.barcodescanner.ui.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.barcodescanner.R;
import com.example.barcodescanner.databinding.ActivityStoreBinding;
import com.example.barcodescanner.ui.login.WelcomeActivity;

/**
 * Activity for the store owner's interface.
 */
public class StoreActivity extends AppCompatActivity {

    ActivityStoreBinding binding;
    private StoreProductViewModel storeProductViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this activity using ViewBinding
        binding = ActivityStoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the StoreProductViewModel
        storeProductViewModel = new StoreProductViewModel(this);

        // Replace the initial fragment with MyStoreFragment
        replaceFragment(new MyStoreFragment(), getResources().getString(R.string.mystore_title));

        // Set click listener for the home button to navigate back to MyStoreFragment
        binding.storeHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new MyStoreFragment(), getResources().getString(R.string.mystore_title));
            }
        });

    }

    /**
     * Replaces the current fragment with the given fragment.
     *
     * @param fragment The fragment to replace.
     * @param title    The title of the fragment.
     */
    public void replaceFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_storeowner_container, fragment);
        fragmentTransaction.commit();

        // Set the title of the toolbar
        binding.storeToolbarTitle.setText(title);
    }

    /**
     * Replaces the current activity with the WelcomeActivity.
     */
    public void replaceActivity() {
        Intent intent = new Intent(StoreActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

    /**
     * Gets the StoreProductViewModel associated with this activity.
     *
     * @return The StoreProductViewModel.
     */
    public StoreProductViewModel getStoreProductViewModel() {
        return storeProductViewModel;
    }

}

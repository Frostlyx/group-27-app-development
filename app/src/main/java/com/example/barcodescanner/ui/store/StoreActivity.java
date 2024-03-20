package com.example.barcodescanner.ui.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.MainActivity;
import com.example.barcodescanner.customer.ProfileFragment;
import com.example.barcodescanner.ui.login.LoginFragment;
import com.example.barcodescanner.ui.login.RegisterCustomerFragment;
import com.example.barcodescanner.ui.login.RegisterStoreOwnerFragment;
import com.example.barcodescanner.ui.login.WelcomeActivity;

public class StoreActivity extends AppCompatActivity {
    Button buttonStoreProfile;
    Button buttonViewItems;

    Button buttonStorePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mystore);
        buttonStoreProfile = findViewById(R.id.button_profile);
        buttonViewItems = findViewById(R.id.button_item_view);
        buttonStorePage = findViewById(R.id.button_store_page_view);

        buttonStoreProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new ProfileFragment());
            }
        });

        buttonViewItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new StoreDatabaseFragment());
            }
        });

        buttonStorePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new EditStoreFragment());
            }
        });



    }

    public void replaceFragment(Fragment fragment) {
        setContentView(R.layout.activity_store);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_store_container, fragment);
        fragmentTransaction.commit();
    }

    public void replaceActivity() {
        Intent intent = new Intent(StoreActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

}

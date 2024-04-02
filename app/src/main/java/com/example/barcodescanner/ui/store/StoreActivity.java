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

public class StoreActivity extends AppCompatActivity {

    ActivityStoreBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new MyStoreFragment(), getResources().getString(R.string.mystore_title));

        binding.storeHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new MyStoreFragment(), getResources().getString(R.string.mystore_title));
            }
        });

    }

    public void replaceFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_storeowner_container, fragment);
        fragmentTransaction.commit();

        binding.storeToolbarTitle.setText(title);
    }

    public void replaceActivity() {
        Intent intent = new Intent(StoreActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

}

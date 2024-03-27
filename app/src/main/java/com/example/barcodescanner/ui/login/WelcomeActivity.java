package com.example.barcodescanner.ui.login;

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
import com.example.barcodescanner.ui.store.StoreActivity;

public class WelcomeActivity extends AppCompatActivity {
    Button buttonRegisterStore;
    Button buttonLogin;

    Button buttonRegisterCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        buttonRegisterStore = findViewById(R.id.registerStoreAccount);
        buttonLogin = findViewById(R.id.loginNow);
        buttonRegisterCustomer = findViewById(R.id.registerCustomerAccount);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new LoginFragment());
            }
        });

        buttonRegisterStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new RegisterStoreOwnerFragment());
            }
        });

        buttonRegisterCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new RegisterCustomerFragment());
            }
        });



    }

    public void replaceFragment(Fragment fragment) {
        setContentView(R.layout.activity_login);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_login_container, fragment);
        fragmentTransaction.commit();
    }

    public void customerActivity() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void storeActivity() {
        Intent intent = new Intent(WelcomeActivity.this, StoreActivity.class);
        startActivity(intent);
    }

    public void welcomeActivity() {
        Intent intent = new Intent(WelcomeActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }
}
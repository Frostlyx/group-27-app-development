package com.example.barcodescanner.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.MainActivity;
import com.example.barcodescanner.ui.store.StoreActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {
    Button buttonRegisterStore;
    Button buttonLogin;

    Button buttonRegisterCustomer;

    FirebaseAuth mAuth;

    //this is what keeps you logged in the app.
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth = FirebaseAuth.getInstance();
        buttonRegisterStore = findViewById(R.id.registerStoreAccount);
        buttonLogin = findViewById(R.id.loginNow);
        buttonRegisterCustomer = findViewById(R.id.registerCustomerAccount);
        buttonLogin.setOnClickListener(view -> replaceFragment(new LoginFragment()));

        buttonRegisterStore.setOnClickListener(view -> replaceFragment(new RegisterStoreOwnerFragment()));

        buttonRegisterCustomer.setOnClickListener(v -> replaceFragment(new RegisterCustomerFragment()));



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
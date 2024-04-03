package com.example.barcodescanner.ui.login;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.MainActivity;
import com.example.barcodescanner.ui.store.StoreActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {
    Button buttonRegisterStore;
    Button buttonLogin;

    Button buttonRegisterCustomer;

    FirebaseAuth mAuth;
    boolean isCustomer;
    boolean isStoreOwner;
    FirebaseUser user;

    //this is what keeps you logged in the app.
    @Override
    public void onStart() {
        super.onStart();
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference referenceCustomers= referenceProfile.child("Customers");
        // Sign in success, update UI with the signed-in user's information
        if (currentUser != null) {
            referenceCustomers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(currentUser.getUid())) {
                        customerActivity();
                    } else {
                        storeActivity();

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if the activity is WelcomeActivity
        if (getSupportFragmentManager().getFragments().isEmpty()) {
            // Check if the orientation has changed to landscape
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Perform the necessary action (e.g., starting a new activity)
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
                finish();
            }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                // Perform the necessary action (e.g., starting a new activity)
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }
}
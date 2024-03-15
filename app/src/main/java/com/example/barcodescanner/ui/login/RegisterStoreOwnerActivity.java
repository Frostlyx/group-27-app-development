package com.example.barcodescanner.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.barcodescanner.customer.MainActivity;
import com.example.barcodescanner.databinding.ActivityRegisterStoreOwnerBinding;

public class RegisterStoreOwnerActivity extends AppCompatActivity {

    private ActivityRegisterStoreOwnerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityRegisterStoreOwnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Button backButton = binding.back;
        final Button registerButton = binding.register;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterStoreOwnerActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterStoreOwnerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
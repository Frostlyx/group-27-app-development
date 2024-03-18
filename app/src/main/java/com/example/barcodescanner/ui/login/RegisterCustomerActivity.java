package com.example.barcodescanner.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.barcodescanner.customer.MainActivity;
import com.example.barcodescanner.databinding.ActivityRegisterCustomerBinding;

public class RegisterCustomerActivity extends AppCompatActivity {

    private ActivityRegisterCustomerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityRegisterCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Button backButton = binding.back;
        final Button registerButton = binding.register;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterCustomerActivity.this, LoginOldActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterCustomerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
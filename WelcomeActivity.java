package com.example.wifiscanning;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Retrieve the WiFi name from the intent
        String wifiName = getIntent().getStringExtra("DataMann");

        // Display the welcome message
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewWelcome.setText("Welcome to Wi-Fi " + wifiName + "!");


        // You can perform additional operations or customize the activity as needed
    }
}
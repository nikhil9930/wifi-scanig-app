package com.example.wifiscanning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Delayed execution of the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);

                // Close the flash screen activity
                finish();
            }
        }, SPLASH_DURATION);
    }
}


package com.example.wifiscanning;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button button = findViewById(R.id.Internet);
        button.setOnClickListener(v -> {
            String url = "https://www.datamann.in";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            Toast.makeText(this, "Welcome To DataMann Website", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });


        button = findViewById(R.id.WIFI);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                        ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    int PERMISSION_REQUEST_CODE = 0;
                    ActivityCompat.requestPermissions(HomeActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_REQUEST_CODE);
                } else {
                    if (isWifiEnabled()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            connectToSavedWifi();
                        }
                    }else{
                        Toast.makeText(HomeActivity.this,"Wi-Fi is not enable",Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.Q)
            private void connectToSavedWifi() {

                String wifiName = "DataMann";
                String wifiPassword = "Datamann@2022";
                WifiNetworkSpecifier specifier = new WifiNetworkSpecifier.Builder()
                        .setSsid(wifiName)
                        .setWpa2Passphrase(wifiPassword)
                        .build();

                NetworkRequest request = new NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                        .setNetworkSpecifier(specifier)
                        .build();

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onAvailable(@NonNull Network network) {

//                            Toast.makeText(HomeActivity.this, "Connected to WiFi network", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                        intent.putExtra("DataMann", wifiName);
                        Toast.makeText(HomeActivity.this, "Welcome you are now Connected to the wifi", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    }
                    @Override
                    public void onUnavailable() {

                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        if (wifiManager != null) {
                            wifiManager.setWifiEnabled(true);
                            WifiConfiguration wifiConfig = new WifiConfiguration();
                            wifiConfig.SSID = "\"" + wifiName + "\"";
                            wifiConfig.preSharedKey = "\"" + wifiPassword + "\"";
                            int networkId = wifiManager.addNetwork(wifiConfig);
                            wifiManager.disconnect();
                            wifiManager.enableNetwork(networkId, true);
                            wifiManager.reconnect();
                            finish();


                           }
                    }
                };
                connectivityManager.requestNetwork(request, networkCallback);
            }
        });
        button = findViewById(R.id.Bluetooth);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
            startActivity(intent);
            Toast.makeText(this, "Now you Can Enable Bluetooth", Toast.LENGTH_SHORT).show();
        });
}

    private boolean isWifiEnabled() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager != null && wifiManager.isWifiEnabled();

    }
}

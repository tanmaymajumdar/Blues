package com.tanmay.nf.bluesapp;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tanmay.nf.blues.BluesOperation.Blues;

import Utils.Interfaces.BluesLocationListener;
import Utils.BluesLocationProviders;

public class MainActivity extends AppCompatActivity implements BluesLocationListener {

    Blues blues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blues = new Blues.Builder(this , 1000 , this)
                            .setRequestId(1000)
                            .setLocationProvider(BluesLocationProviders.GPS)
                            .build();

        blues.checkForPermission();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        blues.onRequestPermissionResult(requestCode , permissions , grantResults);
        blues.startTracking();
    }

    @Override
    public void onLocationCatched(Location location) {
        Log.i("locationtracking" , location.getLatitude() + "");
    }

    @Override
    public void onPermissionDenied() {
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled() {
        Toast.makeText(this, "GPS disabled", Toast.LENGTH_SHORT).show();
    }
}

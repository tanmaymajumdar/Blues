package Utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import Utils.Interfaces.BluesLocationListener;

public class BluesPermissionHandler {

    private Activity activity;
    private BluesLocationListener bluesLocationListener;

    private BluesPermissionHandler() {
    }

    public static int BLUES_PERMISSION_TAG = 100;

    public BluesPermissionHandler(Activity context , BluesLocationListener bluesLocationListener) {
        this.activity = context;
        this.bluesLocationListener = bluesLocationListener;
    }

    public void checkForPermissions() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askForPermission();
        }
    }

    private void askForPermission() {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                BLUES_PERMISSION_TAG
        );
    }

    public void onRequestPermissionResult(int requestCode,
                                          String permissions[], int[] grantResults){

        if(requestCode == BLUES_PERMISSION_TAG) {
            if(permissions ==  new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}) {
                for(int result : grantResults)
                    if(result == PackageManager.PERMISSION_DENIED) {
                        bluesLocationListener.onPermissionDenied();
                        break;
                    }
            }
        }
    }

}

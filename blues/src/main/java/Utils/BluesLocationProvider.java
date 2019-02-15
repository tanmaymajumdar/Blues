package Utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.tanmay.nf.blues.BluesOperation.Blues;

import java.util.List;

import Utils.Exceptions.BluesException;
import Utils.Exceptions.ExceptionEnum;

import static android.content.Context.LOCATION_SERVICE;

public class BluesLocationProvider {

    private Blues blues;
    private LocationListener locationListener;
    public BluesLocationProvider(Blues blues) {
        this.blues = blues;
    }

    private LocationManager getLocationManager() throws BluesException {
        LocationManager locationManager = (LocationManager) blues.getActivity().getSystemService(LOCATION_SERVICE);

        if (locationManager == null)
            throw new BluesException(ExceptionEnum.NULL_MANAGER);

        return locationManager;
    }

    private LocationProvider getLocationProvider(BluesLocationProviders locationProviderEnum, LocationManager locationManager) throws BluesException {


        List<String> locationProvider = locationManager.getProviders(true);
        switch (locationProviderEnum) {
            case GPS:
                if (!locationProvider.contains("gps"))
                    throw new BluesException(ExceptionEnum.GPS_DIABLED);
                return locationManager.getProvider("gps");

            case NETWORK:
                if (!locationProvider.contains("network"))
                    throw new BluesException(ExceptionEnum.NETWORK_DISABLED);
                return locationManager.getProvider("network");

            case PASSIVE:
                if (!locationProvider.contains("passive"))
                    throw new BluesException(ExceptionEnum.PASSIVE_DISABLED);
                return locationManager.getProvider("passive");

            default:
                throw new RuntimeException("No such provider available");
        }
    }

    public void startLocationTracking() throws BluesException {

        LocationManager locationManager = getLocationManager();
        LocationProvider locationProvider = getLocationProvider(blues.getLocationProvider(), locationManager);

        locationListener =  new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                blues.getBluesLocationListener().onLocationCatched(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                blues.getBluesLocationListener().onProviderDisabled();
            }
        };

        if (ActivityCompat.checkSelfPermission(blues.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(blues.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(locationProvider.getName(), blues.getTimeInterval(), 0,locationListener);
    }

    public void stopTracking() throws BluesException {
        if(getLocationManager() != null ) {

            if(locationListener == null )
                throw new RuntimeException("You need to call startTracking to stop the tracking");

            getLocationManager().removeUpdates(locationListener);
        }
    }


}

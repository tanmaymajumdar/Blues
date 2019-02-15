package com.tanmay.nf.blues.BluesOperation;

import android.app.Activity;
import android.app.Service;

import Utils.BluesLocationProvider;
import Utils.BluesPermissionHandler;
import Utils.Exceptions.BluesException;
import Utils.Interfaces.BluesLocationListener;
import Utils.BluesLocationProviders;

public class Blues {

    private Blues() {
    }

    private Activity activity;
    private long timeInterval;
    private short requestID;
    private Service mainService;
    private BluesLocationListener bluesLocationListener;
    private BluesLocationProviders locationProvider;
    private BluesPermissionHandler bluesPermissionHandler;
    private BluesLocationProvider bluesLocationProvider;


    public Blues(Builder builder) {
        activity = builder.activity;

        if (builder.timeInterval <= 0)
            throw new RuntimeException("You must provide a polling interval");

        timeInterval = builder.timeInterval;
        requestID = builder.requestID;
        mainService = builder.mainService;
        locationProvider = builder.bluesLocationProviders;
        bluesLocationListener = builder.bluesLocationListener;
    }

    public void checkForPermission(){
         bluesPermissionHandler = new BluesPermissionHandler(activity , bluesLocationListener);
         bluesPermissionHandler.checkForPermissions();
    }

    public void onRequestPermissionResult(int requestCode , String[] permissions , int[] grantResults) {

        if(bluesPermissionHandler == null )
            throw new RuntimeException("You must call checkForPermission before calling this method.");

        bluesPermissionHandler.onRequestPermissionResult(requestCode , permissions , grantResults);
    }


    public Activity getActivity(){
        return activity;
    }

    public BluesLocationProviders getLocationProvider() {
        return locationProvider;
    }

    public long getTimeInterval() {
        return timeInterval;
    }

    public BluesLocationListener getBluesLocationListener(){
        return bluesLocationListener;
    }

    public void startTracking(){
        BluesLocationProvider bluesLocationProvider = new BluesLocationProvider(this);
        try {
            bluesLocationProvider.startLocationTracking();
        } catch (BluesException e) {
            e.printStackTrace();
        }
    }

    public void stopTracking(){
        try {
            bluesLocationProvider.stopTracking();
        } catch (BluesException e) {
            e.printStackTrace();
        }
    }


    public static class Builder {

        private Activity activity;
        private long timeInterval = -1;
        private short requestID;
        private Service mainService;
        private BluesLocationProviders bluesLocationProviders;

        private BluesLocationListener bluesLocationListener;

        private Builder() {
        }

        public Builder(Activity activity, long timeInterval , BluesLocationListener bluesLocationListener) {
            this.activity = activity;
            this.timeInterval = timeInterval;
            this.bluesLocationListener = bluesLocationListener;
        }

        public Builder setRequestId(int requestId) {
            this.requestID = requestID;
            return this;
        }

        public Builder setMainService(Service service) {
            this.mainService = service;
            return this;
        }

        public Builder setLocationProvider(BluesLocationProviders locationProvider) {
            this.bluesLocationProviders = locationProvider;
            return this;
        }


        public Blues build(){
            return new Blues(this);
        }

    }


}

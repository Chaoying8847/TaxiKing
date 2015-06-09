package com.taxiking.customer.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.taxiking.customer.utils.AppPreferences;

public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    private static AppPreferences prefs = null;
    
    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location

    // Declaring a Location Manager
    protected LocationManager locationManager;
    
    public GPSTracker(Context context) {
        this.mContext = context;
        prefs = new AppPreferences(context);
        
        locationManager = (LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);
        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGPSEnabled || isNetworkEnabled) {
            this.canGetLocation = true;
        }
        
    }

    public void startTracking() {
        try {
            locationManager.requestSingleUpdate(
                    LocationManager.NETWORK_PROVIDER, this, null);
            Log.d("Network", "Network");
            
            locationManager.requestSingleUpdate(
                    LocationManager.GPS_PROVIDER, this, null);
            Log.d("GPS Enabled", "GPS Enabled");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Location getLocation() {
    	return this.location;
    }
   
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }       
    }
   
    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
   
    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });

        // Showing Alert Message
//        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        prefs.setLocation(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}

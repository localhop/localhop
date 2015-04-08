package com.localhop.network;


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

public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    private boolean mIsGPSEnabled = false;
    private boolean mIsNetworkEnabled = false;
    private boolean mCanGetLocation = false;

    private LocationManager mLocationManager;
    private Location mLocation;

    private double mLatitude;
    private double mLongitude;

    // The minimum distance to change Updates in meters
    private static final long MIN_UPDATE_DISTANCE = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_UPDATE_TIME = 1000 * 60 * 10; // 1 minute

    /**
     * Constructor
     * @param context
     */
    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    } // end of constructor

    /**
     * Retrieve the user's current location
     * @return
     */
    public Location getLocation() {
        try {
            mLocationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            mIsGPSEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            mIsNetworkEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!mIsGPSEnabled && !mIsNetworkEnabled) {
                // no network provider is enabled
            }
            else
            {
                this.mCanGetLocation = true;
                // First get Location from Network Provider
                if (mIsNetworkEnabled) {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_UPDATE_TIME,
                            MIN_UPDATE_DISTANCE, this);
                    Log.d("Network", "Network");
                    if (mLocationManager != null) {
                        mLocation = mLocationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (mLocation != null) {
                            mLatitude = mLocation.getLatitude();
                            mLongitude = mLocation.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (mIsGPSEnabled) {
                    if (mLocation == null) {
                        mLocationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_UPDATE_TIME,
                                MIN_UPDATE_DISTANCE, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (mLocationManager != null) {
                            mLocation = mLocationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (mLocation != null) {
                                mLatitude = mLocation.getLatitude();
                                mLongitude = mLocation.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mLocation;
    } // end of function getLocation()

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(mLocationManager != null){
            mLocationManager.removeUpdates(GPSTracker.this);
        }
    } // end of function stopUsingGPS()

    /**
     * Function to get Latitude
     * */
    public double getLatitude(){
        if(mLocation != null){
            mLatitude = mLocation.getLatitude();
        }
        return mLatitude;
    } // end of function getLatitude()

    /**
     * Function to get Longitude
     * */
    public double getLongitude(){
        if(mLocation != null){
            mLongitude = mLocation.getLongitude();
        }

        return mLongitude;
    } // end of function getLongitude()

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.mCanGetLocation;
    } // end of function canGetLocation()

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will launch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("No GPS");

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
        alertDialog.show();
    } // end of function showSettingsAlert()

    @Override
    public void onLocationChanged(Location location) {
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

} // end of class GPSTracker

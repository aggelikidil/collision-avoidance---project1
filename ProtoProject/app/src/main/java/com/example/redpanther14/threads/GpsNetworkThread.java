package com.example.redpanther14.threads;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.redpanther14.data.DataSettings;
import com.example.redpanther14.data.SensorsData;



public class GpsNetworkThread extends Thread implements LocationListener {
    private Context mContext;
    private CheckBox checkbox;
    private Activity mainactivity;

    /**
     *  *parakolou8hsh ths syndesimothtas tou gps kai tou internet
     * @param mainactivity  to mainactivity ths efarmoghs
     *
     * @param locationManager epitrepei thn prosbash sthn topo8esia
     *
     * @param mContext to antistoixo context ths efarmoghs
     *
     * @param checkbox to checkbox online(/offline) pou brisketai sto mainactivity
     */
    public GpsNetworkThread(Activity mainactivity, LocationManager locationManager, Context mContext, CheckBox checkbox) {
        this.mContext = mContext;
        this.checkbox = checkbox;
        this.mainactivity = mainactivity;


        // getting GPS status
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            // disable checkbox
            Log.d("GPS thread", "Gps and network not found");
        } else {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("GPS thread", "No permissions for ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION");
                return;
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }

        // if GPS Enabled get latitude/longtitude using gps
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("GPS thread", "No permissions for ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION");
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }

        }

        Log.d("GPS thread", "Gps network thread stopped");
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);

                final boolean isNetworkEnabled = isNetworkConnected();

                //aithsh sto mainactivity na energopoihsei to checkbox(online/offline)
                mainactivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isNetworkEnabled == false || DataSettings.automatic) {
                            checkbox.setEnabled(false);
                        }

                        if (isNetworkEnabled && ! DataSettings.automatic) {
                            checkbox.setEnabled(true);
                        }

                        if (DataSettings.automatic) {
                            if (isNetworkEnabled == false) {
                                checkbox.setChecked(false);
                            } else {
                                checkbox.setChecked(true);
                            }
                        }
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "location received" + location.getLatitude() + " " + location.getLongitude());

        final boolean isNetworkEnabled = isNetworkConnected();

        SensorsData.location = location;
        //aithsh sto mainactivity na energopoihsei to checkbox(online/offline) otan kai to gps einai energopoihmeno
        mainactivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isNetworkEnabled == true) {
                    checkbox.setEnabled(true);
                } else {
                    checkbox.setEnabled(false);
                    checkbox.setChecked(false);

                }
            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("onStatusChanged", "gps status changed!");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("GspNetworkThread", "gps started!");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("GspNetworkThread", "gps stopped!");

        mainactivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                checkbox.setEnabled(false);
                checkbox.setChecked(false);
            }
        });
    }
}


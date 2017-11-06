package com.example.redpanther14.threads;

import android.app.Activity;
import android.location.LocationManager;

import com.example.redpanther14.data.DataSettings;
import com.example.redpanther14.data.SensorsData;
import com.example.redpanther14.datahandler.DataHandler;
import com.example.redpanther14.savelisteners.ListenersStorage;

import java.text.SimpleDateFormat;
import java.util.Date;


public class PublishThread extends Thread {
    private LocationManager locationManager;
    private Activity activity;

    public PublishThread(Activity activity, LocationManager locationManager) {
        this.locationManager = locationManager;
        this.activity = activity;
    }

    public void run() {

        while (true) {
            try {
                Thread.sleep(100);

                DataHandler handler = new DataHandler();

                DataSettings settings = handler.loadFromDisk(activity);
                MosquitoControllerTerminal controller = new MosquitoControllerTerminal(settings.id, settings.mosquito_ip, String.valueOf(settings.mosquito_port));


                boolean usemqqt = false;

                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                boolean isAutomatic = DataSettings.automatic;
                boolean userWantsToUseInternet = DataSettings.online;

                //online
                if ((isAutomatic && isNetworkEnabled) || (userWantsToUseInternet && isNetworkEnabled)) {
                    usemqqt = true;
                }

                if (usemqqt) {
                    if ( ListenersStorage.l1 != null && ListenersStorage.l2 != null) {
                        if (SensorsData.location != null) {
                            //current date and time
                            Date date = new Date();
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

                            String sdate = format1.format(date);

                            //διαμορφωση μηνυματος
                            String msg = settings.id + ",proximity,1," +
                                    SensorsData.proximity + ",cm,accelerometer,3," +
                                    SensorsData.x + "," +
                                    SensorsData.y + "," +
                                    SensorsData.z + ",m/s^2," + SensorsData.location.getLatitude() + "," + SensorsData.location.getLongitude() +"," + sdate;
                            //publish to administrator
                            controller.publishToAdministrator(msg);
                        } else {
                            // monitoring is not active
                        }
                    }
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

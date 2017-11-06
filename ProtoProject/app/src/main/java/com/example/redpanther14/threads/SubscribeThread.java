package com.example.redpanther14.threads;

import android.app.Activity;
import android.location.LocationManager;

import com.example.redpanther14.data.DataSettings;
import com.example.redpanther14.datahandler.DataHandler;



public class SubscribeThread extends Thread {
    private Activity activity;

    public SubscribeThread(Activity activity, LocationManager locationManager) {
        this.activity = activity;
    }

    //admin thread
    public void run() {
        DataHandler handler = new DataHandler();  //prosvasi sthn mnhmh
        DataSettings settings = handler.loadFromDisk(activity);

        while (true) {
            try {
                MosquitoControllerTerminal controller = new MosquitoControllerTerminal(settings.id, settings.mosquito_ip, String.valueOf(settings.mosquito_port));
                controller.subscribe();

                Thread.sleep(5000);

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}



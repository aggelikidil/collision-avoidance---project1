package com.example.redpanther14.onclicklisteners;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.hardware.Sensor;
import android.hardware.SensorManager;
//import android.util.Log;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.redpanther14.data.DataSettings;
import com.example.redpanther14.datahandler.DataHandler;
import com.example.redpanther14.onsensoreventlisteners.OnAccelerometerSensorEventListener;
import com.example.redpanther14.onsensoreventlisteners.OnProximitySensorEventListener;
import com.example.redpanther14.savelisteners.ListenersStorage;

public class OnClickListenerStartMonitoring implements Button.OnClickListener {
    /**
     *             διαχείριση του πατηματος του START
     *
     * @param   v     το view του application
     */
    @Override
    public void onClick(View v) {
        ContextWrapper wrapper =(ContextWrapper ) v.getContext();
        Activity activity = (Activity) wrapper.getBaseContext();

        //διαβσσμα τιμών απο το δισκο
        DataHandler handler = new DataHandler();
        DataSettings settings = handler.loadFromDisk(activity);

        //διαχειριση και επιλογή αισθητήρων
        SensorManager mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        Sensor mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        LocationManager mLocationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        // ελεγχος οτι υπαρχουν οι αισθητηρες
        if (mProximity != null && mAccelerometer != null) {
            OnProximitySensorEventListener l1 = new OnProximitySensorEventListener(activity);
            OnAccelerometerSensorEventListener l2 = new OnAccelerometerSensorEventListener(activity, mLocationManager, settings);

            // αποθήκευση των listeners
            ListenersStorage.l1 = l1;
            ListenersStorage.l2 = l2;

            //μετατροπή περιοδου (microsec) σε συχνοτητα (Hz)
            int period = 1000000/settings.frequency;

            //έναρξη παρακολούθησης
            mSensorManager.registerListener(l1, mProximity, period);
            mSensorManager.registerListener(l2, mAccelerometer, period);

            //Log.i("Sensor", "Sensor f= " + settings.frequency + " period=" + period);
        } else {
            Log.i("Sensor", "Sensors not found");
        }
    }
}

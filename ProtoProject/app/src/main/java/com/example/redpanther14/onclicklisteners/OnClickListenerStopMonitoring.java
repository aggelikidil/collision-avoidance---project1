package com.example.redpanther14.onclicklisteners;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;

import com.example.redpanther14.onsensoreventlisteners.OnAccelerometerSensorEventListener;
import com.example.redpanther14.onsensoreventlisteners.OnProximitySensorEventListener;
import com.example.redpanther14.savelisteners.ListenersStorage;

public class OnClickListenerStopMonitoring implements Button.OnClickListener {
    /**
     *             διαχείριση του πατηματος του STOP
     *
     * @param   v     το view του application
     */
    @Override
    public void onClick(View v) {
        ContextWrapper wrapper =(ContextWrapper ) v.getContext();
        Activity activity = (Activity) wrapper.getBaseContext();

        //διαχειριση αισθητηρων
        SensorManager mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);

        // αποσύνδεση των listeners
        OnProximitySensorEventListener l1 = ListenersStorage.l1;
        OnAccelerometerSensorEventListener l2 = ListenersStorage.l2;

        // σταμάτημα παρακολούθησης των αισθητήρων
        mSensorManager.unregisterListener(l1);
        mSensorManager.unregisterListener(l2);

        ListenersStorage.l1 = null;
        ListenersStorage.l2 = null;


    }
}

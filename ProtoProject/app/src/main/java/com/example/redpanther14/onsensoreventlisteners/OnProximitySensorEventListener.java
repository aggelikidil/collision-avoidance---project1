package com.example.redpanther14.onsensoreventlisteners;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import com.example.redpanther14.data.SensorsData;
import com.example.redpanther14.protoproject.R;


public class OnProximitySensorEventListener implements SensorEventListener {
    private Activity activity;

    public OnProximitySensorEventListener(Activity activity) {
        this.activity = activity;
    }

    /**
     *     η συναρτηση καλειται καθε φορα που ανιχνευεται αλλαγη στις τιμες του αισθητηρα
     *
     * @param   event     αλλαγη της τιμης του αισθητηρα
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        float [] values = event.values;

        TextView textViewProximitySensorValue =  (TextView ) activity.findViewById(R.id.textViewProximitySensorValue);
        textViewProximitySensorValue.setText(String.valueOf(event.values[0]));

        //αποθηκευση της νεας τιμης στην αντιστοιχης μεταβλητης της κλασης SensorData
        SensorsData.proximity = values[0];
    }

    //δεν υλοποιειται
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

package com.example.redpanther14.onsensoreventlisteners;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.redpanther14.data.DataSettings;
import com.example.redpanther14.data.SensorsData;
import com.example.redpanther14.protoproject.R;

public class OnAccelerometerSensorEventListener implements SensorEventListener {
    private Activity activity;
    private Toast toast;
    private DataSettings dataSettings;
    private MediaPlayer mediaPlayer;
    private LocationManager locationManager;

    public OnAccelerometerSensorEventListener(Activity activity, LocationManager locationManager, DataSettings dataSettings) {
        this.activity = activity;
        this.dataSettings=dataSettings;
        this.locationManager = locationManager;
    }

    /**
     *              εγκατασταση μουσικης και απαραιτητων ρυθμισεων
     *
     */
    private void setupMusic() {
        mediaPlayer = MediaPlayer.create(activity, R.raw.warning);
        mediaPlayer.setLooping(true);
    }

    /**
     *              εναρξη μουσικης
     *
     */
    private void startMusic() {
        //αν η μουσικη δεν εχει ξεκινησει
        if(mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    /**
     *              τερματισμος μουσικης
     *
     */
    private void stopMusic() {
        //αν η μουσικη εχει ξεκινησει
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    /**
     *              η συναρτηση καλειται καθε φορα που ανιχνευεται αλλαγη στις τιμες του αισθητηρα
     *
     * @param   event     αλλαγη της τιμης του αισθητηρα
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView textViewAccelerometerSensorValue =  (TextView ) activity.findViewById(R.id.textViewAccelerometerSensorValue);
        textViewAccelerometerSensorValue.setText(String.valueOf(event.values[0]));

        TextView textViewAccelerometerSensorValue1 =  (TextView ) activity.findViewById(R.id.textViewAccelerometerSensorValue1);
        textViewAccelerometerSensorValue1.setText(String.valueOf(event.values[1]));

        TextView textViewAccelerometerSensorValue2 =  (TextView ) activity.findViewById(R.id.textViewAccelerometerSensorValue2);
        textViewAccelerometerSensorValue2.setText(String.valueOf(event.values[2]));

        //αποθηκευση των νεων τιμων στις αντιστοιχες μεταβλητες της κλασης SensorData
        SensorsData.x = event.values[0];
        SensorsData.y = event.values[1];
        SensorsData.z = event.values[2];

        // decide whether to use MQTT
        boolean usemqqt = false;

        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isAutomatic = DataSettings.automatic;
        boolean userWantsToUseInternet = DataSettings.online;

        if ((isAutomatic && isNetworkEnabled) || (userWantsToUseInternet && isNetworkEnabled)) {
            usemqqt = true;
        }

        if (!usemqqt) {
            //συνθηκη ενεργοποιησης της ειδοποιησης
            if (SensorsData.proximity <= dataSettings.firstsensorvalue && SensorsData.z > dataSettings.secondsensorvalue_z) {
                boolean problem1 = SensorsData.proximity <= dataSettings.firstsensorvalue;
                boolean problem2 = SensorsData.z > dataSettings.secondsensorvalue_z;

                if (toast == null) {
                    if (problem1 && !problem2) {
                        toast = Toast.makeText(activity, "BE CAREFUL!!! PROXIMITY", Toast.LENGTH_SHORT);
                    } else if (!problem1 && problem2) {
                        toast = Toast.makeText(activity, "BE CAREFUL!!! ACCELEROMETER ", Toast.LENGTH_SHORT);
                    } else if (!problem1 && !problem2) {
                        toast = Toast.makeText(activity, "BE CAREFUL!!! SHOULD NEVER HAPPEN ", Toast.LENGTH_SHORT);
                    } else if (problem1 && problem2) {
                        toast = Toast.makeText(activity, "BE CAREFUL!!! BOTH ", Toast.LENGTH_SHORT);
                    }
                    toast.show();
                    // εναρξη μουσικης
                    setupMusic();
                    startMusic();
                } else {
                    //ανανέωση του χρονου για το toast
                    toast.show();
                    // διατηρηση μουσικης
                }
            } else {
                if (toast != null) {
                    toast.cancel();
                    toast = null;
                }
                // τερματισμος μουσικης
                stopMusic();
            }
        } else {

        }
    }

    //δεν υλοποιείται
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



}

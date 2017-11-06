package com.example.redpanther14.onclicklisteners;

import android.app.Activity;
import android.content.ContextWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;

import com.example.redpanther14.data.DataSettings;
import com.example.redpanther14.datahandler.DataHandler;
import com.example.redpanther14.protoproject.R;

public class OnClickListenerSaveData implements Button.OnClickListener {

    /**
     *             διαχείριση του πατηματος του SAVE
     *
     * @param   v     το view του application
     */

    @Override
    public void onClick(View v) {
        ContextWrapper wrapper =(ContextWrapper ) v.getContext();
        Activity activity = (Activity) wrapper.getBaseContext();

        //καθε seek bar συνδεεται με κάποια αντίστοιχη μεταβλητή
        SeekBar seekBarSamplingRate = (SeekBar) activity.findViewById(R.id.seekBarSamplingRate);
        SeekBar seekBarFirstSensor = (SeekBar) activity.findViewById(R.id.seekBarFirstSensor);
        SeekBar seekBarSecondSensorX = (SeekBar) activity.findViewById(R.id.seekBarSecondSensorX);
        SeekBar seekBarSecondSensorY = (SeekBar) activity.findViewById(R.id.seekBarSecondSensorY);
        SeekBar seekBarSecondSensorZ = (SeekBar) activity.findViewById(R.id.seekBarSecondSensorZ);
        EditText ip = (EditText) activity.findViewById(R.id.plainTextMosquitoIP);
        EditText port = (EditText) activity.findViewById(R.id.plainTextMosquitoPort);
        Switch autoswitch =(Switch) activity.findViewById(R.id.switchAutomaticMode);

        // αλλαγη του καθε seek bar
        DataSettings settings = new DataSettings();
        settings.frequency = seekBarSamplingRate.getProgress();
        settings.firstsensorvalue =  seekBarFirstSensor.getProgress();
        settings.secondsensorvalue_x =  seekBarSecondSensorX.getProgress();
        settings.secondsensorvalue_y =  seekBarSecondSensorY.getProgress();
        settings.secondsensorvalue_z =  seekBarSecondSensorZ.getProgress();
        settings.mosquito_ip = ip.getText().toString();
        settings.mosquito_port = Integer.parseInt(port.getText().toString());

        if (autoswitch.isChecked()) {
            DataSettings.automatic = true;
        } else {
            DataSettings.automatic = false;
        }

        //αποθηκευση στον δισκο
        DataHandler handler = new DataHandler();
        handler.saveToDisk(settings, activity);
    }
}

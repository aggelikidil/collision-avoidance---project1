package com.example.redpanther14.protoproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.redpanther14.data.DataSettings;
import com.example.redpanther14.datahandler.DataHandler;
import com.example.redpanther14.onclicklisteners.OnClickListenerSaveData;
import com.example.redpanther14.onprogresslisteners.OnFirstSensorProgressListener;
import com.example.redpanther14.onprogresslisteners.OnSamplingRateProgressListener;
import com.example.redpanther14.onprogresslisteners.OnSecondSensorProgressListenerX;
import com.example.redpanther14.onprogresslisteners.OnSecondSensorProgressListenerY;
import com.example.redpanther14.onprogresslisteners.OnSecondSensorProgressListenerZ;

public class SettingsActivity extends AppCompatActivity {
    DataHandler handler = new DataHandler();

    Button buttonSaveData;

    SeekBar seekBarSamplingRate;
    SeekBar seekBarFirstSensor;
    SeekBar seekBarSecondSensorX;
    SeekBar seekBarSecondSensorY;
    SeekBar seekBarSecondSensorZ;

    TextView textViewSamplingRateValue;
    TextView textViewFirstSensorValue;
    TextView textViewSecondSensorValueX;
    TextView textViewSecondSensorValueY;
    TextView textViewSecondSensorValueZ;

    EditText plainTextMosquitoIP;
    EditText plainTextMosquitoPort;

    Switch autoswitch;

    /**
     *     δημιουργια του Settings activity
     *
     * @param   savedInstanceState     αποθηκευση της καταστασης του activity για περιπτωση επαναφορτωσης
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //εμφανιση του logo
        if (getSupportActionBar() != null) //περιπτωση που δεν υποστηριζεται απο τη συσκευη
        {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }

        //δημιουργια των listeners
        OnClickListenerSaveData l1 = new OnClickListenerSaveData();
        OnSamplingRateProgressListener l2 = new OnSamplingRateProgressListener();
        OnFirstSensorProgressListener l3 = new OnFirstSensorProgressListener();
        OnSecondSensorProgressListenerX l4 = new OnSecondSensorProgressListenerX();
        OnSecondSensorProgressListenerY l5 = new OnSecondSensorProgressListenerY();
        OnSecondSensorProgressListenerZ l6 = new OnSecondSensorProgressListenerZ();

        //δημιουργια δεικτων στα αντιστοιχα κουμπια,seek bar,text view (συνδεση)
        buttonSaveData = (Button) findViewById(R.id.buttonSaveData);
        buttonSaveData.setOnClickListener(l1);

        seekBarSamplingRate = (SeekBar) findViewById(R.id.seekBarSamplingRate);
        seekBarFirstSensor = (SeekBar) findViewById(R.id.seekBarFirstSensor);
        seekBarSecondSensorX = (SeekBar) findViewById(R.id.seekBarSecondSensorX);
        seekBarSecondSensorY = (SeekBar) findViewById(R.id.seekBarSecondSensorY);
        seekBarSecondSensorZ = (SeekBar) findViewById(R.id.seekBarSecondSensorZ);

        textViewSamplingRateValue = (TextView) findViewById(R.id.textViewsamplingRateValue);
        textViewFirstSensorValue = (TextView) findViewById(R.id.textViewFirstSensorValue);
        textViewSecondSensorValueX = (TextView) findViewById(R.id.textViewSecondSensorValueX);
        textViewSecondSensorValueY = (TextView) findViewById(R.id.textViewSecondSensorValueY);
        textViewSecondSensorValueZ = (TextView) findViewById(R.id.textViewSecondSensorValueZ);

        plainTextMosquitoIP = (EditText) findViewById(R.id.plainTextMosquitoIP);
        plainTextMosquitoPort = (EditText) findViewById(R.id.plainTextMosquitoPort);

        autoswitch = (Switch) findViewById(R.id.switchAutomaticMode);

        //φορτωση ρυθμισεων του χρηστη απο τον δισκο
        DataSettings settings = handler.loadFromDisk(this);

        //αλλαγης στις τιμες των seek bar με βαση το αρχειο
        seekBarSamplingRate.setProgress(settings.frequency);
        seekBarFirstSensor.setProgress(settings.firstsensorvalue);
        seekBarSecondSensorX.setProgress(settings.secondsensorvalue_x);
        seekBarSecondSensorY.setProgress(settings.secondsensorvalue_y);
        seekBarSecondSensorZ.setProgress(settings.secondsensorvalue_z);

        //ελεγχος αλλαγης στις τιμες των seek bar απο τον χρηστη
        seekBarSamplingRate.setOnSeekBarChangeListener(l2);
        seekBarFirstSensor.setOnSeekBarChangeListener(l3);
        seekBarSecondSensorX.setOnSeekBarChangeListener(l4);
        seekBarSecondSensorY.setOnSeekBarChangeListener(l5);
        seekBarSecondSensorZ.setOnSeekBarChangeListener(l6);

        //εμφανιση στα text view και στα plaintext(προσθήκη)
        textViewSamplingRateValue.setText(String.valueOf(settings.frequency));
        textViewFirstSensorValue.setText(String.valueOf(settings.firstsensorvalue));
        textViewSecondSensorValueX.setText(String.valueOf(settings.secondsensorvalue_x));
        textViewSecondSensorValueY.setText(String.valueOf(settings.secondsensorvalue_y));
        textViewSecondSensorValueZ.setText(String.valueOf(settings.secondsensorvalue_z));
        plainTextMosquitoIP.setText(settings.mosquito_ip);
        String s= "" +settings.mosquito_port;
        plainTextMosquitoPort.setText(s);
        //επιλογή του χρήστη για automatic mode
        if (DataSettings.automatic) {
            autoswitch.setChecked(true);
        } else {
            autoswitch.setChecked(false);
        }
    }
}
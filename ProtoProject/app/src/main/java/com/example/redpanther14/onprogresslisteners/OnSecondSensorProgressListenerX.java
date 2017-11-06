package com.example.redpanther14.onprogresslisteners;

import android.app.Activity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.redpanther14.protoproject.R;

public class OnSecondSensorProgressListenerX implements SeekBar.OnSeekBarChangeListener {

    /**
     *             εναλλαγη του seek bar για  για τον αξονα χ του accelerometer
     *
     * @param   seekBar     το seek bar
     * @param   progress    εναλλαγή
     * @param   fromUser    αληθης αν υπηρξε αλλαγη απο τον χρηστη
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Activity activity = (Activity) seekBar.getContext();

        // εμφανισε τη στο αντοιστοιχο text view
        TextView textViewSecondSensorValue = (TextView) activity.findViewById(R.id.textViewSecondSensorValueX);
        textViewSecondSensorValue.setText(String.valueOf(progress));
    }

    //δεν υλοποιηθηκε
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    //δεν υλοποιηθηκε
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

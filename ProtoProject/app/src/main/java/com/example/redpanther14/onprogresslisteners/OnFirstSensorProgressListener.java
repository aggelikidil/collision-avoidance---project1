package com.example.redpanther14.onprogresslisteners;

import android.app.Activity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.redpanther14.protoproject.R;

public class OnFirstSensorProgressListener implements SeekBar.OnSeekBarChangeListener {

    /**
     *             εναλλαγη του seek bar για το proximity
     *
     * @param   seekBar     το seek bar
     * @param   progress    εναλλαγή
     * @param   fromUser    αληθης αν υπηρξε αλλαγη απο τον χρηστη
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Activity activity = (Activity) seekBar.getContext();

        // εμφανισε τη στο αντοιστοιχο text view
        TextView textViewFirstSensorValue = (TextView) activity.findViewById(R.id.textViewFirstSensorValue);
        textViewFirstSensorValue.setText(String.valueOf(progress));
}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

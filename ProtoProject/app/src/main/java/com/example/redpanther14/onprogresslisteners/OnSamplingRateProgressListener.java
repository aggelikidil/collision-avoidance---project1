package com.example.redpanther14.onprogresslisteners;

import android.app.Activity;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.redpanther14.protoproject.R;

public class OnSamplingRateProgressListener implements SeekBar.OnSeekBarChangeListener {

    /**
     *             εναλλαγη του seek bar για το συχνοτητας
     *
     * @param   seekBar     το seek bar
     * @param   progress    εναλλαγή
     * @param   fromUser    αληθης αν υπηρξε αλλαγη απο τον χρηστη
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Activity activity = (Activity) seekBar.getContext();

        //αν η τιμη ειναι μεγαλυτερη του 0 εμφανισε τη στο αντοιστοιχο text view
        if (progress > 0) {
            TextView textViewsamplingRateValue = (TextView) activity.findViewById(R.id.textViewsamplingRateValue);
            textViewsamplingRateValue.setText(String.valueOf(progress));
        } else {
            //διαφορετικα error
            Toast.makeText(activity, "Value Is Not Valid - Change Bar Value ", Toast.LENGTH_SHORT).show();
        }
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

package com.example.redpanther14.oncheckchanged;


import android.widget.CompoundButton;


import com.example.redpanther14.data.DataSettings;


public class OnOnlineOffLineCheckChanged implements CompoundButton.OnCheckedChangeListener {
    @Override
    /**
     * allagh ths boolean timhs tou checkbox analoga me thn epilogh tou xrhsth
     */
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            DataSettings.online = true;
        } else {
            DataSettings.online = false;
        }
    }
}

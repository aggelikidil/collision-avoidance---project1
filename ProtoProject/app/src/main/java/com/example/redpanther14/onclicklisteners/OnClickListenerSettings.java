package com.example.redpanther14.onclicklisteners;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.redpanther14.protoproject.SettingsActivity;

public class OnClickListenerSettings implements Button.OnClickListener {
    /**
     *             διαχείριση του πατηματος του SETTINGS
     *
     * @param   v     το view του application
     */
    @Override
    public void onClick(View v) {
        //ανοιγμα νέου activity με το πατημα του κουμπιου (Settings)
        Intent intent = new Intent(v.getContext(), SettingsActivity.class);
        v.getContext().startActivity(intent);
    }
}

package com.example.redpanther14.protoproject;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.example.redpanther14.oncheckchanged.OnOnlineOffLineCheckChanged;
import com.example.redpanther14.onclicklisteners.OnClickListenerSettings;
import com.example.redpanther14.onclicklisteners.OnClickListenerStartMonitoring;
import com.example.redpanther14.onclicklisteners.OnClickListenerStopMonitoring;
import com.example.redpanther14.onsensoreventlisteners.OnAccelerometerSensorEventListener;
import com.example.redpanther14.onsensoreventlisteners.OnProximitySensorEventListener;
import com.example.redpanther14.savelisteners.ListenersStorage;
import com.example.redpanther14.threads.GpsNetworkThread;
import com.example.redpanther14.threads.PublishThread;
import com.example.redpanther14.threads.SubscribeThread;
import com.example.redpanther14.uniquenumber.CreateUniqueNumber;


public class MainActivity extends AppCompatActivity {
    GpsNetworkThread gpsNetworkThread;

    Button buttonSettings;
    Button buttonStartMonitoring;
    Button buttonStopMonitoring;

    CheckBox checkbox;

    PublishThread publishThread;
    SubscribeThread subscribeThread;
    /**
     *     δημιουργια του Main activity
     *
     * @param   savedInstanceState     αποθηκευση της καταστασης του activity για περιπτωση επαναφορτωσης
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //εμφανιση του logo
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }

        //δημιουργια 3 αντικειμενων-listners μεσω του ονοματος της καθε κλασης
        OnClickListenerSettings l1 = new OnClickListenerSettings();
        OnClickListenerStartMonitoring l2 = new OnClickListenerStartMonitoring();
        OnClickListenerStopMonitoring l3 =new OnClickListenerStopMonitoring();

        //δεικτες στη διευθυνση μνημης των κουμπιών
        buttonSettings = (Button) findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(l1);

        buttonStartMonitoring = (Button) findViewById(R.id.buttonStart);
        buttonStartMonitoring.setOnClickListener(l2);

        buttonStopMonitoring = (Button) findViewById(R.id.buttonStop);
        buttonStopMonitoring.setOnClickListener(l3);

        checkbox = (CheckBox) findViewById(R.id.checkBox);
        checkbox.setOnCheckedChangeListener(new OnOnlineOffLineCheckChanged());

        //δημιουργία unique number
        CreateUniqueNumber cun =new CreateUniqueNumber();
        //εμφάνιση του unique number σε μορφή toast οταν ανοίγει η εφαρμογη
        String myid = cun.create(this);
        Toast.makeText(this, myid, Toast.LENGTH_LONG).show();

        //παίρνουμε τις συντεταγμένες του τερματικού
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        gpsNetworkThread = new GpsNetworkThread(this, locationManager, this, checkbox);
        //ενεργοποίηση gpsnetworkthread
        gpsNetworkThread.start();

        publishThread = new PublishThread(this, locationManager);
        //ενεργοποίηση publishthread
        publishThread.start();

        subscribeThread = new SubscribeThread(this, locationManager);
        //ενεργοποίηση subscribethread
        subscribeThread.start();
    }

    /**
     *     δημιουργια του options menu
     *
     * @param   menu     το μενου
     * @return  true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            //επιλογη settings απο το menu
            case R.id.menuid_appsettings:
                Toast.makeText(getApplicationContext(),"settings",Toast.LENGTH_SHORT).show();
                //μεταβαση στο settings activity
                Intent intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                return true;
            //επιλογη exit απο το menu
            case R.id.menuid_exit:
                Toast.makeText(getApplicationContext(),"exit",Toast.LENGTH_SHORT).show();
                exitApplication();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //σε περιπτωση που πατηθει το back button
    @Override
    public void onBackPressed() {

        exitApplication();
    }

    private void exitApplication() {
        final Activity activity = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //εμφανιση του dialog box για επιβεβαιωση εξοδου
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Quit");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //αν YES
                SensorManager mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);

                OnProximitySensorEventListener l1 = ListenersStorage.l1;
                OnAccelerometerSensorEventListener l2 = ListenersStorage.l2;

                //αποσυνδεση των listeners αν εχει ηδη πατηθει το START
                if (l1 != null) {
                    mSensorManager.unregisterListener(l1);
                    l1 = null;
                }
                if (l2 != null) {
                    mSensorManager.unregisterListener(l2);
                    l2 = null;
                }

                try {
                    gpsNetworkThread.interrupt();
                } catch (Exception e) {

                }

                try {
                    publishThread.interrupt();
                } catch (Exception e) {

                }

                try {
                    subscribeThread.interrupt();
                } catch (Exception e) {

                }


                finish();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

}

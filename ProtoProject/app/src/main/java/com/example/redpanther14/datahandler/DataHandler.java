package com.example.redpanther14.datahandler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.redpanther14.data.DataSettings;
import com.example.redpanther14.uniquenumber.CreateUniqueNumber;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 *              διάβασμα και γράψιμο στον δίσκο
 *
 */

    public class DataHandler {
        /**
         *              διάβασμα απο τον δίσκο
         *
         * @param   context     to context tou application
         * @return              Επιστρέφει τις ρυθμίσεις της εφαρμογής
         */
        public DataSettings loadFromDisk(Context context) { //διάβασμα απο τον δίσκο
            DataSettings data = new DataSettings();

            try { //αν υπάρχει αρχείο διάβασε απο αυτό
                FileInputStream fileInputStream = context.openFileInput("config.txt");
                InputStreamReader reader = new InputStreamReader(fileInputStream);
                CreateUniqueNumber cun = new CreateUniqueNumber();

                data.frequency = reader.read();
                data.firstsensorvalue = reader.read();
                data.secondsensorvalue_x = reader.read();
                data.secondsensorvalue_y = reader.read();
                data.secondsensorvalue_z = reader.read();
                data.id = cun.create(context);
                data.mosquito_port = reader.read();

                int x = reader.read();
                int y = reader.read();
                int z = reader.read();
                int w = reader.read();

                data.mosquito_ip = x + "." + y + "." + z + "." + w;

                reader.close();
                fileInputStream.close();

                return data;
            } catch (IOException e) { //διαφορετικα επέστρεψε τις αρχικές τιμές του datasettings
                Log.w("FILE IO", e.toString());

                return data;
            }
        }

        /**
         *              ανάγνωση απο τον δίσκο
         *
         * @param   context     to context tou application
         */

        public void saveToDisk(DataSettings data, Context context) {
            try {
                //αν υπάρχει το αρχείο γράψε σε αυτό
                FileOutputStream fileOutputStream = context.openFileOutput("config.txt", Context.MODE_PRIVATE);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

                //αλλαγή τιμών στο datasettings
                outputStreamWriter.write(data.frequency);
                outputStreamWriter.write(data.firstsensorvalue);
                outputStreamWriter.write(data.secondsensorvalue_x);
                outputStreamWriter.write(data.secondsensorvalue_y);
                outputStreamWriter.write(data.secondsensorvalue_z);
                outputStreamWriter.write(data.mosquito_port);

                String [] f = data.mosquito_ip.split("[.]");

                for (String s : f) {
                    int i = Integer.parseInt(s);
                    outputStreamWriter.write(i);
                }

                fileOutputStream.flush();

                outputStreamWriter.close();
                fileOutputStream.close();

                Toast.makeText(context, "Successfully Saved", Toast.LENGTH_SHORT).show();
            } catch (IOException e) { //error
                Toast.makeText(context, "Settings Save Failed", Toast.LENGTH_SHORT).show();
                Log.e("Exception", "File Write Failed: " + e.toString());
            }
        }

    }







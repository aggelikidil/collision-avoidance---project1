package com.example.redpanther14.uniquenumber;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;




public class CreateUniqueNumber {
    /**
     * dhmiourgia monadikou device_id gia to kathe termatiko
     * @param context to contex tis efarmogis
     * @return hash_value  to monadiko kleidi meta ton katakermatismo MD5
     */
    public String create(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        if (address == null) {
            address = "";
        }

        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            return "000000000000000000000000000000000";
        } else {
            String android_id = telephonyManager.getDeviceId();

            String  hash_string =  address + android_id;

            MD5Hash md5calculator = new MD5Hash();

            String hash_value = md5calculator.md5(hash_string);
            return hash_value;
        }
    }
}

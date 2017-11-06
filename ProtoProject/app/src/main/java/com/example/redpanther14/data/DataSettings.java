package com.example.redpanther14.data;

/**
 *            αρχικοποίηση ιιμών
 *
 *
 */
public class DataSettings {
    public String id;
    public int frequency = 10;
    public int firstsensorvalue = 10;
    public int secondsensorvalue_x = 10;
    public int secondsensorvalue_y = 10;
    public int secondsensorvalue_z = 10;

    public String mosquito_ip = "127.0.0.1";
    public int mosquito_port=1883;

    public static boolean automatic = false; // user setting
    public static boolean online = false;   // user setting (if wants to use internet)
}

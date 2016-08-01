package com.proton.bystone.utils;

import android.util.Log;

import com.proton.bystone.config.Config;

/**
 * Created by MasterFan on 2016/7/15.
 */
public class L {
    private static final String TAG_E = "MTF_ERROR";
    private static final String TAG_I = "MTF_INFO";


    public static void e(Object msg) {
        if (Config.DEBUG) {
            Log.e(TAG_E, msg.toString());
        }
    }

    public static void i(Object msg) {
        if (Config.DEBUG) {
            Log.e(TAG_I, msg.toString());
        }
    }
}

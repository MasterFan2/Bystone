package com.proton.bystone.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by MasterFan on 2016/7/18.
 */
public class T {
    public static void s(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}

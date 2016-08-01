package com.proton.bystone.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.proton.bystone.config.Config;

/**
 * Created by MasterFan on 2016/7/18.
 */
public class S {
    public static void s(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void o(String msg) {
        if (Config.DEBUG)
            System.out.println(msg);
    }
}

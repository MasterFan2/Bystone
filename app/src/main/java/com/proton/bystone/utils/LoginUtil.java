package com.proton.bystone.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/7/26.
 */
public class LoginUtil {

    private static final String LOGIN_TAG = "logined";

    /**
     * is activity_login
     * @param context
     * @return
     */
    public static boolean checkLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences("activity_login", Context.MODE_PRIVATE);
        return sp.getBoolean(LOGIN_TAG, false);
    }

    public static void saveLogin(Context context, boolean isLogin) {
        SharedPreferences sp = context.getSharedPreferences("activity_login", Context.MODE_PRIVATE);
        sp.edit().putBoolean(LOGIN_TAG, isLogin).commit();
    }

    public static void clearLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences("activity_login", Context.MODE_PRIVATE);
        sp.edit().remove(LOGIN_TAG).commit();
    }

}

package com.proton.bystone.utils;

/**
 * Created by MasterFan on 2016/7/23.
 */
public class TimeUtil {
    public static String getStringByIntIfNumLessThanTen(int num) {
        return num < 10 ? "0" + num : num+"";
    }
}

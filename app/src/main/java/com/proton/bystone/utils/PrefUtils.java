package com.proton.bystone.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.proton.bystone.bean.MyLocation;

/**
 * Created by MasterFan on 2016/7/29.
 */
public class PrefUtils {
    private static final String PRIVETE_NAME = "proton";

    /**
     * 保存数据
     * @param mContext
     * @param location
     */
    public static void save(Context mContext, MyLocation location) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PRIVETE_NAME, Context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("address", location.getAddr());
        editor.putFloat("latitude", (float) location.getLatitude());
        editor.putFloat("longitude", (float) location.getLongitude());
        editor.putString("cityCode", location.getCityCode());
        editor.putString("district", location.getDistrict());
        editor.commit();//提交修改
    }

    /**
     * 获取数据
     * @param mContext
     * @return
     */
    public static MyLocation get(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PRIVETE_NAME, Context.MODE_PRIVATE);
        String address   = sharedPreferences.getString("address", null);
        double latitude  = sharedPreferences.getFloat("latitude", 0f);
        double longitude = sharedPreferences.getFloat("longitude", 0f);
        String cityCode  = sharedPreferences.getString("cityCode", null);
        String district  = sharedPreferences.getString("district", null);
        MyLocation location = new MyLocation(address, latitude, longitude, cityCode);
        location.setDistrict(district);
//        sharedPreferences.edit().clear().commit();//删除
        return location;
    }

    /**
     * 清除数据
     * @param mContext
     */
    public static void clear(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PRIVETE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}

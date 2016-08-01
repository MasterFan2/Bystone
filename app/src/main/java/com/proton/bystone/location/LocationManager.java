package com.proton.bystone.location;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * 位置管理类， 主要用于获取位置信息。包括经纬度、省、市、区、街道等信息
 * 使用步骤：
 * 1.LocationManager.getInstance().init(context);   //初始化参数
 *
 * //设置监听 用到的地方 implements AMapLocationListener
 * 2.LocationManager.getInstance().setAMapLocationListener(this);
 *
 * 3.LocationManager.getInstance().startLocation(); //开始定位
 * 4.LocationManager.getInstance().stopLocation();  //结束定位
 * 5.LocationManager.getInstance().destroy();       //销毁   在activity的onDestroy中调用
 *
 * Created by MasterFan on 2016/7/27.
 */
public class LocationManager {

    private static LocationManager single;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private Context mContext;

    private AMapLocationListener aMapLocationListener;

    public static LocationManager getInstance() {
        if (single == null) {
            single = new LocationManager();
        }
        return single;
    }

    private LocationManager(){}

    /**
     * 设置监听
     * @param aMapLocationListener
     */
    public void setAMapLocationListener(AMapLocationListener aMapLocationListener) {
        this.aMapLocationListener = aMapLocationListener;
    }

    /**
     * 初始化、设置参数
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        locationClient = new AMapLocationClient(mContext);
        locationOption = new AMapLocationClientOption();

        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);

        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(false);

        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(false);

        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        String strInterval = etInterval.getText().toString();
//        if (!TextUtils.isEmpty(strInterval)) {
//            // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
//            locationOption.setInterval(Long.valueOf(strInterval));
//        }
    }

    /**
     * 开始定位
     */
    public void startLocation() {

        // 设置定位参数
        locationClient.setLocationOption(locationOption);

        if (aMapLocationListener == null)
            throw new RuntimeException("LocationManager>>> 未设置定位监听回调!!!");

        locationClient.setLocationListener(aMapLocationListener);

        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        locationClient.stopLocation();
    }

    /**
     * 销毁
     */
    public void destroy() {
        if (null != locationClient){
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
}

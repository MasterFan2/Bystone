package com.proton.bystone.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by MasterFan on 2016/7/30.
 */
@Table(name = "MyLocation")
public class MyLocation {

    @Column(name = "location_id", isId = true, autoGen = true)
    private int location_id;

    @Column(name = "addr")
    private String addr;//详细地址

    @Column(name = "district")
    private String district;//区

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "cityCode")
    private String cityCode;

    public String getAddr() {
        return addr;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getLocation_id() {

        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public MyLocation(String addr, double latitude, double longitude, String cityCode) {

        this.addr = addr;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityCode = cityCode;
    }
}

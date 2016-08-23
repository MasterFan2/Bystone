package com.proton.bystone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/8/12.
 * 地区
 */
public class Region implements Parcelable {

    /**
     * ID : 23
     * CODE : 500000
     * NAME : 重庆市
     * PARENT_ID : 1
     * LEVELS : 1
     * ORDERId : 0
     * NAME_EN : Chongqing Shi
     * SHORTNAME_EN : CQ
     * Enable : 1
     */

    private int ID;
    private String CODE;
    private String NAME;
    private String PARENT_ID;
    private int LEVELS;
    private int ORDERId;
    private String NAME_EN;
    private String SHORTNAME_EN;
    private int Enable;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(String PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    public int getLEVELS() {
        return LEVELS;
    }

    public void setLEVELS(int LEVELS) {
        this.LEVELS = LEVELS;
    }

    public int getORDERId() {
        return ORDERId;
    }

    public void setORDERId(int ORDERId) {
        this.ORDERId = ORDERId;
    }

    public String getNAME_EN() {
        return NAME_EN;
    }

    public void setNAME_EN(String NAME_EN) {
        this.NAME_EN = NAME_EN;
    }

    public String getSHORTNAME_EN() {
        return SHORTNAME_EN;
    }

    public void setSHORTNAME_EN(String SHORTNAME_EN) {
        this.SHORTNAME_EN = SHORTNAME_EN;
    }

    public int getEnable() {
        return Enable;
    }

    public void setEnable(int Enable) {
        this.Enable = Enable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.CODE);
        dest.writeString(this.NAME);
        dest.writeString(this.PARENT_ID);
        dest.writeInt(this.LEVELS);
        dest.writeInt(this.ORDERId);
        dest.writeString(this.NAME_EN);
        dest.writeString(this.SHORTNAME_EN);
        dest.writeInt(this.Enable);
    }

    public Region() {
    }

    protected Region(Parcel in) {
        this.ID = in.readInt();
        this.CODE = in.readString();
        this.NAME = in.readString();
        this.PARENT_ID = in.readString();
        this.LEVELS = in.readInt();
        this.ORDERId = in.readInt();
        this.NAME_EN = in.readString();
        this.SHORTNAME_EN = in.readString();
        this.Enable = in.readInt();
    }

    public static final Parcelable.Creator<Region> CREATOR = new Parcelable.Creator<Region>() {
        @Override
        public Region createFromParcel(Parcel source) {
            return new Region(source);
        }

        @Override
        public Region[] newArray(int size) {
            return new Region[size];
        }
    };
}

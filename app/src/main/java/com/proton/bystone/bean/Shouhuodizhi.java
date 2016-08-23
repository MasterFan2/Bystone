package com.proton.bystone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/7/22.
 * 收货地址
 */
public class Shouhuodizhi implements Parcelable {

    /**
     * ID : 1
     * User_Code : 201605261656057265
     * Ad_ContactNumber : 555555
     * Ad_Name : aaa
     * Ad_Address : xxxg
     * IsDefault : 0
     * AddressDetaile : ggg
     */

    private int ID;
    private String User_Code;
    private String Ad_ContactNumber;
    private String Ad_Name;
    private String Ad_Address;
    private int IsDefault;
    private String AddressDetaile;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUser_Code() {
        return User_Code;
    }

    public void setUser_Code(String User_Code) {
        this.User_Code = User_Code;
    }

    public String getAd_ContactNumber() {
        return Ad_ContactNumber;
    }

    public void setAd_ContactNumber(String Ad_ContactNumber) {
        this.Ad_ContactNumber = Ad_ContactNumber;
    }

    public String getAd_Name() {
        return Ad_Name;
    }

    public void setAd_Name(String Ad_Name) {
        this.Ad_Name = Ad_Name;
    }

    public String getAd_Address() {
        return Ad_Address;
    }

    public void setAd_Address(String Ad_Address) {
        this.Ad_Address = Ad_Address;
    }

    public int getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(int IsDefault) {
        this.IsDefault = IsDefault;
    }

    public String getAddressDetaile() {
        return AddressDetaile;
    }

    public void setAddressDetaile(String AddressDetaile) {
        this.AddressDetaile = AddressDetaile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.User_Code);
        dest.writeString(this.Ad_ContactNumber);
        dest.writeString(this.Ad_Name);
        dest.writeString(this.Ad_Address);
        dest.writeInt(this.IsDefault);
        dest.writeString(this.AddressDetaile);
    }

    public Shouhuodizhi() {
    }

    protected Shouhuodizhi(Parcel in) {
        this.ID = in.readInt();
        this.User_Code = in.readString();
        this.Ad_ContactNumber = in.readString();
        this.Ad_Name = in.readString();
        this.Ad_Address = in.readString();
        this.IsDefault = in.readInt();
        this.AddressDetaile = in.readString();
    }

    public static final Parcelable.Creator<Shouhuodizhi> CREATOR = new Parcelable.Creator<Shouhuodizhi>() {
        @Override
        public Shouhuodizhi createFromParcel(Parcel source) {
            return new Shouhuodizhi(source);
        }

        @Override
        public Shouhuodizhi[] newArray(int size) {
            return new Shouhuodizhi[size];
        }
    };
}

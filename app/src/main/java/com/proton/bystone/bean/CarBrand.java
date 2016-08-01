package com.proton.bystone.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.proton.library.utils.PinyinUtil;

/**
 * Created by MasterFan on 2016/7/16.
 */
public class CarBrand implements Comparable<CarBrand>,Parcelable {

    /**
     * B_Name : Alpina
     * Levels : 1
     * B_Code : 201607121450309041
     * BrandAcronym :
     * B_logo :
     */

    private String B_Name;
    private int Levels;
    private String B_Code;
    private String BrandAcronym;
    private String B_logo;
    private String pinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getB_Name() {
        return B_Name;
    }

    public void setB_Name(String B_Name) {
        this.B_Name = B_Name;
        this.pinyin = PinyinUtil.getPinyin(B_Name);
    }

    public int getLevels() {
        return Levels;
    }

    public void setLevels(int Levels) {
        this.Levels = Levels;
    }

    public String getB_Code() {
        return B_Code;
    }

    public void setB_Code(String B_Code) {
        this.B_Code = B_Code;
    }

    public String getBrandAcronym() {
        return BrandAcronym;
    }

    public void setBrandAcronym(String BrandAcronym) {
        this.BrandAcronym = BrandAcronym;
    }

    public String getB_logo() {
        return B_logo;
    }

    public void setB_logo(String B_logo) {
        this.B_logo = B_logo;
    }

    @Override
    public int compareTo(CarBrand another) {
        return pinyin.compareTo(another.pinyin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.B_Name);
        dest.writeInt(this.Levels);
        dest.writeString(this.B_Code);
        dest.writeString(this.BrandAcronym);
        dest.writeString(this.B_logo);
        dest.writeString(this.pinyin);
    }

    public CarBrand() {
    }

    protected CarBrand(Parcel in) {
        this.B_Name = in.readString();
        this.Levels = in.readInt();
        this.B_Code = in.readString();
        this.BrandAcronym = in.readString();
        this.B_logo = in.readString();
        this.pinyin = in.readString();
    }

    public static final Parcelable.Creator<CarBrand> CREATOR = new Parcelable.Creator<CarBrand>() {
        @Override
        public CarBrand createFromParcel(Parcel source) {
            return new CarBrand(source);
        }

        @Override
        public CarBrand[] newArray(int size) {
            return new CarBrand[size];
        }
    };
}

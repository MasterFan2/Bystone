package com.proton.bystone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 二级品牌
 * Created by MasterFan on 2016/7/18.
 */
public class CarBrandTwoLevel implements Parcelable {

    /**
     * B_Name : 阿尔法罗密欧  进口 ALFA 147
     * B_Code : 2016053018372185
     * ParentCode : 201607121031256296
     */

    private String B_Name;
    private String B_Code;
    private String ParentCode;

    public String getB_Name() {
        return B_Name;
    }

    public void setB_Name(String B_Name) {
        this.B_Name = B_Name;
    }

    public String getB_Code() {
        return B_Code;
    }

    public void setB_Code(String B_Code) {
        this.B_Code = B_Code;
    }

    public String getParentCode() {
        return ParentCode;
    }

    public void setParentCode(String ParentCode) {
        this.ParentCode = ParentCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.B_Name);
        dest.writeString(this.B_Code);
        dest.writeString(this.ParentCode);
    }

    public CarBrandTwoLevel() {
    }

    protected CarBrandTwoLevel(Parcel in) {
        this.B_Name = in.readString();
        this.B_Code = in.readString();
        this.ParentCode = in.readString();
    }

    public static final Parcelable.Creator<CarBrandTwoLevel> CREATOR = new Parcelable.Creator<CarBrandTwoLevel>() {
        @Override
        public CarBrandTwoLevel createFromParcel(Parcel source) {
            return new CarBrandTwoLevel(source);
        }

        @Override
        public CarBrandTwoLevel[] newArray(int size) {
            return new CarBrandTwoLevel[size];
        }
    };
}

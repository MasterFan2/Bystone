package com.proton.bystone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MasterFan on 2016/7/18.
 */
public class CarModel implements Parcelable {

    /**
     * Model : 2013 款 1.3L  标准型
     * M_Code : 201605301849223
     * M_Year : 2013
     */

    private String Model;
    private String M_Code;
    private String M_Year;

    public String getModel() {
        return Model;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public String getM_Code() {
        return M_Code;
    }

    public void setM_Code(String M_Code) {
        this.M_Code = M_Code;
    }

    public String getM_Year() {
        return M_Year;
    }

    public void setM_Year(String M_Year) {
        this.M_Year = M_Year;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Model);
        dest.writeString(this.M_Code);
        dest.writeString(this.M_Year);
    }

    public CarModel() {
    }

    protected CarModel(Parcel in) {
        this.Model = in.readString();
        this.M_Code = in.readString();
        this.M_Year = in.readString();
    }

    public static final Parcelable.Creator<CarModel> CREATOR = new Parcelable.Creator<CarModel>() {
        @Override
        public CarModel createFromParcel(Parcel source) {
            return new CarModel(source);
        }

        @Override
        public CarModel[] newArray(int size) {
            return new CarModel[size];
        }
    };
}

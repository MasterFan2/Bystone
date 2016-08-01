package com.proton.bystone.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by MasterFan on 2016/7/18.
 */
@Table(name = "CarUploadParam")
public class CarUploadParam implements Parcelable {

    @Column(name = "carUploadId", isId = true)
    private int carUploadId;

    @Column(name = "User_Code")
    private String User_Code;

    @Column(name = "I_CarDetail")
    private String I_CarDetail;

    @Column(name = "VC_CarNO")
    private String VC_CarNO;

    @Column(name = "VC_CarFDJ")
    private String VC_CarFDJ;

    @Column(name = "CarIdentifier")
    private String CarIdentifier;

    public CarUploadParam() {}

    public CarUploadParam(String user_Code, String i_CarDetail, String VC_CarNO, String VC_CarFDJ, String carIdentifier) {
        User_Code = user_Code;
        I_CarDetail = i_CarDetail;
        this.VC_CarNO = VC_CarNO;
        this.VC_CarFDJ = VC_CarFDJ;
        CarIdentifier = carIdentifier;
    }

    public int getCarUploadId() {
        return carUploadId;
    }

    public void setCarUploadId(int carUploadId) {
        this.carUploadId = carUploadId;
    }

    public String getUser_Code() {
        return User_Code;
    }

    public void setUser_Code(String user_Code) {
        User_Code = user_Code;
    }

    public String getI_CarDetail() {
        return I_CarDetail;
    }

    public void setI_CarDetail(String i_CarDetail) {
        I_CarDetail = i_CarDetail;
    }

    public String getVC_CarNO() {
        return VC_CarNO;
    }

    public void setVC_CarNO(String VC_CarNO) {
        this.VC_CarNO = VC_CarNO;
    }

    public String getVC_CarFDJ() {
        return VC_CarFDJ;
    }

    public void setVC_CarFDJ(String VC_CarFDJ) {
        this.VC_CarFDJ = VC_CarFDJ;
    }

    public String getCarIdentifier() {
        return CarIdentifier;
    }

    public void setCarIdentifier(String carIdentifier) {
        CarIdentifier = carIdentifier;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.carUploadId);
        dest.writeString(this.User_Code);
        dest.writeString(this.I_CarDetail);
        dest.writeString(this.VC_CarNO);
        dest.writeString(this.VC_CarFDJ);
        dest.writeString(this.CarIdentifier);
    }

    protected CarUploadParam(Parcel in) {
        this.carUploadId = in.readInt();
        this.User_Code = in.readString();
        this.I_CarDetail = in.readString();
        this.VC_CarNO = in.readString();
        this.VC_CarFDJ = in.readString();
        this.CarIdentifier = in.readString();
    }

    public static final Parcelable.Creator<CarUploadParam> CREATOR = new Parcelable.Creator<CarUploadParam>() {
        @Override
        public CarUploadParam createFromParcel(Parcel source) {
            return new CarUploadParam(source);
        }

        @Override
        public CarUploadParam[] newArray(int size) {
            return new CarUploadParam[size];
        }
    };
}

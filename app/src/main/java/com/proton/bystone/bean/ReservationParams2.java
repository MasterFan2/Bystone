package com.proton.bystone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 维保提交参数2
 * Created by MasterFan on 2016/8/10.
 */
public class ReservationParams2 implements Parcelable {

    private String UserCode;//会员编号
    private String Type_CODE;//保养项编号
    private String I_ProCode;//材料编号

    public ReservationParams2() {
    }

    public ReservationParams2(String userCode, String type_CODE, String i_ProCode) {

        UserCode = userCode;
        Type_CODE = type_CODE;
        I_ProCode = i_ProCode;
    }

    public String getUserCode() {

        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getType_CODE() {
        return Type_CODE;
    }

    public void setType_CODE(String type_CODE) {
        Type_CODE = type_CODE;
    }

    public String getI_ProCode() {
        return I_ProCode;
    }

    public void setI_ProCode(String i_ProCode) {
        I_ProCode = i_ProCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.UserCode);
        dest.writeString(this.Type_CODE);
        dest.writeString(this.I_ProCode);
    }

    protected ReservationParams2(Parcel in) {
        this.UserCode = in.readString();
        this.Type_CODE = in.readString();
        this.I_ProCode = in.readString();
    }

    public static final Parcelable.Creator<ReservationParams2> CREATOR = new Parcelable.Creator<ReservationParams2>() {
        @Override
        public ReservationParams2 createFromParcel(Parcel source) {
            return new ReservationParams2(source);
        }

        @Override
        public ReservationParams2[] newArray(int size) {
            return new ReservationParams2[size];
        }
    };
}

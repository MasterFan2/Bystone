package com.proton.bystone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 订单状态的基础数据
 * Created by MasterFan on 2016/7/23.
 */
public class OrderBaseInfo implements Parcelable {

    /**
     * BookCode : 201607231653584604
     * UserName : 张三
     * Mobile : 13512365892
     * AddressMTF : 重庆市江北区
     * BookBeginTime : 2016-07-23 08:54:00至2016-07-23 09:54:00
     * BookStaus : 0
     */

    private String BookCode;
    private String UserName;
    private String Mobile;
    private String Address;
    private String BookBeginTime;
    private int BookStaus;

    public String getBookCode() {
        return BookCode;
    }

    public void setBookCode(String BookCode) {
        this.BookCode = BookCode;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getBookBeginTime() {
        return BookBeginTime;
    }

    public void setBookBeginTime(String BookBeginTime) {
        this.BookBeginTime = BookBeginTime;
    }

    public int getBookStaus() {
        return BookStaus;
    }

    public void setBookStaus(int BookStaus) {
        this.BookStaus = BookStaus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.BookCode);
        dest.writeString(this.UserName);
        dest.writeString(this.Mobile);
        dest.writeString(this.Address);
        dest.writeString(this.BookBeginTime);
        dest.writeInt(this.BookStaus);
    }

    public OrderBaseInfo() {
    }

    protected OrderBaseInfo(Parcel in) {
        this.BookCode = in.readString();
        this.UserName = in.readString();
        this.Mobile = in.readString();
        this.Address = in.readString();
        this.BookBeginTime = in.readString();
        this.BookStaus = in.readInt();
    }

    public static final Parcelable.Creator<OrderBaseInfo> CREATOR = new Parcelable.Creator<OrderBaseInfo>() {
        @Override
        public OrderBaseInfo createFromParcel(Parcel source) {
            return new OrderBaseInfo(source);
        }

        @Override
        public OrderBaseInfo[] newArray(int size) {
            return new OrderBaseInfo[size];
        }
    };
}

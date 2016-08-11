package com.proton.bystone.bean;

/**
 * 订单状态的基础数据
 * Created by MasterFan on 2016/7/23.
 */
public class OrderBaseInfo {

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
}

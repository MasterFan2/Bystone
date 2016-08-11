package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/7/22.
 */
public class Add_Address_Ok {

    /**
     * User_Code : 会员主表编号
     * Ad_ContactNumber : 联系电话
     * Ad_Name : 联系人
     * Ad_Address : 地区地址
     * IsDefault : 是否默认(0：否，1：是,2:删除)
     * AddressDetaile : 详细地址
     *
     * 添加到服务器收货地址
     */

    private int ID;
    private String User_Code;
    private String Ad_ContactNumber;
    private String Ad_Name;
    private String Ad_Address;
    private String IsDefault;
    private String AddressDetaile;

    public Add_Address_Ok( String user_Code, String ad_ContactNumber, String ad_Name, String ad_Address, String isDefault, String addressDetaile) {

        User_Code = user_Code;
        Ad_ContactNumber = ad_ContactNumber;
        Ad_Name = ad_Name;
        Ad_Address = ad_Address;
        IsDefault = isDefault;
        AddressDetaile = addressDetaile;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUser_Code() {
        return User_Code;
    }

    public void setUser_Code(String user_Code) {
        User_Code = user_Code;
    }

    public String getAd_ContactNumber() {
        return Ad_ContactNumber;
    }

    public void setAd_ContactNumber(String ad_ContactNumber) {
        Ad_ContactNumber = ad_ContactNumber;
    }

    public String getAd_Name() {
        return Ad_Name;
    }

    public void setAd_Name(String ad_Name) {
        Ad_Name = ad_Name;
    }

    public String getAd_Address() {
        return Ad_Address;
    }

    public void setAd_Address(String ad_Address) {
        Ad_Address = ad_Address;
    }

    public String getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(String isDefault) {
        IsDefault = isDefault;
    }

    public String getAddressDetaile() {
        return AddressDetaile;
    }

    public void setAddressDetaile(String addressDetaile) {
        AddressDetaile = addressDetaile;
    }
}

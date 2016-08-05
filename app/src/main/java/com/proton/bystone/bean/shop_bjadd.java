package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/8/1.
 */
public class shop_bjadd {
    private String id;
    private String User_Code;
    private String Ad_ContactNumber;
    private String Ad_Name;
    private String Ad_Address;
    private String IsDefault;
    private String AddressDetaile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public shop_bjadd(String id, String user_Code, String ad_ContactNumber, String ad_Name, String ad_Address, String isDefault, String addressDetaile) {
        this.id = id;
        User_Code = user_Code;
        Ad_ContactNumber = ad_ContactNumber;
        Ad_Name = ad_Name;
        Ad_Address = ad_Address;
        IsDefault = isDefault;
        AddressDetaile = addressDetaile;
    }
}

package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/8/9.
 */
public class AddressMTF {

    /**
     * ID : 236
     * User_Code : 201605261656057265
     * Ad_ContactNumber : 15736639963
     * Ad_Name : 我们的话
     * Ad_Address : 我们
     * IsDefault : 1
     * AddressDetaile : 天天理由
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
}

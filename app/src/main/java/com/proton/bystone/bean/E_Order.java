package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/8/2.
 */
public class E_Order {

    private String BsCode;
    private String UserCode;
    private String VCCode;
    private String OTotalPrice;
    private String Specification;
    private String VcName;
    private String Address;
    private String CmtyNumber;
    private double OSfJe;

    private String OSpHyJ;
    private int ReceiptMode;
    private String ConsumerHotline;
    private int OrderType;
    private String Remark;

    private String Ad_ContactNumber;
    private String Ad_Name;
    private String Ad_Address;

    public E_Order(String bsCode, String userCode, String VCCode, String OTotalPrice, String specification, String vcName, String address, String cmtyNumber, double OSfJe, String OSpHyJ, int receiptMode, String consumerHotline, int orderType, String remark, String ad_ContactNumber, String ad_Name, String ad_Address) {

        BsCode = bsCode;
        UserCode = userCode;
        this.VCCode = VCCode;
        this.OTotalPrice = OTotalPrice;
        Specification = specification;
        VcName = vcName;
        Address = address;
        CmtyNumber = cmtyNumber;
        this.OSfJe = OSfJe;
        this.OSpHyJ = OSpHyJ;
        ReceiptMode = receiptMode;
        ConsumerHotline = consumerHotline;
        OrderType = orderType;
        Remark = remark;
        Ad_ContactNumber = ad_ContactNumber;
        Ad_Name = ad_Name;
        Ad_Address = ad_Address;
    }

    public String getAd_Address() {
        return Ad_Address;
    }

    public void setAd_Address(String ad_Address) {
        Ad_Address = ad_Address;
    }

    public String getBsCode() {
        return BsCode;
    }

    public void setBsCode(String bsCode) {
        BsCode = bsCode;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getVCCode() {
        return VCCode;
    }

    public void setVCCode(String VCCode) {
        this.VCCode = VCCode;
    }

    public String getOTotalPrice() {
        return OTotalPrice;
    }

    public void setOTotalPrice(String OTotalPrice) {
        this.OTotalPrice = OTotalPrice;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }

    public String getVcName() {
        return VcName;
    }

    public void setVcName(String vcName) {
        VcName = vcName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCmtyNumber() {
        return CmtyNumber;
    }

    public void setCmtyNumber(String cmtyNumber) {
        CmtyNumber = cmtyNumber;
    }

    public double getOSfJe() {
        return OSfJe;
    }

    public void setOSfJe(double OSfJe) {
        this.OSfJe = OSfJe;
    }

    public String getOSpHyJ() {
        return OSpHyJ;
    }

    public void setOSpHyJ(String OSpHyJ) {
        this.OSpHyJ = OSpHyJ;
    }

    public int getReceiptMode() {
        return ReceiptMode;
    }

    public void setReceiptMode(int receiptMode) {
        ReceiptMode = receiptMode;
    }

    public String getConsumerHotline() {
        return ConsumerHotline;
    }

    public void setConsumerHotline(String consumerHotline) {
        ConsumerHotline = consumerHotline;
    }

    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int orderType) {
        OrderType = orderType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
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








}

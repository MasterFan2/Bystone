package com.proton.bystone.bean;

/**
 * 小订单
 * Created by MasterFan on 2016/8/9.
 */
public class OrderSubmitMinorParams {
    private String BsCode;      //商家编号,
    private String UserCode;    //会员编号,
    private String VCCode;      //商品编号,
    private String OTotalPrice; //商品原价,
    private String Specification;//商品规格型号,
    private String VcName;      //商品名称,

    //收货地址({"Ad_ContactNumber":"18225026697","Ad_Name":"邓强","Ad_Address":"重庆市-九龙坡区-华奥小区"})维保订单可无此字段
    private String Address;

    private String OSfJe;       //实付款金额,
    private String OJbDk;       //金币抵扣,
    private String CmtyNumber;  //商品数量,
    private String OSpHyJ;      //商品会员价,
    private String ReceiptMode; //提货方式（1：物流，2：自提）,
    private String ConsumerHotline;//客服电话,
    private String OrderType;   //订单类型1：上门保养,2：到店保养，3：购物,4：商家店面直销,
    private String Remark;      //订单备注

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

    public String getOSfJe() {
        return OSfJe;
    }

    public void setOSfJe(String OSfJe) {
        this.OSfJe = OSfJe;
    }

    public String getOJbDk() {
        return OJbDk;
    }

    public void setOJbDk(String OJbDk) {
        this.OJbDk = OJbDk;
    }

    public String getCmtyNumber() {
        return CmtyNumber;
    }

    public void setCmtyNumber(String cmtyNumber) {
        CmtyNumber = cmtyNumber;
    }

    public String getOSpHyJ() {
        return OSpHyJ;
    }

    public void setOSpHyJ(String OSpHyJ) {
        this.OSpHyJ = OSpHyJ;
    }

    public String getReceiptMode() {
        return ReceiptMode;
    }

    public void setReceiptMode(String receiptMode) {
        ReceiptMode = receiptMode;
    }

    public String getConsumerHotline() {
        return ConsumerHotline;
    }

    public void setConsumerHotline(String consumerHotline) {
        ConsumerHotline = consumerHotline;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public OrderSubmitMinorParams() {
    }

    public OrderSubmitMinorParams(String bsCode, String userCode, String VCCode, String OTotalPrice, String specification, String vcName, String address, String OSfJe, String OJbDk, String cmtyNumber, String OSpHyJ, String receiptMode, String consumerHotline, String orderType, String remark) {

        BsCode = bsCode;
        UserCode = userCode;
        this.VCCode = VCCode;
        this.OTotalPrice = OTotalPrice;
        Specification = specification;
        VcName = vcName;
        Address = address;
        this.OSfJe = OSfJe;
        this.OJbDk = OJbDk;
        CmtyNumber = cmtyNumber;
        this.OSpHyJ = OSpHyJ;
        ReceiptMode = receiptMode;
        ConsumerHotline = consumerHotline;
        OrderType = orderType;
        Remark = remark;
    }
}

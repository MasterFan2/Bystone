package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/7/29.
 */
public class Shop_All_Order {

    /**
     * OConsumerName : 2016年7月26日 上门保养
     * BookCode : 201607260955142739
     * OHYZJ : 0.0
     * Data : []
     * OrderType : 400
     * PadctBrief : null
     * BookCarNum : 渝A:1236545
     */

    private String OConsumerName;
    private String BookCode;
    private double OHYZJ;
    private String Data;
    private int OrderType;
    private Object PadctBrief;
    private String BookCarNum;
    private String CreationTime;
    private String VC_Url;
    private String VC_Name;
    private int OrderStatus;
    private String OSfJe;
    private int CmtyNumber;



    public Shop_All_Order() {
    }

    public String getOSfJe() {
        return OSfJe;
    }

    public void setOSfJe(String OSfJe) {
        this.OSfJe = OSfJe;
    }

    public int getCmtyNumber() {
        return CmtyNumber;
    }

    public void setCmtyNumber(int cmtyNumber) {
        CmtyNumber = cmtyNumber;
    }

    public String getVC_Name() {
        return VC_Name;
    }

    public void setVC_Name(String VC_Name) {
        this.VC_Name = VC_Name;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getOConsumerName() {
        return OConsumerName;
    }

    public void setOConsumerName(String OConsumerName) {
        this.OConsumerName = OConsumerName;
    }

    public String getBookCode() {
        return BookCode;
    }

    public void setBookCode(String BookCode) {
        this.BookCode = BookCode;
    }

    public double getOHYZJ() {
        return OHYZJ;
    }

    public void setOHYZJ(double OHYZJ) {
        this.OHYZJ = OHYZJ;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int OrderType) {
        this.OrderType = OrderType;
    }

    public Object getPadctBrief() {
        return PadctBrief;
    }

    public void setPadctBrief(Object PadctBrief) {
        this.PadctBrief = PadctBrief;
    }

    public String getBookCarNum() {
        return BookCarNum;
    }

    public void setBookCarNum(String BookCarNum) {
        this.BookCarNum = BookCarNum;
    }

    public String getCreationTime() {
        return CreationTime;
    }

    public void setCreationTime(String creationTime) {
        CreationTime = creationTime;
    }

    public String getVC_Url() {
        return VC_Url;
    }

    public void setVC_Url(String VC_Url) {
        this.VC_Url = VC_Url;
    }
}

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
}

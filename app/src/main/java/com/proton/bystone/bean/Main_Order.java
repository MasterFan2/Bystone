package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/8/2.
 */
public class Main_Order {
    private String OConsumerName;
    private String UserCode;
    private String OOrglTotalPrice;
    private String OHYZJ;
    private double OJbDkZe;
    private int OrderType;
    private double OSfJe;
    private String BookCode;

    public Main_Order(String OConsumerName, String userCode, String OOrglTotalPrice, String OHYZJ, double OJbDkZe, int orderType, double OSfJe, String bookCode) {
        this.OConsumerName = OConsumerName;
        UserCode = userCode;
        this.OOrglTotalPrice = OOrglTotalPrice;
        this.OHYZJ = OHYZJ;
        this.OJbDkZe = OJbDkZe;
        OrderType = orderType;
        this.OSfJe = OSfJe;
        BookCode = bookCode;
    }

    public String getOConsumerName() {
        return OConsumerName;
    }

    public void setOConsumerName(String OConsumerName) {
        this.OConsumerName = OConsumerName;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getOOrglTotalPrice() {
        return OOrglTotalPrice;
    }

    public void setOOrglTotalPrice(String OOrglTotalPrice) {
        this.OOrglTotalPrice = OOrglTotalPrice;
    }

    public String getOHYZJ() {
        return OHYZJ;
    }

    public void setOHYZJ(String OHYZJ) {
        this.OHYZJ = OHYZJ;
    }

    public double getOJbDkZe() {
        return OJbDkZe;
    }

    public void setOJbDkZe(double OJbDkZe) {
        this.OJbDkZe = OJbDkZe;
    }

    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int orderType) {
        OrderType = orderType;
    }

    public double getOSfJe() {
        return OSfJe;
    }

    public void setOSfJe(double OSfJe) {
        this.OSfJe = OSfJe;
    }

    public String getBookCode() {
        return BookCode;
    }

    public void setBookCode(String bookCode) {
        BookCode = bookCode;
    }
}

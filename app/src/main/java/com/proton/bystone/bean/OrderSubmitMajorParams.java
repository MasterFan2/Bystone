package com.proton.bystone.bean;

/**
 * 主订单
 * Created by MasterFan on 2016/8/9.
 */
public class OrderSubmitMajorParams {
    private String OConsumerName;//主订单名称,
    private String UserCode;//会员编号,
    private String OOrglTotalPrice;//订单原总价,
    private String OHYZJ;//订单会员总价,
    private String OJbDkZe;//金币抵扣总数,
    private String OSfJe;//实付款总金额数,
    private String OrderType;//订单类型,
    private String BookCode;//维保记录编号

    public OrderSubmitMajorParams() {
    }

    public OrderSubmitMajorParams(String OConsumerName, String userCode, String OOrglTotalPrice, String OHYZJ, String OJbDkZe, String OSfJe, String orderType, String bookCode) {

        this.OConsumerName = OConsumerName;
        UserCode = userCode;
        this.OOrglTotalPrice = OOrglTotalPrice;
        this.OHYZJ = OHYZJ;
        this.OJbDkZe = OJbDkZe;
        this.OSfJe = OSfJe;
        OrderType = orderType;
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

    public String getOJbDkZe() {
        return OJbDkZe;
    }

    public void setOJbDkZe(String OJbDkZe) {
        this.OJbDkZe = OJbDkZe;
    }

    public String getOSfJe() {
        return OSfJe;
    }

    public void setOSfJe(String OSfJe) {
        this.OSfJe = OSfJe;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getBookCode() {
        return BookCode;
    }

    public void setBookCode(String bookCode) {
        BookCode = bookCode;
    }
}

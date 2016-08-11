package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/8/8.
 * 退货
 */
public class Cbms {

    /**
     * OrderCode : 订单编号
     * CmtyCode : 商品编号
     * UserCode : 会员编号
     * BusCode : 商家编号
     * ReturnTheReason : 退货原因
     * Path : 退货图片多张时：；隔开
     */

    private String OrderCode;
    private String CmtyCode;
    private String UserCode;
    private String BusCode;
    private String ReturnTheReason;
    private String Path;


    public Cbms(String orderCode, String returnTheReason, String path, String busCode, String userCode, String cmtyCode) {
        OrderCode = orderCode;
        ReturnTheReason = returnTheReason;
        Path = path;
        BusCode = busCode;
        UserCode = userCode;
        CmtyCode = cmtyCode;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String OrderCode) {
        this.OrderCode = OrderCode;
    }

    public String getCmtyCode() {
        return CmtyCode;
    }

    public void setCmtyCode(String CmtyCode) {
        this.CmtyCode = CmtyCode;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getBusCode() {
        return BusCode;
    }

    public void setBusCode(String BusCode) {
        this.BusCode = BusCode;
    }

    public String getReturnTheReason() {
        return ReturnTheReason;
    }

    public void setReturnTheReason(String ReturnTheReason) {
        this.ReturnTheReason = ReturnTheReason;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String Path) {
        this.Path = Path;
    }
}

package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/8/3.
 */
public class shop_Order {

    /**
     * Code : 1
     * OrderCode : 201608031254367089
     * JumpToPay : null
     * GoodsInventoryShortage : null
     */

    private int Code;
    private String OrderCode;
    private Object JumpToPay;
    private Object GoodsInventoryShortage;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String OrderCode) {
        this.OrderCode = OrderCode;
    }

    public Object getJumpToPay() {
        return JumpToPay;
    }

    public void setJumpToPay(Object JumpToPay) {
        this.JumpToPay = JumpToPay;
    }

    public Object getGoodsInventoryShortage() {
        return GoodsInventoryShortage;
    }

    public void setGoodsInventoryShortage(Object GoodsInventoryShortage) {
        this.GoodsInventoryShortage = GoodsInventoryShortage;
    }
}

package com.proton.bystone.bean;

/**
 *  订单状态的具体状态
 * Created by MasterFan on 2016/7/23.
 */
public class OrderStateInfo {

    /**
     * CreateTime : 2016-07-23 16:53:58
     * BookStaus : 0
     */

    private String CreateTime;
    private int BookStaus;

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getBookStaus() {
        return BookStaus;
    }

    public void setBookStaus(int BookStaus) {
        this.BookStaus = BookStaus;
    }
}

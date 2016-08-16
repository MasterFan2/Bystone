package com.proton.bystone.bean;

/**
 * 保养单状态
 * Created by MasterFan on 2016/8/11.
 */
public class Status {

    /**
     * CreateTime : 2016-08-11 15:31:21
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

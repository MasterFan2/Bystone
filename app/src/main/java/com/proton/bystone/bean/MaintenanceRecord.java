package com.proton.bystone.bean;

/**
 * 保养记录
 * Created by MasterFan on 2016/7/29.
 */
public class MaintenanceRecord {

    /**
     * BookCode : 201607051616481425
     * OConsumerName : 2016年月日上门保养
     * CreateTime : 2016-07-07 14:16:34
     */

    private String BookCode;
    private String OConsumerName;
    private String CreateTime;

    public String getBookCode() {
        return BookCode;
    }

    public void setBookCode(String BookCode) {
        this.BookCode = BookCode;
    }

    public String getOConsumerName() {
        return OConsumerName;
    }

    public void setOConsumerName(String OConsumerName) {
        this.OConsumerName = OConsumerName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
}

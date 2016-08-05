package com.proton.bystone.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/8/4.
 * 支付参数
 */
public class shop_pay_cs {


    /**
     * appid : wx80cb910cfa56f7a6
     * partnerid : 1311262101
     * prepayid : wx2016080413391645f5fff5190262107773
     * package : Sign=WXPay
     * noncestr : mRXwomQQSag6RMye
     * timestamp : 1470289158
     * sign : 4A45240FB39C17BCFCE73FA12883BFE8
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    @SerializedName("package")
    private String packageX;
    private String noncestr;
    private int timestamp;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

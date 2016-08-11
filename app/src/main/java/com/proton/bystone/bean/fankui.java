package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/8/10.
 */
public class fankui {
    private  String UserCode;
    private  String OpinionsContent;
    private  int MobileType;

    public fankui(String userCode, String opinionsContent, int mobileType) {
        UserCode = userCode;
        OpinionsContent = opinionsContent;
        MobileType = mobileType;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getOpinionsContent() {
        return OpinionsContent;
    }

    public void setOpinionsContent(String opinionsContent) {
        OpinionsContent = opinionsContent;
    }

    public int getMobileType() {
        return MobileType;
    }

    public void setMobileType(int mobileType) {
        MobileType = mobileType;
    }
}

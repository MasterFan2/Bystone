package com.proton.bystone.bean;

/**
 * 广告
 * Created by MasterFan on 2016/7/15.
 */
public class Advert {

    /**
     * AdvTitle : 首页广告一
     * AdvPath : /Uplaod/Attachment/20160628/20160628204524742.Jpeg
     * Advcode : 201605271811431435
     * AdvType : adbusiness
     */

    private String AdvTitle;
    private String AdvPath;
    private String Advcode;
    private String AdvType;

    public String getAdvTitle() {
        return AdvTitle;
    }

    public void setAdvTitle(String AdvTitle) {
        this.AdvTitle = AdvTitle;
    }

    public String getAdvPath() {
        return AdvPath;
    }

    public void setAdvPath(String AdvPath) {
        this.AdvPath = AdvPath;
    }

    public String getAdvcode() {
        return Advcode;
    }

    public void setAdvcode(String Advcode) {
        this.Advcode = Advcode;
    }

    public String getAdvType() {
        return AdvType;
    }

    public void setAdvType(String AdvType) {
        this.AdvType = AdvType;
    }

    public Advert(String advTitle, String advPath, String advcode, String advType) {
        AdvTitle = advTitle;
        AdvPath = advPath;
        Advcode = advcode;
        AdvType = advType;
    }
}

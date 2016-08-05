package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/8/3.
 */
public class UploadResp {

    /**
     * originalUrl : D:\IIS\WebPcOrem\Uplaod\Attachment\/default/20160803/default/20160803\20160803153202_7227.jpg
     * name : 上传成功
     * result : 1
     */

    private String originalUrl;
    private String name;
    private int result;

    public UploadResp(){}

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}

package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/7/30.
 */
public class Version {

    /**
     * Id : 0
     * filePath : /Uplaod/Attachment/APPAPK/20160728/20160728142248904.apk
     * version : 1.1
     * apkName : null
     * downloadURL : null
     * Status : 0
     * ApplicationType : 0
     * WhetherToForce : 1
     */

    private int Id;
    private String filePath;
    private String version;
    private String apkName;
    private String downloadURL;
    private int Status;
    private int ApplicationType;
    private int WhetherToForce;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public int getApplicationType() {
        return ApplicationType;
    }

    public void setApplicationType(int ApplicationType) {
        this.ApplicationType = ApplicationType;
    }

    public int getWhetherToForce() {
        return WhetherToForce;
    }

    public void setWhetherToForce(int WhetherToForce) {
        this.WhetherToForce = WhetherToForce;
    }
}

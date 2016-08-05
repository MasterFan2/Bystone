package com.proton.bystone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MasterFan on 2016/7/30.
 */
public class Version implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.filePath);
        dest.writeString(this.version);
        dest.writeString(this.apkName);
        dest.writeString(this.downloadURL);
        dest.writeInt(this.Status);
        dest.writeInt(this.ApplicationType);
        dest.writeInt(this.WhetherToForce);
    }

    public Version() {
    }

    protected Version(Parcel in) {
        this.Id = in.readInt();
        this.filePath = in.readString();
        this.version = in.readString();
        this.apkName = in.readString();
        this.downloadURL = in.readString();
        this.Status = in.readInt();
        this.ApplicationType = in.readInt();
        this.WhetherToForce = in.readInt();
    }

    public static final Parcelable.Creator<Version> CREATOR = new Parcelable.Creator<Version>() {
        @Override
        public Version createFromParcel(Parcel source) {
            return new Version(source);
        }

        @Override
        public Version[] newArray(int size) {
            return new Version[size];
        }
    };
}

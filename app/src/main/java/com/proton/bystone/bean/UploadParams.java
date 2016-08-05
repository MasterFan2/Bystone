package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/8/3.
 */
public class UploadParams {
    private String Extension;
    private String FileBusinessType;

    public UploadParams() {
    }

    public UploadParams(String extension, String fileBusinessType) {

        Extension = extension;
        FileBusinessType = fileBusinessType;
    }

    public String getExtension() {

        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }

    public String getFileBusinessType() {
        return FileBusinessType;
    }

    public void setFileBusinessType(String fileBusinessType) {
        FileBusinessType = fileBusinessType;
    }
}

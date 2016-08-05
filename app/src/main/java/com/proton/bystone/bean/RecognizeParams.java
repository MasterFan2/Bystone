package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/8/2.
 */
public class RecognizeParams {
    private String key;
    private String secret;
    private int typeId;
    private String format;

    public RecognizeParams() {}

    public RecognizeParams(String key, String secret, int typeId, String format) {

        this.key = key;
        this.secret = secret;
        this.typeId = typeId;
        this.format = format;
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}

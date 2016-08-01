package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/7/15.
 */
public class BaseResp {

    private int code;
    private String content;
    private String data;

    public BaseResp() {
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BaseResp(int code, String content, String data) {

        this.code = code;
        this.content = content;
        this.data = data;
    }
}

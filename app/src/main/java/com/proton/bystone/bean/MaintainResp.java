package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/7/14.
 */
public class MaintainResp {

    private int code;
    private String content;
    private String data;

    public MaintainResp() {}

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

    @Override
    public String toString() {
        return "MaintainResp{" +
                "code=" + code +
                ", content='" + content + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}

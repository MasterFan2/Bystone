package com.proton.bystone.bean;

import java.util.ArrayList;

/**
 * Created by MasterFan on 2016/7/14.
 */
public class MaintainReq {

    private String key;
    private String methodName;
    private String para;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }
}

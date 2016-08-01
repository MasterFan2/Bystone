package com.proton.bystone.bean;

/**
 * 参数基类，所有参数都以这种形式
 * Created by MasterFan on 2016/7/15.
 */
public class Base {

    private String key;
    private String methodName;
    private String para;

    public Base(){}

    public Base(String key, String methodName, String para) {
        this.key = key;
        this.methodName = methodName;
        this.para = para;
    }

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

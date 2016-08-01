package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/7/26.
 */
public class ComboKey {
    private String Type_CODE;
    private String Ps_NAME;
    private int GongShiFei;
    private boolean disable;

    public ComboKey() {
    }


    public String getType_CODE() {
        return Type_CODE;
    }

    public void setType_CODE(String type_CODE) {
        Type_CODE = type_CODE;
    }

    public String getPs_NAME() {
        return Ps_NAME;
    }

    public void setPs_NAME(String ps_NAME) {
        Ps_NAME = ps_NAME;
    }

    public int getGongShiFei() {
        return GongShiFei;
    }

    public void setGongShiFei(int gongShiFei) {
        GongShiFei = gongShiFei;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public ComboKey(String type_CODE, String ps_NAME, int gongShiFei, boolean disable) {

        Type_CODE = type_CODE;
        Ps_NAME = ps_NAME;
        GongShiFei = gongShiFei;
        this.disable = disable;
    }
}

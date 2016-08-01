package com.proton.bystone.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/18.
 * 商品分类
 */
public class Category_nameResp {

    private String Ps_CODE;
    private String Ps_NAME;
    private String Ps_Parent;
    private String Img_Pic;

    public String getPs_CODE() {
        return Ps_CODE;
    }

    public void setPs_CODE(String ps_CODE) {
        Ps_CODE = ps_CODE;
    }

    public String getPs_NAME() {
        return Ps_NAME;
    }

    public void setPs_NAME(String ps_NAME) {
        Ps_NAME = ps_NAME;
    }

    public String getPs_Parent() {
        return Ps_Parent;
    }

    public void setPs_Parent(String ps_Parent) {
        Ps_Parent = ps_Parent;
    }

    public String getImg_Pic() {
        return Img_Pic;
    }

    public void setImg_Pic(String img_Pic) {
        Img_Pic = img_Pic;
    }
}
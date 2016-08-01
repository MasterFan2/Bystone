package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/7/24.
 */
public class TestChildBean {
    private String name;
    private String price;
    private boolean checked;
    private boolean required;

    public TestChildBean(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

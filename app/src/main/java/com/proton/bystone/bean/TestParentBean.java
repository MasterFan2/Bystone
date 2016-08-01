package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/7/24.
 */
public class TestParentBean {

    private String name;
    private boolean checked;

    public TestParentBean(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

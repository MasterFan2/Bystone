package com.proton.bystone.bean;

import java.util.List;

/**
 * 维保首页Bean
 * Created by MasterFan on 2016/7/29.
 */
public class CarComboMaintenance {
    private String name;
    private String code;
    private int    isDefault;
    private CarInfo carInfo;
    private List<CarCombo> combos;

    public CarComboMaintenance(String name, String code, int isDefault, List<CarCombo> combos) {
        this.name = name;
        this.code = code;
        this.isDefault = isDefault;
        this.combos = combos;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CarCombo> getCombos() {
        return combos;
    }

    public void setCombos(List<CarCombo> combos) {
        this.combos = combos;
    }
}

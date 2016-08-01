package com.proton.bystone.bean;

import java.util.ArrayList;

/**
 * 自组装套餐数据
 * Created by MasterFan on 2016/7/26.
 */
public class MCombo {
    private String name;
    private ArrayList<CarCombo> carCombos;

    public MCombo(String name, ArrayList<CarCombo> carCombos) {
        this.name = name;
        this.carCombos = carCombos;
    }

    public MCombo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CarCombo> getCarCombos() {
        return carCombos;
    }

    public void setCarCombos(ArrayList<CarCombo> carCombos) {
        this.carCombos = carCombos;
    }
}

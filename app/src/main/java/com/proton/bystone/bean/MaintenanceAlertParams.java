package com.proton.bystone.bean;

/**
 * 开启保养提醒Params
 * Created by MasterFan on 2016/7/29.
 */
public class MaintenanceAlertParams {

    /**
     * ID : 车辆ID
     * LastMaintenanceMileage : 上次保养里程
     * LastMaintenanceTime : 上次保养时间
     * AnnualMileage : 年里程
     * N_BeginMile : 总里程
     */

    private int ID;
    private int LastMaintenanceMileage;
    private String LastMaintenanceTime;
    private int AnnualMileage;
    private int N_BeginMile;

    public MaintenanceAlertParams(int ID, int lastMaintenanceMileage, String lastMaintenanceTime, int annualMileage, int n_BeginMile) {
        this.ID = ID;
        LastMaintenanceMileage = lastMaintenanceMileage;
        LastMaintenanceTime = lastMaintenanceTime;
        AnnualMileage = annualMileage;
        N_BeginMile = n_BeginMile;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getLastMaintenanceMileage() {
        return LastMaintenanceMileage;
    }

    public void setLastMaintenanceMileage(int LastMaintenanceMileage) {
        this.LastMaintenanceMileage = LastMaintenanceMileage;
    }

    public String getLastMaintenanceTime() {
        return LastMaintenanceTime;
    }

    public void setLastMaintenanceTime(String LastMaintenanceTime) {
        this.LastMaintenanceTime = LastMaintenanceTime;
    }

    public int getAnnualMileage() {
        return AnnualMileage;
    }

    public void setAnnualMileage(int AnnualMileage) {
        this.AnnualMileage = AnnualMileage;
    }

    public int getN_BeginMile() {
        return N_BeginMile;
    }

    public void setN_BeginMile(int N_BeginMile) {
        this.N_BeginMile = N_BeginMile;
    }
}

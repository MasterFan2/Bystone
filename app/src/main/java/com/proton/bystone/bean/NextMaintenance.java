package com.proton.bystone.bean;

/**
 * 下次保养的时间和里程
 * Created by MasterFan on 2016/7/29.
 */
public class NextMaintenance {

    /**
     * NextServiceMileage : 下次保养里程
     * SaveMaintenanceTime : 下次保养时间
     */

    private String NextServiceMileage;
    private String SaveMaintenanceTime;

    public String getNextServiceMileage() {
        return NextServiceMileage;
    }

    public void setNextServiceMileage(String NextServiceMileage) {
        this.NextServiceMileage = NextServiceMileage;
    }

    public String getSaveMaintenanceTime() {
        return SaveMaintenanceTime;
    }

    public void setSaveMaintenanceTime(String SaveMaintenanceTime) {
        this.SaveMaintenanceTime = SaveMaintenanceTime;
    }
}

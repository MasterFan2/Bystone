package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/7/28.
 */
public class UpdateMyCarParams {

    /**
     * ID : 车辆ID
     * User_Code : 会员编号
     * I_CarDetail : 车型编号
     * VC_CarNO : 车牌
     * VC_CarFDJ : 发动机号
     * IsDefault : 是否默认为爱车(1是，0：否)
     * CarIdentifier : 车辆识别号
     */

    private int ID;
    private String User_Code;
    private String I_CarDetail;
    private String VC_CarNO;
    private String VC_CarFDJ;
    private String IsDefault;
    private String CarIdentifier;

    public UpdateMyCarParams(int ID, String user_Code, String i_CarDetail, String VC_CarNO, String VC_CarFDJ, String isDefault, String carIdentifier) {
        this.ID = ID;
        User_Code = user_Code;
        I_CarDetail = i_CarDetail;
        this.VC_CarNO = VC_CarNO;
        this.VC_CarFDJ = VC_CarFDJ;
        IsDefault = isDefault;
        CarIdentifier = carIdentifier;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUser_Code() {
        return User_Code;
    }

    public void setUser_Code(String User_Code) {
        this.User_Code = User_Code;
    }

    public String getI_CarDetail() {
        return I_CarDetail;
    }

    public void setI_CarDetail(String I_CarDetail) {
        this.I_CarDetail = I_CarDetail;
    }

    public String getVC_CarNO() {
        return VC_CarNO;
    }

    public void setVC_CarNO(String VC_CarNO) {
        this.VC_CarNO = VC_CarNO;
    }

    public String getVC_CarFDJ() {
        return VC_CarFDJ;
    }

    public void setVC_CarFDJ(String VC_CarFDJ) {
        this.VC_CarFDJ = VC_CarFDJ;
    }

    public String getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(String IsDefault) {
        this.IsDefault = IsDefault;
    }

    public String getCarIdentifier() {
        return CarIdentifier;
    }

    public void setCarIdentifier(String CarIdentifier) {
        this.CarIdentifier = CarIdentifier;
    }
}

package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/8/3.
 */
public class DrivingLicense {

    /**
     * UserCode : 会员编号
     * VC_CarNO : 车牌号
     * VehicleType : 车辆类型
     * VC_UseCar : 所有人
     * FixedAddress : 地址
     * NatureOfUse : 适用性质
     * BrandModelNumber : 品牌型号
     * CarIdentifier : 车辆识别代码
     * VC_CarFDJ : 发动机号
     * DrivingLicenseTime : 注册日期
     * DateOfIssue : 发证日期
     */

    private String UserCode;
    private String VC_CarNO;
    private String VehicleType;
    private String VC_UseCar;
    private String FixedAddress;
    private String NatureOfUse;
    private String BrandModelNumber;
    private String CarIdentifier;
    private String VC_CarFDJ;
    private String DrivingLicenseTime;
    private String DateOfIssue;

    public DrivingLicense(String userCode, String VC_CarNO, String vehicleType, String VC_UseCar, String fixedAddress, String natureOfUse, String brandModelNumber, String carIdentifier, String VC_CarFDJ, String drivingLicenseTime, String dateOfIssue) {
        UserCode = userCode;
        this.VC_CarNO = VC_CarNO;
        VehicleType = vehicleType;
        this.VC_UseCar = VC_UseCar;
        FixedAddress = fixedAddress;
        NatureOfUse = natureOfUse;
        BrandModelNumber = brandModelNumber;
        CarIdentifier = carIdentifier;
        this.VC_CarFDJ = VC_CarFDJ;
        DrivingLicenseTime = drivingLicenseTime;
        DateOfIssue = dateOfIssue;
    }

    public DrivingLicense() {}

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getVC_CarNO() {
        return VC_CarNO;
    }

    public void setVC_CarNO(String VC_CarNO) {
        this.VC_CarNO = VC_CarNO;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String VehicleType) {
        this.VehicleType = VehicleType;
    }

    public String getVC_UseCar() {
        return VC_UseCar;
    }

    public void setVC_UseCar(String VC_UseCar) {
        this.VC_UseCar = VC_UseCar;
    }

    public String getFixedAddress() {
        return FixedAddress;
    }

    public void setFixedAddress(String FixedAddress) {
        this.FixedAddress = FixedAddress;
    }

    public String getNatureOfUse() {
        return NatureOfUse;
    }

    public void setNatureOfUse(String NatureOfUse) {
        this.NatureOfUse = NatureOfUse;
    }

    public String getBrandModelNumber() {
        return BrandModelNumber;
    }

    public void setBrandModelNumber(String BrandModelNumber) {
        this.BrandModelNumber = BrandModelNumber;
    }

    public String getCarIdentifier() {
        return CarIdentifier;
    }

    public void setCarIdentifier(String CarIdentifier) {
        this.CarIdentifier = CarIdentifier;
    }

    public String getVC_CarFDJ() {
        return VC_CarFDJ;
    }

    public void setVC_CarFDJ(String VC_CarFDJ) {
        this.VC_CarFDJ = VC_CarFDJ;
    }

    public String getDrivingLicenseTime() {
        return DrivingLicenseTime;
    }

    public void setDrivingLicenseTime(String DrivingLicenseTime) {
        this.DrivingLicenseTime = DrivingLicenseTime;
    }

    public String getDateOfIssue() {
        return DateOfIssue;
    }

    public void setDateOfIssue(String DateOfIssue) {
        this.DateOfIssue = DateOfIssue;
    }
}

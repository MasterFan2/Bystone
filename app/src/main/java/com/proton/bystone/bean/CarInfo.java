package com.proton.bystone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 车辆Bean
 * Created by MasterFan on 2016/7/28.
 */
public class CarInfo implements Parcelable {

    private int ID;
    private String User_Code;
    private String I_CarDetail;
    private String VC_CarNO;//车牌号
    private String VC_CarJiaNo;//车架号
    private String VC_CarFDJ;
    private String N_BeginMile;
    private String VC_UseCar;
    private String VC_UseUnit;//使用单位
    private String VC_Tel;
    private String CardNo;//
    private String Email;
    private String FixedAddress;//联系地址
    private int IsDefault;
    private String CarIdentifier;//车辆识别号
    private String DrivingLicenseTime;
    private String VehicleType;
    private String NatureOfUse;
    private String BrandModelNumber;
    private String LastMaintenanceTime;
    private String LastMaintenanceMileage;
    private String AnnualMileage;
    private String DateOfIssue;
    private String M_Model;//车型
    private String B_Name;//车辆品牌
    private String LatestMileage;
    private String M_Year;
    private String BookEndTime;
    private String B_logo;
    private int    MaintenanceMileage;

    public int getMaintenanceMileage() {
        return MaintenanceMileage;
    }

    public void setMaintenanceMileage(int maintenanceMileage) {
        MaintenanceMileage = maintenanceMileage;
    }

    private int Countdown; //倒计时天数
    private int OpenRemind;//1:开启保养提醒         0：没启用


    public int getCountdown() {
        return Countdown;
    }

    public void setCountdown(int countdown) {
        Countdown = countdown;
    }

    public int getOpenRemind() {
        return OpenRemind;
    }

    public void setOpenRemind(int openRemind) {
        OpenRemind = openRemind;
    }

    private boolean checked;

    public String getB_logo() {
        return B_logo;
    }

    public void setB_logo(String b_logo) {
        B_logo = b_logo;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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

    public String getVC_CarJiaNo() {
        return VC_CarJiaNo;
    }

    public void setVC_CarJiaNo(String VC_CarJiaNo) {
        this.VC_CarJiaNo = VC_CarJiaNo;
    }

    public String getVC_CarFDJ() {
        return VC_CarFDJ;
    }

    public void setVC_CarFDJ(String VC_CarFDJ) {
        this.VC_CarFDJ = VC_CarFDJ;
    }

    public String getN_BeginMile() {
        return N_BeginMile;
    }

    public void setN_BeginMile(String N_BeginMile) {
        this.N_BeginMile = N_BeginMile;
    }

    public String getVC_UseCar() {
        return VC_UseCar;
    }

    public void setVC_UseCar(String VC_UseCar) {
        this.VC_UseCar = VC_UseCar;
    }

    public String getVC_UseUnit() {
        return VC_UseUnit;
    }

    public void setVC_UseUnit(String VC_UseUnit) {
        this.VC_UseUnit = VC_UseUnit;
    }

    public String getVC_Tel() {
        return VC_Tel;
    }

    public void setVC_Tel(String VC_Tel) {
        this.VC_Tel = VC_Tel;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String CardNo) {
        this.CardNo = CardNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getFixedAddress() {
        return FixedAddress;
    }

    public void setFixedAddress(String FixedAddress) {
        this.FixedAddress = FixedAddress;
    }

    public int getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(int IsDefault) {
        this.IsDefault = IsDefault;
    }

    public String getCarIdentifier() {
        return CarIdentifier;
    }

    public void setCarIdentifier(String CarIdentifier) {
        this.CarIdentifier = CarIdentifier;
    }

    public String getDrivingLicenseTime() {
        return DrivingLicenseTime;
    }

    public void setDrivingLicenseTime(String DrivingLicenseTime) {
        this.DrivingLicenseTime = DrivingLicenseTime;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String VehicleType) {
        this.VehicleType = VehicleType;
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

    public String getLastMaintenanceTime() {
        return LastMaintenanceTime;
    }

    public void setLastMaintenanceTime(String LastMaintenanceTime) {
        this.LastMaintenanceTime = LastMaintenanceTime;
    }

    public String getLastMaintenanceMileage() {
        return LastMaintenanceMileage;
    }

    public void setLastMaintenanceMileage(String LastMaintenanceMileage) {
        this.LastMaintenanceMileage = LastMaintenanceMileage;
    }

    public String getAnnualMileage() {
        return AnnualMileage;
    }

    public void setAnnualMileage(String AnnualMileage) {
        this.AnnualMileage = AnnualMileage;
    }

    public String getDateOfIssue() {
        return DateOfIssue;
    }

    public void setDateOfIssue(String DateOfIssue) {
        this.DateOfIssue = DateOfIssue;
    }

    public String getM_Model() {
        return M_Model;
    }

    public void setM_Model(String M_Model) {
        this.M_Model = M_Model;
    }

    public String getB_Name() {
        return B_Name;
    }

    public void setB_Name(String B_Name) {
        this.B_Name = B_Name;
    }

    public String getLatestMileage() {
        return LatestMileage;
    }

    public void setLatestMileage(String LatestMileage) {
        this.LatestMileage = LatestMileage;
    }

    public String getM_Year() {
        return M_Year;
    }

    public void setM_Year(String M_Year) {
        this.M_Year = M_Year;
    }

    public String getBookEndTime() {
        return BookEndTime;
    }

    public void setBookEndTime(String BookEndTime) {
        this.BookEndTime = BookEndTime;
    }

    public CarInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.User_Code);
        dest.writeString(this.I_CarDetail);
        dest.writeString(this.VC_CarNO);
        dest.writeString(this.VC_CarJiaNo);
        dest.writeString(this.VC_CarFDJ);
        dest.writeString(this.N_BeginMile);
        dest.writeString(this.VC_UseCar);
        dest.writeString(this.VC_UseUnit);
        dest.writeString(this.VC_Tel);
        dest.writeString(this.CardNo);
        dest.writeString(this.Email);
        dest.writeString(this.FixedAddress);
        dest.writeInt(this.IsDefault);
        dest.writeString(this.CarIdentifier);
        dest.writeString(this.DrivingLicenseTime);
        dest.writeString(this.VehicleType);
        dest.writeString(this.NatureOfUse);
        dest.writeString(this.BrandModelNumber);
        dest.writeString(this.LastMaintenanceTime);
        dest.writeString(this.LastMaintenanceMileage);
        dest.writeString(this.AnnualMileage);
        dest.writeString(this.DateOfIssue);
        dest.writeString(this.M_Model);
        dest.writeString(this.B_Name);
        dest.writeString(this.LatestMileage);
        dest.writeString(this.M_Year);
        dest.writeString(this.BookEndTime);
        dest.writeString(this.B_logo);
        dest.writeInt(this.MaintenanceMileage);
        dest.writeInt(this.Countdown);
        dest.writeInt(this.OpenRemind);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected CarInfo(Parcel in) {
        this.ID = in.readInt();
        this.User_Code = in.readString();
        this.I_CarDetail = in.readString();
        this.VC_CarNO = in.readString();
        this.VC_CarJiaNo = in.readString();
        this.VC_CarFDJ = in.readString();
        this.N_BeginMile = in.readString();
        this.VC_UseCar = in.readString();
        this.VC_UseUnit = in.readString();
        this.VC_Tel = in.readString();
        this.CardNo = in.readString();
        this.Email = in.readString();
        this.FixedAddress = in.readString();
        this.IsDefault = in.readInt();
        this.CarIdentifier = in.readString();
        this.DrivingLicenseTime = in.readString();
        this.VehicleType = in.readString();
        this.NatureOfUse = in.readString();
        this.BrandModelNumber = in.readString();
        this.LastMaintenanceTime = in.readString();
        this.LastMaintenanceMileage = in.readString();
        this.AnnualMileage = in.readString();
        this.DateOfIssue = in.readString();
        this.M_Model = in.readString();
        this.B_Name = in.readString();
        this.LatestMileage = in.readString();
        this.M_Year = in.readString();
        this.BookEndTime = in.readString();
        this.B_logo = in.readString();
        this.MaintenanceMileage = in.readInt();
        this.Countdown = in.readInt();
        this.OpenRemind = in.readInt();
        this.checked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CarInfo> CREATOR = new Parcelable.Creator<CarInfo>() {
        @Override
        public CarInfo createFromParcel(Parcel source) {
            return new CarInfo(source);
        }

        @Override
        public CarInfo[] newArray(int size) {
            return new CarInfo[size];
        }
    };
}

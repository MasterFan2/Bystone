package com.proton.bystone.bean;

/**
 * 预约上传参数
 * Created by MasterFan on 2016/7/23.
 */
public class ReservationParam {
    private String BookAreaCode;//预约区域编号
    private String Address;     //预约地址
    private String R_West;      //坐标经度
    private String R_East;      //坐标纬度
    private String IsNewAddress;//是否最新地址(1: 是，0否)
    private String MemberDescription;//会员车辆自行描述'
    private String Mb_Code;     //会员编码
    private String CarCode;     //车型编号
    private String BookUser;    //预约人 [登录账号]
    private String UserName;    //联系人

    //预约类型:
    // 1：预约上门保养,
    // 2：预约到店保养,
    // 3：非预约到店保养
    private String BookType;
    private String Mobile;          //联系电话
    private String BookCarNum;      //车牌号
    private String BookBeginTime;   //预约起始时间
    private String BookEndTime;     //预约结束时间

    public ReservationParam(String bookAreaCode, String address, String r_West, String r_East, String isNewAddress, String memberDescription, String mb_Code, String carCode, String bookUser, String userName, String bookType, String mobile, String bookCarNum, String bookBeginTime, String bookEndTime) {
        BookAreaCode = bookAreaCode;
        Address = address;
        R_West = r_West;
        R_East = r_East;
        IsNewAddress = isNewAddress;
        MemberDescription = memberDescription;
        Mb_Code = mb_Code;
        CarCode = carCode;
        BookUser = bookUser;
        UserName = userName;
        BookType = bookType;
        Mobile = mobile;
        BookCarNum = bookCarNum;
        BookBeginTime = bookBeginTime;
        BookEndTime = bookEndTime;
    }

    public String getBookAreaCode() {
        return BookAreaCode;
    }

    public void setBookAreaCode(String bookAreaCode) {
        BookAreaCode = bookAreaCode;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getR_West() {
        return R_West;
    }

    public void setR_West(String r_West) {
        R_West = r_West;
    }

    public String getR_East() {
        return R_East;
    }

    public void setR_East(String r_East) {
        R_East = r_East;
    }

    public String getIsNewAddress() {
        return IsNewAddress;
    }

    public void setIsNewAddress(String isNewAddress) {
        IsNewAddress = isNewAddress;
    }

    public String getMemberDescription() {
        return MemberDescription;
    }

    public void setMemberDescription(String memberDescription) {
        MemberDescription = memberDescription;
    }

    public String getMb_Code() {
        return Mb_Code;
    }

    public void setMb_Code(String mb_Code) {
        Mb_Code = mb_Code;
    }

    public String getCarCode() {
        return CarCode;
    }

    public void setCarCode(String carCode) {
        CarCode = carCode;
    }

    public String getBookUser() {
        return BookUser;
    }

    public void setBookUser(String bookUser) {
        BookUser = bookUser;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getBookType() {
        return BookType;
    }

    public void setBookType(String bookType) {
        BookType = bookType;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getBookCarNum() {
        return BookCarNum;
    }

    public void setBookCarNum(String bookCarNum) {
        BookCarNum = bookCarNum;
    }

    public String getBookBeginTime() {
        return BookBeginTime;
    }

    public void setBookBeginTime(String bookBeginTime) {
        BookBeginTime = bookBeginTime;
    }

    public String getBookEndTime() {
        return BookEndTime;
    }

    public void setBookEndTime(String bookEndTime) {
        BookEndTime = bookEndTime;
    }
}

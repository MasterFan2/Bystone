package com.proton.bystone.bean;

/**
 * 检测单确认
 * Created by MasterFan on 2016/8/12.
 */
public class TestListParams {
    private String BookCode;    //保养记录编号
    private String UserCode;    //会员编号private String  ,
    private String Type_CODE;   //保养项编号
    private String Pt_Code;     //常规保养材料编号
    private String I_ProCode;   //检查项材料编号
    private String MaterialType;//服务类型（1：常规保养，2：其它检测项）
    private String MaterialPrice;//材料最低价格
    private String HourlyWage;  //工时费
    private String PtName;      //材料名称

    public TestListParams(String bookCode, String userCode, String type_CODE, String pt_Code, String i_ProCode, String materialType, String materialPrice, String hourlyWage, String ptName, String activityPrice) {
        BookCode = bookCode;
        UserCode = userCode;
        Type_CODE = type_CODE;
        Pt_Code = pt_Code;
        I_ProCode = i_ProCode;
        MaterialType = materialType;
        MaterialPrice = materialPrice;
        HourlyWage = hourlyWage;
        PtName = ptName;
        ActivityPrice = activityPrice;
    }

    public TestListParams() {}

    public String getBookCode() {

        return BookCode;
    }

    public void setBookCode(String bookCode) {
        BookCode = bookCode;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getType_CODE() {
        return Type_CODE;
    }

    public void setType_CODE(String type_CODE) {
        Type_CODE = type_CODE;
    }

    public String getPt_Code() {
        return Pt_Code;
    }

    public void setPt_Code(String pt_Code) {
        Pt_Code = pt_Code;
    }

    public String getI_ProCode() {
        return I_ProCode;
    }

    public void setI_ProCode(String i_ProCode) {
        I_ProCode = i_ProCode;
    }

    public String getMaterialType() {
        return MaterialType;
    }

    public void setMaterialType(String materialType) {
        MaterialType = materialType;
    }

    public String getMaterialPrice() {
        return MaterialPrice;
    }

    public void setMaterialPrice(String materialPrice) {
        MaterialPrice = materialPrice;
    }

    public String getHourlyWage() {
        return HourlyWage;
    }

    public void setHourlyWage(String hourlyWage) {
        HourlyWage = hourlyWage;
    }

    public String getPtName() {
        return PtName;
    }

    public void setPtName(String ptName) {
        PtName = ptName;
    }

    public String getActivityPrice() {
        return ActivityPrice;
    }

    public void setActivityPrice(String activityPrice) {
        ActivityPrice = activityPrice;
    }

    private String ActivityPrice;//活动优惠金额
}

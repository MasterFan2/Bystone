package com.proton.bystone.bean;

/**
 * 服务详情Data Model
 * Created by MasterFan on 2016/8/16.
 */
public class ServiceDetail {

    /**
     * OCode : 201608102019094535
     * Ps_NAME : 机油滤清器
     * UserCode : 201605261656057265
     * Type_CODE : 201607011037086328
     * I_ProCode :
     * BookCode : 201608091423285485
     * MaterialPrice : 300.00
     * HourlyWage : 50
     * MaterialType : 1
     * VC_Name : 车事通机油滤芯SN-4300247
     * Pt_Barcode : 出厂编号XXXY-112
     * Pt_Code : 2016052317205144747
     * EventTitle :
     * RelatedCode : 201608091423285486
     * Sys_UserCode : 201607061700180571
     * PadctBrief : 让你的爱车每年少做一次保养
     * VC_Url : /Uplaod/Attachment/Product/20160729/20160729194738504.png
     */

    private String OCode;
    private String Ps_NAME;
    private String UserCode;
    private String Type_CODE;
    private String I_ProCode;
    private String BookCode;
    private String MaterialPrice;
    private int HourlyWage;
    private int MaterialType;
    private String VC_Name;
    private String Pt_Barcode;
    private String Pt_Code;
    private String EventTitle;
    private String RelatedCode;
    private String Sys_UserCode;
    private String PadctBrief;
    private String VC_Url;
    private int ActivityPrice;

    public int getActivityPrice() {
        return ActivityPrice;
    }

    public void setActivityPrice(int activityPrice) {
        ActivityPrice = activityPrice;
    }

    public String getOCode() {
        return OCode;
    }

    public void setOCode(String OCode) {
        this.OCode = OCode;
    }

    public String getPs_NAME() {
        return Ps_NAME;
    }

    public void setPs_NAME(String Ps_NAME) {
        this.Ps_NAME = Ps_NAME;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getType_CODE() {
        return Type_CODE;
    }

    public void setType_CODE(String Type_CODE) {
        this.Type_CODE = Type_CODE;
    }

    public String getI_ProCode() {
        return I_ProCode;
    }

    public void setI_ProCode(String I_ProCode) {
        this.I_ProCode = I_ProCode;
    }

    public String getBookCode() {
        return BookCode;
    }

    public void setBookCode(String BookCode) {
        this.BookCode = BookCode;
    }

    public String getMaterialPrice() {
        return MaterialPrice;
    }

    public void setMaterialPrice(String MaterialPrice) {
        this.MaterialPrice = MaterialPrice;
    }

    public int getHourlyWage() {
        return HourlyWage;
    }

    public void setHourlyWage(int HourlyWage) {
        this.HourlyWage = HourlyWage;
    }

    public int getMaterialType() {
        return MaterialType;
    }

    public void setMaterialType(int MaterialType) {
        this.MaterialType = MaterialType;
    }

    public String getVC_Name() {
        return VC_Name;
    }

    public void setVC_Name(String VC_Name) {
        this.VC_Name = VC_Name;
    }

    public String getPt_Barcode() {
        return Pt_Barcode;
    }

    public void setPt_Barcode(String Pt_Barcode) {
        this.Pt_Barcode = Pt_Barcode;
    }

    public String getPt_Code() {
        return Pt_Code;
    }

    public void setPt_Code(String Pt_Code) {
        this.Pt_Code = Pt_Code;
    }

    public String getEventTitle() {
        return EventTitle;
    }

    public void setEventTitle(String EventTitle) {
        this.EventTitle = EventTitle;
    }

    public String getRelatedCode() {
        return RelatedCode;
    }

    public void setRelatedCode(String RelatedCode) {
        this.RelatedCode = RelatedCode;
    }

    public String getSys_UserCode() {
        return Sys_UserCode;
    }

    public void setSys_UserCode(String Sys_UserCode) {
        this.Sys_UserCode = Sys_UserCode;
    }

    public String getPadctBrief() {
        return PadctBrief;
    }

    public void setPadctBrief(String PadctBrief) {
        this.PadctBrief = PadctBrief;
    }

    public String getVC_Url() {
        return VC_Url;
    }

    public void setVC_Url(String VC_Url) {
        this.VC_Url = VC_Url;
    }
}

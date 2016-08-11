package com.proton.bystone.bean;

import java.util.ArrayList;

/**
 * Created by MasterFan on 2016/7/19.
 */
public class CarCombo {


    private String Pt_Code;
    private String SeckilPrice;
    private int IsSeckill;
    private String VC_Rule;
    private String VC_PP;
    private String I_ProCode;
    private String Type_CODE;
    private String Ps_NAME;
    private String VC_Name;
    private String N_FHYJ;
    private String N_HYJ;
    private String VC_Note;
    private String I_CarDetail;
    private String VC_Url;
    private String VC_XH;
    private String VC_Params;
    private String PadctBrief;
    private ArrayList<CarCombo> Data;
    private int    GongShiFei;//工时费
    private boolean disable;//是否禁用
    private boolean checked;//是否选中
    private boolean canCanceled;

    public String getPt_Code() {
        return Pt_Code;
    }

    public void setPt_Code(String pt_Code) {
        Pt_Code = pt_Code;
    }

    public String getSeckilPrice() {
        return SeckilPrice;
    }

    public void setSeckilPrice(String seckilPrice) {
        SeckilPrice = seckilPrice;
    }

    public int getIsSeckill() {
        return IsSeckill;
    }

    public void setIsSeckill(int isSeckill) {
        IsSeckill = isSeckill;
    }

    public String getVC_Rule() {
        return VC_Rule;
    }

    public void setVC_Rule(String VC_Rule) {
        this.VC_Rule = VC_Rule;
    }

    public String getVC_PP() {
        return VC_PP;
    }

    public void setVC_PP(String VC_PP) {
        this.VC_PP = VC_PP;
    }

    public ArrayList<CarCombo> getData() {
        return Data;
    }

    public void setData(ArrayList<CarCombo> data) {
        Data = data;
    }

    public int getGongShiFei() {
        return GongShiFei;
    }

    public void setGongShiFei(int gongShiFei) {
        GongShiFei = gongShiFei;
    }

    public boolean isCanCanceled() {
        return canCanceled;
    }

    public void setCanCanceled(boolean canCanceled) {
        this.canCanceled = canCanceled;
    }

    public String getVC_Params() {
        return VC_Params;
    }

    public void setVC_Params(String VC_Params) {
        this.VC_Params = VC_Params;
    }

    public String getPadctBrief() {
        return PadctBrief;
    }

    public void setPadctBrief(String padctBrief) {
        PadctBrief = padctBrief;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getI_ProCode() {
        return I_ProCode;
    }

    public void setI_ProCode(String I_ProCode) {
        this.I_ProCode = I_ProCode;
    }

    public String getType_CODE() {
        return Type_CODE;
    }

    public void setType_CODE(String Type_CODE) {
        this.Type_CODE = Type_CODE;
    }

    public String getPs_NAME() {
        return Ps_NAME;
    }

    public void setPs_NAME(String Ps_NAME) {
        this.Ps_NAME = Ps_NAME;
    }

    public String getVC_Name() {
        return VC_Name;
    }

    public void setVC_Name(String VC_Name) {
        this.VC_Name = VC_Name;
    }

    public String getN_FHYJ() {
        return N_FHYJ;
    }

    public void setN_FHYJ(String N_FHYJ) {
        this.N_FHYJ = N_FHYJ;
    }

    public String getN_HYJ() {
        return N_HYJ;
    }

    public void setN_HYJ(String N_HYJ) {
        this.N_HYJ = N_HYJ;
    }

    public String getVC_Note() {
        return VC_Note;
    }

    public void setVC_Note(String VC_Note) {
        this.VC_Note = VC_Note;
    }

    public String getI_CarDetail() {
        return I_CarDetail;
    }

    public void setI_CarDetail(String I_CarDetail) {
        this.I_CarDetail = I_CarDetail;
    }

    public String getVC_Url() {
        return VC_Url;
    }

    public void setVC_Url(String VC_Url) {
        this.VC_Url = VC_Url;
    }

    public String getVC_XH() {
        return VC_XH;
    }

    public void setVC_XH(String VC_XH) {
        this.VC_XH = VC_XH;
    }
}

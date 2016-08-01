package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/7/26.
 */
public class Commodity {

    private String Type_CODE;
    private String I_Company;
    private String Packing;
    private int  SaleNum;
    private String  Pc_Code;
    private String  N_JXS;
    private String  VC_Params;
    private int Stock;
    private String  Ps_Name;
    private int PostagePrice;


    private String Title;
    private String N_HYJ;
    private String N_FHYJ;
    private String VC_Code;
    private String VC_Name;
    private String PadctBrief;
    private String VC_Rule;
    private String VC_XH;
    private String VC_PP;
    private String VC_Url;


    public Commodity(String type_CODE, String i_Company, String packing, int saleNum, String pc_Code, String n_JXS, String VC_Params, int stock, String ps_Name, int postagePrice, String title, String n_HYJ, String n_FHYJ, String VC_Code, String VC_Name, String padctBrief, String VC_Rule, String VC_XH, String VC_PP, String VC_Url, int count, boolean checked) {
        Type_CODE = type_CODE;
        I_Company = i_Company;
        Packing = packing;
        SaleNum = saleNum;
        Pc_Code = pc_Code;
        N_JXS = n_JXS;
        this.VC_Params = VC_Params;
        Stock = stock;
        Ps_Name = ps_Name;
        PostagePrice = postagePrice;
        Title = title;
        N_HYJ = n_HYJ;
        N_FHYJ = n_FHYJ;
        this.VC_Code = VC_Code;
        this.VC_Name = VC_Name;
        PadctBrief = padctBrief;
        this.VC_Rule = VC_Rule;
        this.VC_XH = VC_XH;
        this.VC_PP = VC_PP;
        this.VC_Url = VC_Url;
        this.count = count;
        this.checked = checked;
    }

    ///购物车用
    private int count;//数量，
    private boolean checked;//是否选中

    public String getType_CODE() {
        return Type_CODE;
    }

    public void setType_CODE(String type_CODE) {
        Type_CODE = type_CODE;
    }

    public String getI_Company() {
        return I_Company;
    }

    public void setI_Company(String i_Company) {
        I_Company = i_Company;
    }

    public String getPacking() {
        return Packing;
    }

    public void setPacking(String packing) {
        Packing = packing;
    }

    public int getSaleNum() {
        return SaleNum;
    }

    public void setSaleNum(int saleNum) {
        SaleNum = saleNum;
    }

    public String getPc_Code() {
        return Pc_Code;
    }

    public void setPc_Code(String pc_Code) {
        Pc_Code = pc_Code;
    }

    public String getN_JXS() {
        return N_JXS;
    }

    public void setN_JXS(String n_JXS) {
        N_JXS = n_JXS;
    }

    public String getVC_Params() {
        return VC_Params;
    }

    public void setVC_Params(String VC_Params) {
        this.VC_Params = VC_Params;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public String getPs_Name() {
        return Ps_Name;
    }

    public void setPs_Name(String ps_Name) {
        Ps_Name = ps_Name;
    }

    public int getPostagePrice() {
        return PostagePrice;
    }

    public void setPostagePrice(int postagePrice) {
        PostagePrice = postagePrice;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getN_HYJ() {
        return N_HYJ;
    }

    public void setN_HYJ(String n_HYJ) {
        N_HYJ = n_HYJ;
    }

    public String getN_FHYJ() {
        return N_FHYJ;
    }

    public void setN_FHYJ(String n_FHYJ) {
        N_FHYJ = n_FHYJ;
    }

    public String getVC_Code() {
        return VC_Code;
    }

    public void setVC_Code(String VC_Code) {
        this.VC_Code = VC_Code;
    }

    public String getVC_Name() {
        return VC_Name;
    }

    public void setVC_Name(String VC_Name) {
        this.VC_Name = VC_Name;
    }

    public String getPadctBrief() {
        return PadctBrief;
    }

    public void setPadctBrief(String padctBrief) {
        PadctBrief = padctBrief;
    }

    public String getVC_Rule() {
        return VC_Rule;
    }

    public void setVC_Rule(String VC_Rule) {
        this.VC_Rule = VC_Rule;
    }

    public String getVC_XH() {
        return VC_XH;
    }

    public void setVC_XH(String VC_XH) {
        this.VC_XH = VC_XH;
    }

    public String getVC_PP() {
        return VC_PP;
    }

    public void setVC_PP(String VC_PP) {
        this.VC_PP = VC_PP;
    }

    public String getVC_Url() {
        return VC_Url;
    }

    public void setVC_Url(String VC_Url) {
        this.VC_Url = VC_Url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment() {
        this.count = this.count++;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

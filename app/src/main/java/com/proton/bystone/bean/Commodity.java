package com.proton.bystone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MasterFan on 2016/7/26.
 */
public class Commodity implements Parcelable {

    private String Type_CODE;//商品分类编号
    public String I_Company;//商家编号
    private String Packing;//商品包装规格
    private int  SaleNum;//商品销量
    private String  Pc_Code;
    private String  N_JXS;
    private String  VC_Params;//商品简短介绍
    private int Stock;//库存
    private String  Ps_Name;
    private int PostagePrice;


    private String Title;
    private String N_HYJ;
    private String N_FHYJ;//原价
    private String VC_Code;//商品编号
    private String VC_Name;//商品名称
    private String PadctBrief;//商品简要
    private String VC_Rule;//商品容量
    private String VC_XH;
    private String VC_PP;
    private String VC_Url;

    ///购物车用
    private int     count;//数量，
    private boolean checked;//是否选中

    ///订单用
    private boolean canUseGoldCoin;//是否使用金币
    private String  remark;         //备注
    private int usedGoldCoin;       //使用的金币
    private int needGoldCoin;       //需要使用的金币 = 单价 * 数量 + 邮费;

    public int getNeedGoldCoin() {
        return needGoldCoin;
    }

    public void setNeedGoldCoin(int needGoldCoin) {
        this.needGoldCoin = needGoldCoin;
    }

    public int getUsedGoldCoin() {
        return usedGoldCoin;
    }

    public void setUsedGoldCoin(int usedGoldCoin) {
        this.usedGoldCoin = usedGoldCoin;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isCanUseGoldCoin() {
        return canUseGoldCoin;
    }

    public void setCanUseGoldCoin(boolean canUseGoldCoin) {
        this.canUseGoldCoin = canUseGoldCoin;
    }

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
        this.count = this.count + 1;
    }

    public void subtract() {
        this.count = this.count - 1;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Type_CODE);
        dest.writeString(this.I_Company);
        dest.writeString(this.Packing);
        dest.writeInt(this.SaleNum);
        dest.writeString(this.Pc_Code);
        dest.writeString(this.N_JXS);
        dest.writeString(this.VC_Params);
        dest.writeInt(this.Stock);
        dest.writeString(this.Ps_Name);
        dest.writeInt(this.PostagePrice);
        dest.writeString(this.Title);
        dest.writeString(this.N_HYJ);
        dest.writeString(this.N_FHYJ);
        dest.writeString(this.VC_Code);
        dest.writeString(this.VC_Name);
        dest.writeString(this.PadctBrief);
        dest.writeString(this.VC_Rule);
        dest.writeString(this.VC_XH);
        dest.writeString(this.VC_PP);
        dest.writeString(this.VC_Url);
        dest.writeInt(this.count);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected Commodity(Parcel in) {
        this.Type_CODE = in.readString();
        this.I_Company = in.readString();
        this.Packing = in.readString();
        this.SaleNum = in.readInt();
        this.Pc_Code = in.readString();
        this.N_JXS = in.readString();
        this.VC_Params = in.readString();
        this.Stock = in.readInt();
        this.Ps_Name = in.readString();
        this.PostagePrice = in.readInt();
        this.Title = in.readString();
        this.N_HYJ = in.readString();
        this.N_FHYJ = in.readString();
        this.VC_Code = in.readString();
        this.VC_Name = in.readString();
        this.PadctBrief = in.readString();
        this.VC_Rule = in.readString();
        this.VC_XH = in.readString();
        this.VC_PP = in.readString();
        this.VC_Url = in.readString();
        this.count = in.readInt();
        this.checked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Commodity> CREATOR = new Parcelable.Creator<Commodity>() {
        @Override
        public Commodity createFromParcel(Parcel source) {
            return new Commodity(source);
        }

        @Override
        public Commodity[] newArray(int size) {
            return new Commodity[size];
        }
    };
}

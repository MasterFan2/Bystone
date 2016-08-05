package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/8/3.
 */
public class DrivingLicensePic {

    /**
     * User_Code : 会员编号
     * I_PicType : 证件类型(5行驶证正面)
     * Img_Card : 证件路径
     * NumberpPate : 车牌号
     */

    private String User_Code;
    private String I_PicType;
    private String Img_Card;
    private String NumberpPate;

    public DrivingLicensePic() {
    }

    public DrivingLicensePic(String user_Code, String i_PicType, String img_Card, String numberpPate) {

        User_Code = user_Code;
        I_PicType = i_PicType;
        Img_Card = img_Card;
        NumberpPate = numberpPate;
    }

    public String getUser_Code() {
        return User_Code;
    }

    public void setUser_Code(String User_Code) {
        this.User_Code = User_Code;
    }

    public String getI_PicType() {
        return I_PicType;
    }

    public void setI_PicType(String I_PicType) {
        this.I_PicType = I_PicType;
    }

    public String getImg_Card() {
        return Img_Card;
    }

    public void setImg_Card(String Img_Card) {
        this.Img_Card = Img_Card;
    }

    public String getNumberpPate() {
        return NumberpPate;
    }

    public void setNumberpPate(String NumberpPate) {
        this.NumberpPate = NumberpPate;
    }
}

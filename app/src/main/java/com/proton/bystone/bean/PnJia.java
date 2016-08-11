package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/8/10.
 * //评价
 */
public class PnJia {
    private  int CommType;
    private  String BookCode;
    private  String CompanyCode;
    private  String UserCode;
    private  String ReviewsScore;
    private  String NickName;
    private  String AvatarPath;

    public PnJia(int commType, String bookCode, String companyCode, String userCode, String reviewsScore, String nickName, String avatarPath) {
        CommType = commType;
        BookCode = bookCode;
        CompanyCode = companyCode;
        UserCode = userCode;
        ReviewsScore = reviewsScore;
        NickName = nickName;
        AvatarPath = avatarPath;
    }

    public int getCommType() {
        return CommType;
    }

    public void setCommType(int commType) {
        CommType = commType;
    }

    public String getBookCode() {
        return BookCode;
    }

    public void setBookCode(String bookCode) {
        BookCode = bookCode;
    }

    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(String companyCode) {
        CompanyCode = companyCode;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getReviewsScore() {
        return ReviewsScore;
    }

    public void setReviewsScore(String reviewsScore) {
        ReviewsScore = reviewsScore;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getAvatarPath() {
        return AvatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        AvatarPath = avatarPath;
    }
}

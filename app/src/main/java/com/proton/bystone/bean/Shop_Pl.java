package com.proton.bystone.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/25.
 * //商品全部评论
 */
public class Shop_Pl {

    /**
     * Reply : [{"Reply":"谢谢你对本店的自持欢迎下次再来"},{"Reply":"下次来给你打5折"}]
     * Path : /Uplaod/Attachment/20160713/20160713145112344.jpg,/Uplaod/Attachment/20160713/20160713145112344.jpg,/Uplaod/Attachment/20160713/20160713145112344.jpg
     * CommentsTime : 2016-06-30 10:18:18
     * Id : 1
     * CmtCode : null
     * CommType : 0
     * CommContent : 老板不错，商品也不错，老板厚道
     * ScoreCode : 1
     * ReviewsScore : 5
     * BookCode : null
     * CompanyCode : 201606241124069327
     * CmtyCode : 2016052317205144748
     * UserCode : 201605091528083056
     * NickName : 小强强
     * AvatarPath :
     * CmentSource : 0
     * ReplyType : 1
     * CommTime : /Date(-62135596800000)/
     */

    private String Path;
    private String CommentsTime;
    private int Id;
    private Object CmtCode;
    private int CommType;
    private String CommContent;
    private String ScoreCode;
    private int ReviewsScore;
    private Object BookCode;
    private String CompanyCode;
    private String CmtyCode;
    private String UserCode;
    private String NickName;
    private String AvatarPath;
    private int CmentSource;
    private int ReplyType;
    private String CommTime;
    /**
     * Reply : 谢谢你对本店的自持欢迎下次再来
     */

    private List<ReplyBean> Reply;

    public String getPath() {
        return Path;
    }

    public void setPath(String Path) {
        this.Path = Path;
    }

    public String getCommentsTime() {
        return CommentsTime;
    }

    public void setCommentsTime(String CommentsTime) {
        this.CommentsTime = CommentsTime;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Object getCmtCode() {
        return CmtCode;
    }

    public void setCmtCode(Object CmtCode) {
        this.CmtCode = CmtCode;
    }

    public int getCommType() {
        return CommType;
    }

    public void setCommType(int CommType) {
        this.CommType = CommType;
    }

    public String getCommContent() {
        return CommContent;
    }

    public void setCommContent(String CommContent) {
        this.CommContent = CommContent;
    }

    public String getScoreCode() {
        return ScoreCode;
    }

    public void setScoreCode(String ScoreCode) {
        this.ScoreCode = ScoreCode;
    }

    public int getReviewsScore() {
        return ReviewsScore;
    }

    public void setReviewsScore(int ReviewsScore) {
        this.ReviewsScore = ReviewsScore;
    }

    public Object getBookCode() {
        return BookCode;
    }

    public void setBookCode(Object BookCode) {
        this.BookCode = BookCode;
    }

    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(String CompanyCode) {
        this.CompanyCode = CompanyCode;
    }

    public String getCmtyCode() {
        return CmtyCode;
    }

    public void setCmtyCode(String CmtyCode) {
        this.CmtyCode = CmtyCode;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getAvatarPath() {
        return AvatarPath;
    }

    public void setAvatarPath(String AvatarPath) {
        this.AvatarPath = AvatarPath;
    }

    public int getCmentSource() {
        return CmentSource;
    }

    public void setCmentSource(int CmentSource) {
        this.CmentSource = CmentSource;
    }

    public int getReplyType() {
        return ReplyType;
    }

    public void setReplyType(int ReplyType) {
        this.ReplyType = ReplyType;
    }

    public String getCommTime() {
        return CommTime;
    }

    public void setCommTime(String CommTime) {
        this.CommTime = CommTime;
    }

    public List<ReplyBean> getReply() {
        return Reply;
    }

    public void setReply(List<ReplyBean> Reply) {
        this.Reply = Reply;
    }

    public static class ReplyBean {
        private String Reply;

        public String getReply() {
            return Reply;
        }

        public void setReply(String Reply) {
            this.Reply = Reply;
        }
    }
}

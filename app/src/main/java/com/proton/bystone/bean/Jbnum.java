package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/8/5.
 */
public class Jbnum {


    /**
     * GoldBalance : -51.00
     * InviteFriendsGold : 7
     * ConsumPtionGold : 0
     * Exceed : 0%
     */

    private String GoldBalance;
    private int InviteFriendsGold;
    private int ConsumPtionGold;
    private String Exceed;

    public String getGoldBalance() {
        return GoldBalance;
    }

    public void setGoldBalance(String GoldBalance) {
        this.GoldBalance = GoldBalance;
    }

    public int getInviteFriendsGold() {
        return InviteFriendsGold;
    }

    public void setInviteFriendsGold(int InviteFriendsGold) {
        this.InviteFriendsGold = InviteFriendsGold;
    }

    public int getConsumPtionGold() {
        return ConsumPtionGold;
    }

    public void setConsumPtionGold(int ConsumPtionGold) {
        this.ConsumPtionGold = ConsumPtionGold;
    }

    public String getExceed() {
        return Exceed;
    }

    public void setExceed(String Exceed) {
        this.Exceed = Exceed;
    }
}

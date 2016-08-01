package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/7/15.
 * 注册
 */
public class Refister {
    public String InvitePeople;
    public String Mb_LoginName;
    public int Mb_Pwd;
    public int LogonType;

    public String getInvitePeople() {
        return InvitePeople;
    }

    public void setInvitePeople(String invitePeople) {
        InvitePeople = invitePeople;
    }

    public String getMb_LoginName() {
        return Mb_LoginName;
    }

    public void setMb_LoginName(String mb_LoginName) {
        Mb_LoginName = mb_LoginName;
    }

    public int getMb_Pwd() {
        return Mb_Pwd;
    }

    public void setMb_Pwd(int mb_Pwd) {
        Mb_Pwd = mb_Pwd;
    }

    public int getLogonType() {
        return LogonType;
    }

    public void setLogonType(int logonType) {
        LogonType = logonType;
    }

    public Refister(String invitePeople, String mb_LoginName, int mb_Pwd, int logonType) {
        InvitePeople = invitePeople;
        Mb_LoginName = mb_LoginName;
        Mb_Pwd = mb_Pwd;
        LogonType = logonType;
    }
}

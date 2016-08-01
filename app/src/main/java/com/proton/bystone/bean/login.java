package com.proton.bystone.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/7/22.
 * d登陆的bean
 */
@Table(name = "login")
public class login {


    /**
     * LogonType : 1
     * Mb_LoginName : 18225026697
     * Mb_Pwd : null
     * VC_Client : xxaabbc085412556sxx
     * Id : 2
     * Mb_Code : 201605261656057265
     * Mb_Name : 小强
     * Enable : 0
     * RegistrationTime : /Date(1465955745000)/
     * LastLoginTime : /Date(1469169809000)/
     * GoldBalance : 0
     * InvitePeople : null
     * ProfilePicture : null
     */
    @Column(name = "LogonType")
    private int LogonType;

    @Column(name = "Mb_LoginName")
    private String Mb_LoginName;

    @Column(name = "Mb_Pwd")
    private Object Mb_Pwd;

    @Column(name = "VC_Client")
    private String VC_Client;

    @Column(name = "Id")
    private int Id;

    @Column(name = "Mb_Code", isId = true)
    private String Mb_Code;

    @Column(name = "Mb_Name")
    private String Mb_Name;

    @Column(name = "Enable")
    private int Enable;

    @Column(name = "RegistrationTime")
    private String RegistrationTime;

    @Column(name = "LastLoginTime")
    private String LastLoginTime;

    @Column(name = "GoldBalance")
    private int GoldBalance;
    private Object InvitePeople;
    private Object ProfilePicture;

    public int getLogonType() {
        return LogonType;
    }

    public void setLogonType(int LogonType) {
        this.LogonType = LogonType;
    }

    public String getMb_LoginName() {
        return Mb_LoginName;
    }

    public void setMb_LoginName(String Mb_LoginName) {
        this.Mb_LoginName = Mb_LoginName;
    }

    public Object getMb_Pwd() {
        return Mb_Pwd;
    }

    public void setMb_Pwd(Object Mb_Pwd) {
        this.Mb_Pwd = Mb_Pwd;
    }

    public String getVC_Client() {
        return VC_Client;
    }

    public void setVC_Client(String VC_Client) {
        this.VC_Client = VC_Client;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getMb_Code() {
        return Mb_Code;
    }

    public void setMb_Code(String Mb_Code) {
        this.Mb_Code = Mb_Code;
    }

    public String getMb_Name() {
        return Mb_Name;
    }

    public void setMb_Name(String Mb_Name) {
        this.Mb_Name = Mb_Name;
    }

    public int getEnable() {
        return Enable;
    }

    public void setEnable(int Enable) {
        this.Enable = Enable;
    }

    public String getRegistrationTime() {
        return RegistrationTime;
    }

    public void setRegistrationTime(String RegistrationTime) {
        this.RegistrationTime = RegistrationTime;
    }

    public String getLastLoginTime() {
        return LastLoginTime;
    }

    public void setLastLoginTime(String LastLoginTime) {
        this.LastLoginTime = LastLoginTime;
    }

    public int getGoldBalance() {
        return GoldBalance;
    }

    public void setGoldBalance(int GoldBalance) {
        this.GoldBalance = GoldBalance;
    }

    public Object getInvitePeople() {
        return InvitePeople;
    }

    public void setInvitePeople(Object InvitePeople) {
        this.InvitePeople = InvitePeople;
    }

    public Object getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(Object ProfilePicture) {
        this.ProfilePicture = ProfilePicture;
    }
}

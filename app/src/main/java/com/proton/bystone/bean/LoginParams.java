package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/7/15.
 */
public class LoginParams{

    private String Mb_LoginName;
    private String Mb_Pwd;
    private String VC_Client;
    private int    LogonType;

    public String getMb_LoginName() {
        return Mb_LoginName;
    }

    public void setMb_LoginName(String mb_LoginName) {
        Mb_LoginName = mb_LoginName;
    }

    public String getMb_Pwd() {
        return Mb_Pwd;
    }

    public void setMb_Pwd(String mb_Pwd) {
        Mb_Pwd = mb_Pwd;
    }

    public String getVC_Client() {
        return VC_Client;
    }

    public void setVC_Client(String VC_Client) {
        this.VC_Client = VC_Client;
    }

    public int getLogonType() {
        return LogonType;
    }

    public void setLogonType(int logonType) {
        LogonType = logonType;
    }

    public LoginParams() {

    }

    public LoginParams(String mb_LoginName, String mb_Pwd, String VC_Client, int logonType) {

        Mb_LoginName = mb_LoginName;
        Mb_Pwd = mb_Pwd;
        this.VC_Client = VC_Client;
        LogonType = logonType;
    }
}

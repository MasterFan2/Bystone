package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/8/16.
 */
public class Newpasswd {
    private  String Mb_LoginName;
    private  String Mb_Pwd;

    public Newpasswd(String mb_LoginName, String mb_Pwd) {
        Mb_LoginName = mb_LoginName;
        Mb_Pwd = mb_Pwd;
    }

    public String getMb_Pwd() {
        return Mb_Pwd;
    }

    public void setMb_Pwd(String mb_Pwd) {
        Mb_Pwd = mb_Pwd;
    }

    public String getMb_LoginName() {
        return Mb_LoginName;
    }

    public void setMb_LoginName(String mb_LoginName) {
        Mb_LoginName = mb_LoginName;
    }
}

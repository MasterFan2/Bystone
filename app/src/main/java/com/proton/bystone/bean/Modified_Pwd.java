package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/7/18.
 * 修改密码
 */
public class Modified_Pwd {
    private String Mb_LoginName;
    private String Mb_Pwd;

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

    public Modified_Pwd(String mb_LoginName, String mb_Pwd) {
        Mb_LoginName = mb_LoginName;
        Mb_Pwd = mb_Pwd;
    }
}

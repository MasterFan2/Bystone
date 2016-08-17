package com.proton.bystone.cache;

import com.proton.bystone.bean.LoginResp;
import com.proton.bystone.utils.MDbUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * 登录管理类, 主要作用是缓存登录信息, 判断 是否登录, 删除登录 信息
 * Created by MasterFan on 2016/8/4.
 */
public class LoginManager {

    ///登录用户信息
    private LoginResp loginInfo = null;

    private DbManager db = null;
    private static LoginManager ourInstance = new LoginManager();
    public static LoginManager getInstance() {
        return ourInstance;
    }
    private LoginManager() { }

    /**
     * 初始化
     */
    public void initialize() {
        db = x.getDb(MDbUtils.getDaoConfig());
    }

    /**
     * 是否登录
     * true:已经登录
     * false:未登录
     *
     * @return
     */
    public boolean isLogin() {
        if (loginInfo != null) return true;
        loginInfo = getCacheLoginInfo();
        return loginInfo != null;
    }

    /**
     * 缓存登录信息
     *
     * @param loginInfo
     * @return
     */
    public boolean cacheLogin(LoginResp loginInfo) {
        if (loginInfo == null) return false;
        this.loginInfo = loginInfo;
        try {
            db.save(loginInfo);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取缓存信息
     *
     * @return
     */
    public LoginResp getLoginInfo(){
        if (loginInfo != null) return loginInfo;
        loginInfo = getCacheLoginInfo();
        return loginInfo;
    }

    /**
     * 获取缓存的登录信息
     * @return
     */
    private LoginResp getCacheLoginInfo() {
        try {
            return db.findFirst(LoginResp.class);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除登录信息
     * @return
     */
    public boolean delLoginInfo() {
        this.loginInfo = null;
        try {
            LoginResp loginInfo = db.findFirst(LoginResp.class);
            if (null != loginInfo) {
                db.delete(loginInfo);
            }
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }
}

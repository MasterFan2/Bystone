package com.proton.bystone;

import android.app.Application;

import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.net.HttpClients;

import org.xutils.x;


/**
 * Application
 * create by MasterFan on 2016/8/4
 */
public class BystoneApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ///xutils init
        x.Ext.init(this);

        ///init net
        HttpClients.getInstance().initialize(this);

        ///init login manager
        LoginManager.getInstance().initialize();
    }
}

package com.proton.bystone;

import android.app.Application;

import com.proton.bystone.net.HttpClients;

import org.xutils.x;


/**
 * 测试提交44444909i.j;oj
 *  by Brightbeacon on 2016/7/4 0004.
 */
public class BystoneApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//xutils init
        HttpClients.getInstance().initialize(this);
    }
}

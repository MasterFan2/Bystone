package com.proton.bystone.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 下载服务
 * Created by MasterFan on 2016/8/1.
 */
public class DownloadService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }
}

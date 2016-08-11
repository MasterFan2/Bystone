package com.proton.library.utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * activity跳转管理类， 某些情况下需要关闭之前打开的界面【先添加到管理器中， 再finishAllActivity】
 * Created by MasterFan on 2016/8/4.
 */
public class ActivityManager {

    private static ArrayList<Activity> activityList = null;//缓存所有activity
    private static ActivityManager     ourInstance  = null;//singleton instance

    /**
     *
     * @return
     */
    public static ActivityManager getInstance() {
        if (null == ourInstance)
            ourInstance = new ActivityManager();
        return ourInstance;
    }

    private ActivityManager() {
        if (null == activityList) {
            activityList = new ArrayList<>();
        }
    }

    /**
     * 添加activity到缓存 列表
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activity == null) return;
        if (activityList == null) activityList = new ArrayList<>();
        if (activityList.size() <= 0) activityList.add(activity);
        else {
            if (!activityList.contains(activity)) activityList.add(activity);
        }
    }

    /**
     * 删除缓存中的Activity
     * @param activity
     */
    public void delActivity(Activity activity) {
        if (activity == null) return;
        if (activityList == null || activityList.size() == 0) return;
        if (activityList.contains(activity)){
            activityList.remove(activity);
        }
    }

    /**
     * 清除所有缓存 的activity
     */
    public void finishAllActivity(){
        if (activityList == null || activityList.size() == 0) return;
        for (Activity activity : activityList){
            if (!activity.isFinishing())
                activity.finish();
        }
        activityList.clear();
    }
}

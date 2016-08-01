package com.proton.bystone.utils;

import android.os.Environment;

import org.xutils.DbManager;

import java.io.File;

/**
 * Created by Brightbeacon on 2016/7/4 0004.
 */
public class MDbUtils {
    static DbManager.DaoConfig daoConfig;

    public static DbManager.DaoConfig getDaoConfig() {
        File file = new File(Environment.getExternalStorageDirectory().getPath());
        if (daoConfig == null) {
            daoConfig = new DbManager.DaoConfig()
                    .setDbName("test.db")
                    // 不设置dbDir时, 默认存储在app的私有目录.
                    .setDbDir(file) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.
                    .setDbVersion(2)
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                            // TODO: ...
                            // db.addColumn(...);
                            // db.dropTable(...);
                            // ...
                            // or
                            // db.dropDb();
                        }
                    });
        }
        return daoConfig;
    }
}

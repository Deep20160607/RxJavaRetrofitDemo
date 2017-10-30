package com.tsingda.rxjavaretrofitdemo.application;

import android.app.Application;
import android.content.Context;

/**
 * @author dupeng
 * @version 1.0.0
 * @since 2017/10/25 17:00
 */

public class BaseApplication  extends Application {

    private static Context appContext;
    public static boolean isCaches;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static int getBuild() {
        return ConfigConstant.OFFLINE;
//        return Static.ONLINE;
    }

    public static Context getAppContext() {
        return appContext;
    }
}

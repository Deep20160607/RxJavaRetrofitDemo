package com.tsingda.rxjavaretrofitdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tsingda.rxjavaretrofitdemo.application.BaseApplication;

/**
 * 获取系统状态工具类
 *
 * @author dupeng
 * @version 1.0.0
 * @since 2017/10/26 17:14
 */

public class SystemState {

    private static ConnectivityManager connectManager = (ConnectivityManager) BaseApplication.getAppContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE);

    public static boolean isNetConnected() {
        NetworkInfo info = connectManager.getActiveNetworkInfo();
        return info != null && connectManager.getActiveNetworkInfo().isConnected();
    }
}

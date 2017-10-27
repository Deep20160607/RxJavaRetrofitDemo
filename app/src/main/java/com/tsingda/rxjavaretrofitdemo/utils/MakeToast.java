package com.tsingda.rxjavaretrofitdemo.utils;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import com.tsingda.rxjavaretrofitdemo.application.BaseApplication;

/**
 * 吐司具类
 *
 * @author dupeng
 * @version 1.0.0
 * @since 2017/10/26 17:14
 */
public class MakeToast {

    private static Toast currentToast = null;
    private static String message;
    public static int LENGTH_SHORT = 0;
    public static int LENGTH_LONG = 1;
    public static long time = 0;

    private static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void create(String text) {
        create(BaseApplication.getAppContext(), text);
    }

    public static void create(int textResourceId) {
        create(BaseApplication.getAppContext(), textResourceId);
    }

    public static void create(Context context, String text) {
        showToast(context, text, LENGTH_SHORT);
    }

    public static void create(Context context, int textResourceId) {
        showToast(context, context.getString(textResourceId), LENGTH_SHORT);
    }

    public static synchronized void showToast(Context context, String text, int duration) {
        if (currentToast == null) {
            currentToast = Toast.makeText(context, text, duration);
            currentToast.setGravity(Gravity.CENTER, 0, 0);
            time = System.currentTimeMillis();
            message = text;
            currentToast.show();
        } else {
            if (!message.equals(text)) {
                message = text;
                currentToast.setText(text);
                currentToast.setDuration(duration);
                currentToast.show();
            } else {
                long curTime = System.currentTimeMillis();
                if (curTime - time > 2000) {
                    currentToast.show();
                }
            }
        }
    }
}

package com.tsingda.rxjavaretrofitdemo.model.net.api;

import android.util.Log;

import com.tsingda.rxjavaretrofitdemo.R;
import com.tsingda.rxjavaretrofitdemo.model.net.bean.common.ResCommon;
import com.tsingda.rxjavaretrofitdemo.utils.MakeToast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * @author dupeng
 * @version 1.0.0
 * @since 2017/10/25 18:05
 */
public abstract class ResponseSubscriber<T extends ResCommon> extends Subscriber<T> {

    @Override
    public void onStart() {
        super.onStart();
        loadStart();
    }

    /**
     * DO NOT OVERRIDE THIS FUNCTION
     */
    @Override
    public void onCompleted() {
        completed();
    }

    /**
     * DO NOT OVERRIDE THIS FUNCTION
     *
     * @param e Exception
     */
    @Override
    public void onError(Throwable e) {
        Log.e("request onError:", "请求失败了...");
        Log.e("network exception,", "message : " + e);
        if (e instanceof SocketTimeoutException) {
            MakeToast.create(R.string.net_error_socket_timeout);
        } else if (e instanceof ConnectException) {
            MakeToast.create(R.string.net_error_connect_lost);
        }
        //completed();
    }

    /**
     * DO NOT OVERRIDE THIS FUNCTION
     *
     * @param t {@code ? extends ResCommon}
     */
    @Override
    public void onNext(T t) {
        Log.e("request onNext:", "请求成功了..." +t.toString());
        String code = t.getResCode();
        if (ResponseCode.OK.equals(code)) {
              success(t);
        } else if (ResponseCode.REQUIRED_LOGIN.equals(code)) {
            requiredLogin();
        } else {
            fail(t.getResMessage(), code);
        }
    }

    /**
     * must override
     *
     * @param t {@code ? extends ResCommon}
     */
    protected abstract void success(T t);

    /**
     * must override
     */
    protected abstract void requiredLogin();

    /**
     * override if you need
     *
     * @param tag          API TAG
     * @param responseCode error code
     */
    protected void fail(String tag, String responseCode) {
        Log.e("request fail:", String.format("Tag : %s, cause by : %s", tag, responseCode));
    }

    /**
     * override if you need
     */
    protected void completed() {
        Log.e("request completed:", "结束请求了...");
    }

    /**
     * override if you need
     */
    protected void loadStart() {
        Log.e("request onStart:", "开始请求了...");
    }
}

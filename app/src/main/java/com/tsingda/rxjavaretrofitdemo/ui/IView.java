package com.tsingda.rxjavaretrofitdemo.ui;

/**
 * @author dupeng
 * @version 1.0.0
 * @since 2017/10/26 11:11
 */
public interface IView {

    /**
     * forceLogout when another device login the same account
     */
    void forceLogout();

    /**
     * if net connect, will return true and make a toast, otherwise return false
     *
     * @return is net connected
     */
    boolean isNetConnected();
}

package com.tsingda.rxjavaretrofitdemo.presenter;

/**
 * @author dupeng
 * @version 1.0.0
 * @since 2017/10/26 10:39
 */

public interface IPresenter<V> {

    /**
     * attach view to presenter
     *
     * @param view attach view
     */
    void attachView(V view);

    /**
     * detach view from presenter
     *
     * @param view detach view
     */
    void detachView(V view);
}

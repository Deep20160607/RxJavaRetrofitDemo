package com.tsingda.rxjavaretrofitdemo.presenter.common;

import android.util.Log;

import com.tsingda.rxjavaretrofitdemo.R;
import com.tsingda.rxjavaretrofitdemo.model.net.bean.common.ResCommon;
import com.tsingda.rxjavaretrofitdemo.presenter.IPresenter;
import com.tsingda.rxjavaretrofitdemo.utils.MakeToast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author dupeng
 * @version 1.0.0
 * @since 2017/10/26 10:39
 */
public class BasePresenter<V> implements IPresenter<V> {

    protected CompositeSubscription mCompositeSubscription;
    protected V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView(V view) {
        mView = null;
        clearAllSubscribe();
    }

    protected void clearAllSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }

    protected void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    protected <T extends ResCommon> void addNetSubScribe(Observable<T> observable, Subscriber<T> subscriber) {
        addSubScribe(observable, subscriber);
    }

    protected <T> void addSubScribe(Observable<T> observable, Subscriber<T> subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    public void NetError(Throwable e) {
        Log.e("network exception,", String.format("message : %s, cause by : %s", e.getMessage(), e.getCause()));
        if (e instanceof SocketTimeoutException) {
            MakeToast.create(R.string.net_error_socket_timeout);
        } else if (e instanceof ConnectException) {
            MakeToast.create(R.string.net_error_connect_lost);
        }
    }

    protected void NetFail(String tag, String responseCode) {
        Log.e("request fail", String.format("Tag : %s, cause by : %s", tag, responseCode));
    }
}

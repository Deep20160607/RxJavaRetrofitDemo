package com.tsingda.rxjavaretrofitdemo.ui.frament;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author dupeng
 * @version 1.0.0
 * @since 2017/11/6 14:50
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    protected View fragmentRootView;
    private Unbinder unbinder;
    private static BaseFragment.ThreadDataCallBack callback;
    private static Handler threadHandle = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what == 225809) {
                BaseFragment.callback.onSuccess();
            }

        }
    };

    protected abstract View inflaterView(LayoutInflater var1, ViewGroup var2, Bundle var3);

    protected void initWidget(View parentView) {
    }

    protected void initData() {
    }

    protected void threadDataInit() {
    }

    protected void initDataFromThread() {
        callback = new BaseFragment.ThreadDataCallBack() {
            public void onSuccess() {
                BaseFragment.this.threadDataInit();
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.fragmentRootView = this.inflaterView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, fragmentRootView);
        this.initData();
        this.initWidget(this.fragmentRootView);
        (new Thread(new Runnable() {
            public void run() {
                BaseFragment.this.initDataFromThread();
                BaseFragment.threadHandle.sendEmptyMessage(225809);
            }
        })).start();
        return this.fragmentRootView;

    }

    protected <T extends View> T bindView(int id) {
        return (T) this.fragmentRootView.findViewById(id);
    }

    protected <T extends View> T bindView(int id, boolean click) {
        T view = (T) this.fragmentRootView.findViewById(id);
        if(click) {
            view.setOnClickListener(this);
        }

        return view;
    }

    private interface ThreadDataCallBack {
        void onSuccess();
    }

    @Override
    public void onDestroy() {
        if(unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }
}

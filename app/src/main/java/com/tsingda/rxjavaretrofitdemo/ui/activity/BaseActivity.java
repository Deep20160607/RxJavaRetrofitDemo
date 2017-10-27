package com.tsingda.rxjavaretrofitdemo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tsingda.rxjavaretrofitdemo.R;
import com.tsingda.rxjavaretrofitdemo.ui.IView;
import com.tsingda.rxjavaretrofitdemo.utils.MakeToast;
import com.tsingda.rxjavaretrofitdemo.utils.SystemState;

public class BaseActivity extends AppCompatActivity implements IView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void forceLogout() {

    }

    @Override
    public boolean isNetConnected() {
        if (SystemState.isNetConnected()) {
            return true;
        } else {
            MakeToast.create(this, R.string.net_connected_fail);
            return false;
        }
    }
}

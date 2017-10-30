package com.tsingda.rxjavaretrofitdemo.presenter;

import android.util.Log;

import com.tsingda.rxjavaretrofitdemo.model.net.api.ResponseSubscriber;
import com.tsingda.rxjavaretrofitdemo.model.net.api.ServiceFactory;
import com.tsingda.rxjavaretrofitdemo.model.net.bean.UserBeanRes;
import com.tsingda.rxjavaretrofitdemo.model.net.bean.common.ResCommon;
import com.tsingda.rxjavaretrofitdemo.presenter.common.BasePresenter;
import com.tsingda.rxjavaretrofitdemo.ui.IMainView;

import java.util.List;

/**
 * @author dupeng
 * @version 1.0.0
 * @since 2017/10/26 11:10
 */

public class MainPresent extends BasePresenter<IMainView>{

    public MainPresent(IMainView view) {
        attachView(view);
    }

    public void LoginNet(final String userName, String userPassword, boolean isCache){


        addNetSubScribe(ServiceFactory.getUserService(isCache).getQuery(userName, userPassword), new ResponseSubscriber<ResCommon<UserBeanRes>>() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            protected void success(ResCommon<UserBeanRes> userBeanResResCommon) {
                Log.v("request", "------------success:" + userBeanResResCommon.toString());
                mView.updateUserInfo(userBeanResResCommon.getData());
            }

            @Override
            protected void requiredLogin() {

            }
        });
    }

    public void LoginListNet(final String userName, String userPassword, boolean isCache){
        addNetSubScribe(ServiceFactory.getUserService(isCache).getListQuery(userName, userPassword), new ResponseSubscriber<ResCommon<List<UserBeanRes>>>() {
            @Override
            protected void success(ResCommon<List<UserBeanRes>> listResCommon) {
                Log.v("request", "------------success:" + listResCommon.getData().size());
                mView.updateUserListInfo(listResCommon.getData());
            }

            @Override
            protected void requiredLogin() {

            }
        });
    }
}

package com.tsingda.rxjavaretrofitdemo.ui;

import com.tsingda.rxjavaretrofitdemo.model.net.bean.UserBeanRes;

import java.util.List;

/**
 * @author dupeng
 * @version 1.0.0
 * @since 2017/10/26 11:11
 */

public interface IMainView extends IView{

    void updateUserInfo(UserBeanRes userBeanRes);

    void updateUserListInfo(List<UserBeanRes> userBeanResList);
}

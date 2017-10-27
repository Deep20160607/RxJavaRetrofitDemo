package com.tsingda.rxjavaretrofitdemo.model.net.bean;

/**
 * @author dupeng
 * @version 1.0.0
 * @since 2017/10/17 10:02
 */

public class UserBeanRes{

    private String userName;
    private String userPwd;

    public UserBeanRes() {
    }

    public UserBeanRes(String userName, String userPwd) {
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                '}';
    }
}

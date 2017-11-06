package com.tsingda.rxjavaretrofitdemo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.tsingda.rxjavaretrofitdemo.model.net.bean.UserBeanRes;
import com.tsingda.rxjavaretrofitdemo.presenter.MainPresent;
import com.tsingda.rxjavaretrofitdemo.ui.IMainView;
import com.tsingda.rxjavaretrofitdemo.ui.activity.BaseActivity;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements IMainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.tv)
    TextView mTv;

    MainPresent presenter;
    private String userName;
    private String userPassword;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "onCreate 执行了。。。");
        this.context = this;
        mTv.setText("Butterknife注解");
        presenter = new MainPresent(this);
        userName = "dupeng";
        userPassword = "123456";

        //presenter.LoginNet(userName, userPassword, true);//不带list
        //presenter.LoginListNet(userName, userPassword, true);//带list
    }

    @Override
    protected int getLayout() {
        Log.v(TAG, "getLayout 执行了。。。");
        return R.layout.activity_main;
    }


    @Override
    public void updateUserInfo(UserBeanRes userBeanRes) {
        Log.v("Tag", "------------updateUserInfo:" + userBeanRes.toString());
        Toast.makeText(context, "登录成功!:" + userBeanRes.getUserName(), Toast.LENGTH_LONG).show();
        //MakeToast.create(userBeanRes.toString());

    }

    @Override
    public void updateUserListInfo(List<UserBeanRes> userBeanResList) {
        List<UserBeanRes> list = userBeanResList;
        if(list !=null) {
            for (int i = 0; i < list.size(); i++) {
               UserBeanRes userBeanRes = list.get(i);
                Toast.makeText(context, "登录成功!:" + userBeanRes.getUserName() + "______" + i, Toast.LENGTH_LONG).show();
            }
        }
    }
}

package com.tsingda.rxjavaretrofitdemo.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.tsingda.rxjavaretrofitdemo.R;
import com.tsingda.rxjavaretrofitdemo.ui.IView;
import com.tsingda.rxjavaretrofitdemo.ui.frament.BaseFragment;
import com.tsingda.rxjavaretrofitdemo.utils.MakeToast;
import com.tsingda.rxjavaretrofitdemo.utils.SystemState;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements IView {

    private Unbinder unbinder;
    protected BaseFragment currentSupportFragment;
    private static final int PERMISSION_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);

        //checkPermissions(mNeedPermissions);
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

    @Override
    protected void onDestroy() {
        if(unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    protected abstract int getLayout();

    public void changeFragment(int resView, BaseFragment targetFragment) {
        if(!targetFragment.equals(this.currentSupportFragment)) {
            android.support.v4.app.FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
            if(!targetFragment.isAdded()) {
                transaction.add(resView, targetFragment, targetFragment.getClass().getName());
            }

            if(targetFragment.isHidden()) {
                transaction.show(targetFragment);
            }

            if(this.currentSupportFragment != null && this.currentSupportFragment.isVisible()) {
                transaction.hide(this.currentSupportFragment);
            }

            this.currentSupportFragment = targetFragment;
            transaction.commit();
        }
    }

    /**
     * 需要进行检测的权限数组
     */
    protected String[] mNeedPermissions = {
            // 这里填你需要申请的权限
            // 如：读取sd卡
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    /**
     * @param permissions
     */
    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissionList = findDeniedPermissions(permissions);
        if (null != needRequestPermissionList
                && needRequestPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissionList.toArray(
                            new String[needRequestPermissionList.size()]),
                    PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                needRequestPermissionList.add(perm);
            }
        }
        return needRequestPermissionList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (!verifyPermissions(grantResults)) {
                showMissingPermissionDialog();
            }
        }
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示信息
     *
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("权限提示");
        builder.setMessage("是否同意权限？");

        // 拒绝, 退出应用
        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}

package com.haixi.spacetime.Common;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.haixi.spacetime.R;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Common.Settings.update;

public class BasicActivity extends AppCompatActivity {
    public static List<Activity> activityList0 = new ArrayList<Activity>();
    public static List<Activity> activityList1 = new ArrayList<Activity>();
    public Fragment originFragment;
    public String fragmentName = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActionBar actionBar=getSupportActionBar();
        if (actionBar.isShowing()){
            actionBar.hide();
        }
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        update(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(statusColor));
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    public static void closeL_R_W(){
        if (activityList0.size() == 0) return;
        for (Activity activity: activityList0){
            activity.finish();
        }
    }

    public static void closeCUT(){
        if (activityList1.size() == 0) return;
        for (Activity activity: activityList1){
            activity.finish();
        }
    }

    protected void replaceFragment(int id){
        if (originFragment == null){
            originFragment = new Fragment();
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id ,originFragment);
        transaction.commit();
    }
}

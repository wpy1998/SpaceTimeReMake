package com.haixi.spacetime.Common.Components;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;

public class BasicFragment extends Fragment {
    protected BroadcastReceiver userInfoBroadcastReceiver = null;
    protected OkHttpAction okHttpAction = null;
    protected IntentFilter intentFilter = null;

    @Override
    public void onDestroyView() {
        if (userInfoBroadcastReceiver != null){
            getContext().unregisterReceiver(userInfoBroadcastReceiver);
        }
        super.onDestroyView();
    }

    private static final int MIN_DELAY_TIME = 2000;
    private long lastClickTime;
    public boolean isFastClick() {//false代表不是连续触屏
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}

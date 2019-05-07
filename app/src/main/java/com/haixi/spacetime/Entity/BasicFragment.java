package com.haixi.spacetime.Entity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.widget.Toast;

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

    public void refresh(){
    }

    private static final int MIN_DELAY_TIME = 100;
    private long lastClickTime;
    public boolean isFastClick() {//false代表不是连续触屏
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        if (flag){
            Toast.makeText(getContext(), "请求次数过多，请稍等片刻", Toast.LENGTH_SHORT).show();
        }
        return flag;
    }
}

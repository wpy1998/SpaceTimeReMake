package com.example.spacetime.UserModel.Components;

import android.support.v4.app.Fragment;

public class BasicFragment extends Fragment {
    private static final int MIN_DELAY_TIME = 1000;
    private static long lastClickTime;
    public static boolean isFastClick() {//false代表不是连续触屏
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}

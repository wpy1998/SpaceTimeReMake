package com.haixi.spacetime.Common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Settings {
    public static boolean isReset = false;
    public static int windowsWidth;
    public static int windowsHeight;

    private static int dpi;
    private static int dpiToDp;

    public static int getPx(int input){
//        return input * dpiToDp;
        return input * windowsWidth / 375;
    }

    public static void update(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        dpi = dm.densityDpi;
//        dpiToDp = dpi / 160;
        windowsWidth = dm.widthPixels;
        windowsHeight = dm.heightPixels;
    }

    public static void setMargin(View view, int left, int
            top, int right, int bottom, boolean isCenter){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(view
                .getLayoutParams());
        params.setMargins(getPx(left), getPx(top), getPx(right), getPx(bottom));
        if (isCenter){
            params.gravity = Gravity.CENTER;
        }
        view.setLayoutParams(params);
    }

    public static void setHW(View view, int height, int width){
        view.getLayoutParams().height = getPx(height);
        view.getLayoutParams().width = getPx(width);
    }

    public static void setH(View view, int height){
        view.getLayoutParams().height = getPx(height);
    }

    public static void setW(View view, int width){
        view.getLayoutParams().width = getPx(width);
    }

    public static void setTextSize(TextView view, int size){
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, getPx(size));
    }

    public static void setTextSize(EditText view, int size){
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, getPx(size));
    }
}
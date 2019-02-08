package com.example.spacetime.Others;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.spacetime.Others.Settings.update;

public class BasicActivity extends AppCompatActivity {
    public static List<Activity> activityList0 = new ArrayList<Activity>();
    public static List<Activity> activityList1 = new ArrayList<Activity>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar=getSupportActionBar();
        if (actionBar.isShowing()){
            actionBar.hide();
        }

        update(this);
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
}

package com.haixi.spacetime.LoginAndRegister;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haixi.spacetime.Common.BasicActivity;
import com.haixi.spacetime.LoginAndRegister.Fragments.CompleteMessageFragment;
import com.haixi.spacetime.LoginAndRegister.Fragments.RegisterBeginFragment;
import com.haixi.spacetime.R;

@Route(path = "/spaceTime/register")
public class RegisterActivity extends BasicActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        activityList0.add(this);
        setStatusBarColor(this, R.color.colorWhite);
        Intent intentFront = getIntent();
        choosePath(intentFront.getStringExtra("path"));
    }

    private void choosePath(String path){
        switch (path){
            case "registerBegin":
                originFragment = new RegisterBeginFragment();
                replaceFragment(R.id.register_frameLayout);
                break;
            case "completeMessage":
                originFragment = new CompleteMessageFragment();
                replaceFragment(R.id.register_frameLayout);
                break;
            default:
                replaceFragment(R.id.register_frameLayout);
                break;
        }
    }
}

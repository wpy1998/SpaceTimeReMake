package com.haixi.spacetime.LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haixi.spacetime.LoginAndRegister.Fragments.WelcomeFragment;
import com.haixi.spacetime.Others.BasicActivity;
import com.haixi.spacetime.LoginAndRegister.Fragments.CompleteMessageFragment;
import com.haixi.spacetime.LoginAndRegister.Fragments.RegisterBeginFragment;
import com.haixi.spacetime.R;

@Route(path = "/spaceTime/register")
public class RegisterActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        activityList0.add(this);
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

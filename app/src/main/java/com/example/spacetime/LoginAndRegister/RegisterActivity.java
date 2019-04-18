package com.example.spacetime.LoginAndRegister;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.spacetime.Others.BasicActivity;
import com.example.spacetime.LoginAndRegister.Fragments.FragmentCompleteMessage;
import com.example.spacetime.LoginAndRegister.Fragments.RegisterBeginFragment;
import com.example.spacetime.R;

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
                originFragment = new FragmentCompleteMessage();
                replaceFragment(R.id.register_frameLayout);
                break;
            default:
                replaceFragment(R.id.register_frameLayout);
                break;
        }
    }
}

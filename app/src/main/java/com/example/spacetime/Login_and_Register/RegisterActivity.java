package com.example.spacetime.Login_and_Register;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.spacetime.Components.BasicActivity;
import com.example.spacetime.Login_and_Register.Fragments.FragmentCompleteMessage;
import com.example.spacetime.Login_and_Register.Fragments.RegisterBeginFragment;
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

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.register_frameLayout, fragment);
        transaction.commit();
    }

    private void choosePath(String path){
        switch (path){
            case "registerBegin":
                replaceFragment(new RegisterBeginFragment());
                break;
            case "completeMessage":
                replaceFragment(new FragmentCompleteMessage());
                break;
            default:
                replaceFragment(new Fragment());
                break;
        }
    }
}

package com.haixi.spacetime.LoginAndRegister;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haixi.spacetime.Entity.BasicActivity;
import com.haixi.spacetime.LoginAndRegister.Fragments.GetTelephoneFragment;
import com.haixi.spacetime.LoginAndRegister.Fragments.SmsCodeFragment;
import com.haixi.spacetime.LoginAndRegister.Fragments.ResetPasswordFragment;
import com.haixi.spacetime.LoginAndRegister.Fragments.LoginBeginFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ActivityLoginBinding;

@Route(path = "/spaceTime/login")
public class LoginActivity extends BasicActivity {
    ActivityLoginBinding binding;

    private String[] allPermissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS};

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityList0.add(this);
        setStatusBarColor(this, R.color.colorWhite, false);
        Intent intentFront = getIntent();
        choosePath(intentFront.getStringExtra("path"));
    }

    private void choosePath(String path){
        switch (path){
            case "loginBegin":
                originFragment = new LoginBeginFragment();
                replaceFragment(R.id.login_frameLayout);
                break;
            case "getTelephone":
                originFragment = new GetTelephoneFragment();
                replaceFragment(R.id.login_frameLayout);
                break;
            case "getVerificationCode":
                originFragment = new SmsCodeFragment();
                replaceFragment(R.id.login_frameLayout);
                break;
            case "resetPassword":
                originFragment = new ResetPasswordFragment();
                replaceFragment(R.id.login_frameLayout);
                break;
            default:
                replaceFragment(R.id.login_frameLayout);
                break;
        }
    }
}

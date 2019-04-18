package com.example.spacetime.LoginAndRegister;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.spacetime.Others.BasicActivity;
import com.example.spacetime.LoginAndRegister.Fragments.FragmentGetTelephone;
import com.example.spacetime.LoginAndRegister.Fragments.FragmentGetVerificationCode;
import com.example.spacetime.LoginAndRegister.Fragments.FragmentResetPassword;
import com.example.spacetime.LoginAndRegister.Fragments.LoginBeginFragment;
import com.example.spacetime.R;
import com.example.spacetime.databinding.ActivityLoginBinding;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityList0.add(this);
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
                originFragment = new FragmentGetTelephone();
                replaceFragment(R.id.login_frameLayout);
                break;
            case "getVerificationCode":
                originFragment = new FragmentGetVerificationCode();
                replaceFragment(R.id.login_frameLayout);
                break;
            case "resetPassword":
                originFragment = new FragmentResetPassword();
                replaceFragment(R.id.login_frameLayout);
                break;
            default:
                replaceFragment(R.id.login_frameLayout);
                break;
        }
    }
}

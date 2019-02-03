package com.example.spacetime.Login_and_Register;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.spacetime.Components.BasicActivity;
import com.example.spacetime.Login_and_Register.Fragments.FragmentGetTelephone;
import com.example.spacetime.Login_and_Register.Fragments.FragmentGetVerificationCode;
import com.example.spacetime.Login_and_Register.Fragments.FragmentResetPassword;
import com.example.spacetime.Login_and_Register.Fragments.LoginBeginFragment;
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

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.login_frameLayout, fragment);
        transaction.commit();
    }

    private void choosePath(String path){
        switch (path){
            case "loginBegin":
                replaceFragment(new LoginBeginFragment());
                break;
            case "getTelephone":
                replaceFragment(new FragmentGetTelephone());
                break;
            case "getVerificationCode":
                replaceFragment(new FragmentGetVerificationCode());
                break;
            case "resetPassword":
                replaceFragment(new FragmentResetPassword());
                break;
            default:
                replaceFragment(new Fragment());
                break;
        }
    }
}

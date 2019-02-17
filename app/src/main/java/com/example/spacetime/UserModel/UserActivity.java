package com.example.spacetime.UserModel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.spacetime.Others.BasicActivity;
import com.example.spacetime.R;
import com.example.spacetime.UserModel.Fragments.FragmentEditName;
import com.example.spacetime.UserModel.Fragments.FragmentEditSign;
import com.example.spacetime.UserModel.Fragments.FragmentEditUser;
import com.example.spacetime.UserModel.Fragments.FragmentFeedback;
import com.example.spacetime.UserModel.Fragments.FragmentSetting;
import com.example.spacetime.databinding.ActivityUserBinding;

@Route(path = "/spaceTime/user")
public class UserActivity extends BasicActivity {
    private ActivityUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_user);
        activityList1.add(this);

        Intent intentFront = getIntent();
        String path = intentFront.getStringExtra("path");
        choosePath(path);
    }

    private void choosePath(String path) {
        switch (path){
            case "editUserMessage":
                replaceFragment(new FragmentEditUser());
                break;
            case "feedback":
                replaceFragment(new FragmentFeedback());
                break;
            case "setting":
                replaceFragment(new FragmentSetting());
                break;
            case "editName":
                replaceFragment(new FragmentEditName());
                break;
            case "editSign":
                replaceFragment(new FragmentEditSign());
                break;
            default:
                break;
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.user_frameLayout, fragment);
        transaction.commit();
    }
}

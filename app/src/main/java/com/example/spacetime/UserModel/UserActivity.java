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
                originFragment = new FragmentEditUser();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "feedback":
                originFragment = new FragmentFeedback();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "setting":
                originFragment = new FragmentSetting();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "editName":
                originFragment = new FragmentEditName();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "editSign":
                originFragment = new FragmentEditSign();
                replaceFragment(R.id.user_frameLayout);
                break;
            default:
                break;
        }
    }
}

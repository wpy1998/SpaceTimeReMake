package com.haixi.spacetime.UserModel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haixi.spacetime.Others.BasicActivity;
import com.haixi.spacetime.R;
import com.haixi.spacetime.UserModel.Fragments.FeedbackFragment;
import com.haixi.spacetime.UserModel.Fragments.FragmentEditName;
import com.haixi.spacetime.UserModel.Fragments.FragmentEditSign;
import com.haixi.spacetime.UserModel.Fragments.FragmentEditUser;
import com.haixi.spacetime.UserModel.Fragments.SettingFragment;
import com.haixi.spacetime.databinding.ActivityUserBinding;

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
                originFragment = new FeedbackFragment();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "setting":
                originFragment = new SettingFragment();
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

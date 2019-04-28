package com.haixi.spacetime.UserModel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haixi.spacetime.Common.BasicActivity;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.R;
import com.haixi.spacetime.UserModel.Fragments.FeedbackFragment;
import com.haixi.spacetime.UserModel.Fragments.FragmentEditName;
import com.haixi.spacetime.UserModel.Fragments.FragmentEditSign;
import com.haixi.spacetime.UserModel.Fragments.EditUserFragment;
import com.haixi.spacetime.UserModel.Fragments.SettingFragment;
import com.haixi.spacetime.UserModel.Fragments.UserFragment;
import com.haixi.spacetime.databinding.ActivityUserBinding;

@Route(path = "/spaceTime/user")
public class UserActivity extends BasicActivity {
    private ActivityUserBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_user);
        activityList1.add(this);

        Intent intentFront = getIntent();
        String path = intentFront.getStringExtra("path");
        choosePath(path, intentFront);
    }

    private void choosePath(String path, Intent intent) {
        switch (path){
            case "editUserMessage":
                originFragment = new EditUserFragment();
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
            case "user":
                int userId = intent.getIntExtra("userId", 0);
                User user = new User();
                user.userId = userId;
                originFragment = new UserFragment(user);
                replaceFragment(R.id.user_frameLayout);
                break;
            default:
                replaceFragment(R.id.user_frameLayout);
                break;
        }
    }
}

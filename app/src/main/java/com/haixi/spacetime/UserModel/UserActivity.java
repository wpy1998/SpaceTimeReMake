package com.haixi.spacetime.UserModel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haixi.spacetime.Entity.BasicActivity;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.R;
import com.haixi.spacetime.UserModel.Fragments.EditImageFragment;
import com.haixi.spacetime.UserModel.Fragments.FeedbackFragment;
import com.haixi.spacetime.UserModel.Fragments.EditNameFragment;
import com.haixi.spacetime.UserModel.Fragments.EditSignFragment;
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
        fragmentName = intentFront.getStringExtra("path");
        choosePath(fragmentName, intentFront);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void choosePath(String path, Intent intent) {
        switch (path){
            case "changeUserMessage":
                setStatusBarColor(this, R.color.colorWhite, true);
                originFragment = new EditUserFragment();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "feedback":
                setStatusBarColor(this, R.color.colorWhite, true);
                originFragment = new FeedbackFragment();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "setting":
                setStatusBarColor(this, R.color.colorWhite, true);
                originFragment = new SettingFragment();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "editImage":
                String picturePath = intent.getStringExtra("picturePath");
                setStatusBarColor(this, R.color.colorWhite, true);
                originFragment = new EditImageFragment(picturePath);
                replaceFragment(R.id.user_frameLayout);
                break;
            case "editName":
                setStatusBarColor(this, R.color.colorWhite, true);
                originFragment = new EditNameFragment();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "editSign":
                setStatusBarColor(this, R.color.colorWhite, true);
                originFragment = new EditSignFragment();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "user":
                setStatusBarColor(this, R.color.colorBlue, false);
                User user = new User();
                user.phoneNumber = intent.getStringExtra("userTelephone");
                user.userName = intent.getStringExtra("userName");
                originFragment = new UserFragment(user);
                replaceFragment(R.id.user_frameLayout);
                break;
            default:
                replaceFragment(R.id.user_frameLayout);
                break;
        }
    }
}

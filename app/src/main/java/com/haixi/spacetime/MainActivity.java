package com.haixi.spacetime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haixi.spacetime.CircleModel.Fragments.CircleFragment;
import com.haixi.spacetime.Common.BasicActivity;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.Common.OkHttpAction;
import com.haixi.spacetime.DynamicModel.Fragments.DynamicFragment;
import com.haixi.spacetime.UserModel.Fragments.UserFragment;
import com.haixi.spacetime.databinding.ActivityMainBinding;

import org.json.JSONObject;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Entity.Cookies.owner;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;
import static com.haixi.spacetime.Entity.User.setMessage;

@Route(path = "/spaceTime/main")
public class MainActivity extends BasicActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private DynamicFragment browser;
    private UserFragment personal;
    private CircleFragment circle;
    private final String intentAction = "com.haixi.spacetime.MainActivity";
    private final int intentAction_getUserMessage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        closeL_R_W();
        owner.phoneNumber = phoneNumber;
        browser = new DynamicFragment();
        circle = new CircleFragment();
        personal = new UserFragment(owner);
        originFragment = browser;
        activityList1.add(this);
        drawActivity();

        binding.mainCircle.setOnClickListener(this);
        binding.mainPersonal.setOnClickListener(this);
        binding.mainBrowser.setOnClickListener(this);
        binding.mainB1.setOnClickListener(this);
        binding.mainB2.setOnClickListener(this);
        binding.mainB3.setOnClickListener(this);

        binding.mainBrowser.performClick();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void switchFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!browser.isAdded()){
            transaction.add(R.id.main_fragment, browser);
        }
        if (!personal.isAdded()){
            transaction.add(R.id.main_fragment, personal);
        }
        if (!circle.isAdded()){
            transaction.add(R.id.main_fragment, circle);
        }
        if (originFragment == browser){
            transaction.hide(personal).hide(circle).show(browser);
        }else if (originFragment == circle){
            transaction.hide(personal).hide(browser).show(circle);
        }else {
            transaction.hide(browser).hide(circle).show(personal);
        }
        transaction.commitAllowingStateLoss();
        if (originFragment != browser){
            setStatusBarColor(this, R.color.colorBlue, false);
        }else {
            setStatusBarColor(this, R.color.colorWhite, true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_circle:
                fragmentName = "circle";
                binding.mainCircle.setImageResource(R.drawable.ic_talk_lighting);
                binding.mainBrowser.setImageResource(R.drawable.ic_earth);
                binding.mainPersonal.setImageResource(R.drawable.person);
                originFragment = circle;
                switchFragment();
                circle.refresh();
                break;
            case R.id.main_personal:
                fragmentName = "personal";
                binding.mainCircle.setImageResource(R.drawable.ic_talk);
                binding.mainBrowser.setImageResource(R.drawable.ic_earth);
                binding.mainPersonal.setImageResource(R.drawable.person_lighting);
                originFragment = personal;
                personal.refresh();
                switchFragment();
//                okHttpAction.getUserMessage(phoneNumber, intentAction_getUserMessage, intentAction);
                break;
            case R.id.main_browser:
                fragmentName = "browser";
                binding.mainCircle.setImageResource(R.drawable.ic_talk);
                binding.mainBrowser.setImageResource(R.drawable.ic_earth_lighting);
                binding.mainPersonal.setImageResource(R.drawable.person);
                originFragment = browser;
                switchFragment();
                browser.refresh();
                break;
            case R.id.main_b1:
                binding.mainCircle.performClick();
                break;
            case R.id.main_b2:
                binding.mainBrowser.performClick();
                break;
            case R.id.main_b3:
                binding.mainPersonal.performClick();
                break;
            default:
                break;
        }
    }

    private void drawActivity(){
        setH(binding.mainPartView, 50);

        setHW(binding.mainBrowser, 30, 30);
        setMargin(binding.mainBrowser, 10, 10, 10, 10, true);

        setHW(binding.mainCircle, 30, 30);
        setMargin(binding.mainCircle, 10, 10, 10, 10,
                true);

        setHW(binding.mainPersonal, 30, 30);
        setMargin(binding.mainPersonal, 10, 10, 10, 10,
                true);
    }
}
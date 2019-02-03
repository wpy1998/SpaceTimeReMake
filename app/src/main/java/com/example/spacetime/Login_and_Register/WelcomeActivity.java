package com.example.spacetime.Login_and_Register;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.spacetime.Components.BasicActivity;
import com.example.spacetime.Login_and_Register.Adapter.FragmentAdapter;
import com.example.spacetime.Login_and_Register.Fragments.FragmentWelcome1;
import com.example.spacetime.Login_and_Register.Fragments.FragmentWelcome2;
import com.example.spacetime.Login_and_Register.Fragments.FragmentWelcome3;
import com.example.spacetime.R;
import com.example.spacetime.databinding.ActivityWelcomeBinding;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/spaceTime/welcome")
public class WelcomeActivity extends BasicActivity {
    private ActivityWelcomeBinding binding;
    ViewPager viewPager;
    List<Fragment> fragments;
    FragmentWelcome1 welcome1;
    FragmentWelcome2 welcome2;
    FragmentWelcome3 welcome3;
    FragmentAdapter fragmentAdapter;

    private int fragmentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        activityList0.add(this);
        fragmentNumber = 0;

        initView();
        setAction();
    }

    private void initView() {
        viewPager = findViewById(R.id.welcome_viewPager);
        fragments = new ArrayList<Fragment>();
        welcome1 = new FragmentWelcome1();
        welcome2 = new FragmentWelcome2();
        welcome3 = new FragmentWelcome3();
        fragments.add(welcome1);
        fragments.add(welcome2);
        fragments.add(welcome3);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
    }

    private void setAction() {
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                fragmentNumber = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}

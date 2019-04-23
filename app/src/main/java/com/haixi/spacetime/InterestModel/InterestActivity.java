package com.haixi.spacetime.InterestModel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haixi.spacetime.Others.BasicActivity;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ActivityInterestBinding;

@Route(path = "/spaceTime/interest")
public class InterestActivity extends BasicActivity {
    private ActivityInterestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_interest);

        Intent intentFront = getIntent();
        String path = intentFront.getStringExtra("path");
        choosePath(path);
    }

    private void choosePath(String path) {
        switch (path){
            default:
                replaceFragment(R.id.interest_frameLayout);
                break;
        }
    }
}

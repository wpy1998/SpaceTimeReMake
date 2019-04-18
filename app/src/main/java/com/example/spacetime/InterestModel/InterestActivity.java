package com.example.spacetime.InterestModel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.example.spacetime.Others.BasicActivity;
import com.example.spacetime.R;
import com.example.spacetime.databinding.ActivityInterestBinding;

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

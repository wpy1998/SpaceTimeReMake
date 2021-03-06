package com.haixi.spacetime.CircleModel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haixi.spacetime.CircleModel.Fragments.AddCircleFragment;
import com.haixi.spacetime.CircleModel.Fragments.CircleMessageFragment;
import com.haixi.spacetime.CircleModel.Fragments.CreateCircleFragment;
import com.haixi.spacetime.Entity.BasicActivity;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ActivityCircleBinding;

@Route(path = "/spaceTime/circle")
public class CircleActivity extends BasicActivity {
    private ActivityCircleBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_circle);
        setStatusBarColor(this, R.color.colorBlue, false);

        Intent intentFront = getIntent();
        String path = intentFront.getStringExtra("path");
        choosePath(path);
    }

    private void choosePath(String path) {
        switch (path){
            case "addCircle":
                originFragment = new AddCircleFragment();
                replaceFragment(R.id.circle_frameLayout);
                break;
            case "circleMessage":
                Circle circle = new Circle();
                circle.id = getIntent().getIntExtra("circleId", 0);
                circle.name = getIntent().getStringExtra("circleName");
                originFragment = new CircleMessageFragment(circle);
                replaceFragment(R.id.circle_frameLayout);
                break;
            case "createCircle":
                originFragment = new CreateCircleFragment();
                replaceFragment(R.id.circle_frameLayout);
                break;
            default:
                replaceFragment(R.id.circle_frameLayout);
                break;
        }
    }
}

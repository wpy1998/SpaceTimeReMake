package com.haixi.spacetime.DynamicModel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haixi.spacetime.Common.Components.BasicActivity;
import com.haixi.spacetime.R;
import com.haixi.spacetime.DynamicModel.Fragments.FragmentAddDynamic;
import com.haixi.spacetime.databinding.ActivityDynamicBinding;

@Route(path = "/spaceTime/topic")
public class DynamicActivity extends BasicActivity {
    private ActivityDynamicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_dynamic);

        Intent intentFront = getIntent();
        String path = intentFront.getStringExtra("path");

        choosePath(path);
    }

    private void choosePath(String path){
        switch (path){
            case "addDynamic":
                originFragment = new FragmentAddDynamic();
                replaceFragment(R.id.topic_frameLayout);
                break;
            default:
                replaceFragment(R.id.topic_frameLayout);
                break;
        }
    }
}

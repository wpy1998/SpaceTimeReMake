package com.haixi.spacetime.DynamicModel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Common.BasicActivity;
import com.haixi.spacetime.DynamicModel.Fragments.CommentFragment;
import com.haixi.spacetime.DynamicModel.Fragments.FollowFragment;
import com.haixi.spacetime.Entity.Dynamic;
import com.haixi.spacetime.R;
import com.haixi.spacetime.DynamicModel.Fragments.AddDynamicFragment;
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

    @Autowired(name = "dynamic")
    private Dynamic dynamic;

    private void choosePath(String path){
        switch (path){
            case "addDynamic":
                originFragment = new AddDynamicFragment();
                replaceFragment(R.id.topic_frameLayout);
                break;
            case "follow":
                originFragment = new FollowFragment();
                replaceFragment(R.id.topic_frameLayout);
            case "comment":
                ARouter.getInstance().inject(this);
                originFragment = new CommentFragment(dynamic);
                replaceFragment(R.id.topic_frameLayout);
            default:
                replaceFragment(R.id.topic_frameLayout);
                break;
        }
    }
}

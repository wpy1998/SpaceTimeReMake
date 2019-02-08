package com.example.spacetime.TopicModel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.spacetime.Others.BasicActivity;
import com.example.spacetime.R;
import com.example.spacetime.TopicModel.Fragments.FragmentAddDynamic;
import com.example.spacetime.databinding.ActivityTopicBinding;

@Route(path = "/spaceTime/topic")
public class TopicActivity extends BasicActivity {
    private ActivityTopicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_topic);

        Intent intentFront = getIntent();
        String path = intentFront.getStringExtra("path");

        choosePath(path);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.topic_frameLayout,fragment);
        transaction.commit();
    }

    private void choosePath(String path){
        switch (path){
            case "addDynamic":
                replaceFragment(new FragmentAddDynamic());
                break;
            default:
                replaceFragment(new Fragment());
                break;
        }
    }
}

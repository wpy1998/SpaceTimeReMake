package com.example.spacetime.TopicModel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.spacetime.R;
import com.example.spacetime.databinding.ActivityUserBinding;

public class TopicActivity extends AppCompatActivity {
    private ActivityUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_topic);

        Intent intentFront = getIntent();
        String path = intentFront.getStringExtra("path");

        choosePath(path);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_change_animation, 0, 0, 0);
        transaction.replace(R.id.topic_frameLayout,fragment);
        transaction.commit();
    }

    private void choosePath(String path){
        switch (path){
            default:
                break;
        }
    }
}

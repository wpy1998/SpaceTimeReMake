package com.example.spacetime;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.spacetime.Components.BasicActivity;
import com.example.spacetime.UserModel.Fragments.FragmentUser;
import com.example.spacetime.databinding.ActivityMainBinding;

@Route(path = "/spaceTime/main")
public class MainActivity extends BasicActivity implements View.OnClickListener {
    private ActivityMainBinding binding;

    Fragment fragmentTopic;
    Fragment fragmentConversation;
    boolean isUser = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        closeL_R_W();
        activityList1.add(this);

        binding.mainConversation.setOnClickListener(this);
        binding.mainPersonal.setOnClickListener(this);
        binding.mainFunction.setOnClickListener(this);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_conversation:
                binding.mainConversation.setImageResource(R.drawable.conversation_lighting);
                binding.mainPersonal.setImageResource(R.drawable.person);
                replaceFragment(new Fragment());
                break;
            case R.id.main_personal:
                binding.mainConversation.setImageResource(R.drawable.conversation);
                binding.mainPersonal.setImageResource(R.drawable.person_lighting);
                replaceFragment(new FragmentUser());
                break;
            case R.id.main_function:
                binding.mainConversation.setImageResource(R.drawable.conversation);
                binding.mainPersonal.setImageResource(R.drawable.person);
                replaceFragment(new Fragment());
                break;
            default:
                break;
        }
    }
}
package com.haixi.spacetime;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haixi.spacetime.CircleModel.Fragments.CircleFragment;
import com.haixi.spacetime.Common.Components.BasicActivity;
import com.haixi.spacetime.DynamicModel.Fragments.DynamicFragment;
import com.haixi.spacetime.UserModel.Fragments.UserFragment;
import com.haixi.spacetime.databinding.ActivityMainBinding;

import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Common.Settings.setHW;

@Route(path = "/spaceTime/main")
public class MainActivity extends BasicActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        closeL_R_W();
        activityList1.add(this);
        drawActivity();

        binding.mainConversation.setOnClickListener(this);
        binding.mainPersonal.setOnClickListener(this);
        binding.mainBrowser.setOnClickListener(this);
        binding.mainB1.setOnClickListener(this);
        binding.mainB2.setOnClickListener(this);
        binding.mainB3.setOnClickListener(this);

        binding.mainBrowser.performClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_conversation:
                binding.mainConversation.setImageResource(R.drawable.ic_talk_lighting);
                binding.mainBrowser.setImageResource(R.drawable.ic_earth);
                binding.mainPersonal.setImageResource(R.drawable.person);
                originFragment = new CircleFragment();
                replaceFragment(R.id.main_fragment);
                break;
            case R.id.main_personal:
                binding.mainConversation.setImageResource(R.drawable.ic_talk);
                binding.mainBrowser.setImageResource(R.drawable.ic_earth);
                binding.mainPersonal.setImageResource(R.drawable.person_lighting);
                originFragment = new UserFragment();
                replaceFragment(R.id.main_fragment);
                break;
            case R.id.main_browser:
                binding.mainConversation.setImageResource(R.drawable.ic_talk);
                binding.mainBrowser.setImageResource(R.drawable.ic_earth_lighting);
                binding.mainPersonal.setImageResource(R.drawable.person);
                originFragment = new DynamicFragment();
                replaceFragment(R.id.main_fragment);
                break;
            case R.id.main_b1:
                binding.mainConversation.performClick();
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
        binding.mainPartView.getLayoutParams().height = getPx(40);

        setHW(binding.mainBrowser, 30, 30);
        setMargin(binding.mainBrowser, 5, 5, 5, 5, true);

        setHW(binding.mainConversation, 30, 30);
        setMargin(binding.mainConversation, 5, 5, 5, 5,
                true);

        setHW(binding.mainPersonal, 30, 30);
        setMargin(binding.mainPersonal, 5, 5, 5, 5,
                true);
    }
}
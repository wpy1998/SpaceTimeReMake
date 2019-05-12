package com.haixi.spacetime.LoginAndRegister.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Entity.BasicFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentWelcomeBinding;

import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setTextSize;

public class WelcomeFragment extends BasicFragment implements View.OnClickListener {
    private FragmentWelcomeBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome,
                null, false);
        drawFragment();

        binding.welcomeT1.setOnClickListener(this);
        binding.welcomeT2.setOnClickListener(this);
        return binding.getRoot();
    }

    private void drawFragment(){
        setHW(binding.fragmentWelcomeLogo, 151, 151);
        setMargin(binding.fragmentWelcomeLogo, 110, 151, 110, 0, true);

        setTextSize(binding.welcomeT1, 30);

        setMargin(binding.welcomeT2, 0, 0, 0, 20, true);
        setTextSize(binding.welcomeT2, 20);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.welcome_t1:
                ARouter.getInstance()
                        .build("/spaceTime/start")
                        .withInt("type", 1)
                        .navigation();
                break;
            case R.id.welcome_t2:
                ARouter.getInstance()
                        .build("/spaceTime/start")
                        .withInt("type", 1)
                        .navigation();
                break;
            default:
                break;
        }
    }
}

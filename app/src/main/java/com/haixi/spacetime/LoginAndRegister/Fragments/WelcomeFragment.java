package com.haixi.spacetime.LoginAndRegister.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Others.BasicFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentWelcomeBinding;

import static com.haixi.spacetime.Others.Settings.adaptView;
import static com.haixi.spacetime.Others.Settings.setH;
import static com.haixi.spacetime.Others.Settings.setHW;
import static com.haixi.spacetime.Others.Settings.setTextSize;

public class WelcomeFragment extends BasicFragment implements View.OnClickListener {
    private FragmentWelcomeBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome,
                null, false);
        drawFragment();

        binding.welcomeLogin.setOnClickListener(this);
        return binding.getRoot();
    }

    private void drawFragment(){
        setH(binding.welcomeLogo, 130);
        adaptView(binding.welcomeLogo, 0, 246, 0, 0, true);
        setTextSize(binding.welcomeLogo, 32);

        setHW(binding.welcomeLogin, 50, 222);
        adaptView(binding.welcomeLogin, 77, 0, 76,32, true);
        setTextSize(binding.welcomeLogin, 16);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.welcome_login:
                ARouter.getInstance()
                        .build("/spaceTime/main")
                        .navigation();
                break;
            default:
                break;
        }
    }
}

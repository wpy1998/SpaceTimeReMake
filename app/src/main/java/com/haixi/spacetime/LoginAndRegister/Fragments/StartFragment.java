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
import com.haixi.spacetime.databinding.FragmentStartBinding;

import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.setTextSize;

public class StartFragment extends BasicFragment implements View.OnClickListener {
    private FragmentStartBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, null,
                false);

        drawFragment();

        binding.startLogin.setOnClickListener(this);
        binding.startRegister.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_login:
                ARouter.getInstance()
                        .build("/spaceTime/login")
                        .withString("path", "loginBegin")
                        .navigation();
                break;
            case R.id.start_register:
                ARouter.getInstance()
                        .build("/spaceTime/register")
                        .withString("path", "registerBegin")
                        .navigation();
                break;
            default:
                break;
        }
    }

    private void drawFragment(){
        setHW(binding.fragmentStartLogo, 151, 151);
        setMargin(binding.fragmentStartLogo, 110, 100, 110, 0, true);

        setTextSize(binding.fragmentStartT1, 30);

        setMargin(binding.fragmentStartT2, 0, 10, 0, 20, true);
        setTextSize(binding.fragmentStartT2, 20);

        setHW(binding.startRegister, 50, 278);
        setMargin(binding.startRegister, 49, 0, 48, 0, true);
        setTextSize(binding.startRegister, 16);

        setH(binding.startLogin, 23);
        setMargin(binding.startLogin, 0, 20, 0, 20, true);
        setTextSize(binding.startLogin, 16);
    }
}

package com.haixi.spacetime.DynamicModel.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.DynamicModel.Components.TagComponent;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.Entity.Dynamic;
import com.haixi.spacetime.R;
import com.haixi.spacetime.DynamicModel.Components.DynamicComponent;
import com.haixi.spacetime.UserModel.UserActivity;
import com.haixi.spacetime.databinding.FragmentSocialBinding;

import java.util.List;

import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.setW;
import static com.haixi.spacetime.Entity.Cookies.resultCode;

@SuppressLint("ValidFragment")
public class SocialFragment extends BasicFragment{
    private FragmentSocialBinding binding;
    public List<Dynamic> dynamics;
    public Circle circle;

    public SocialFragment(Circle circle, List<Dynamic> dynamics){
        this.circle = circle;
        this.dynamics = dynamics;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_social,
                null, false);
        refreshDynamic();
        drawFragment();
        return binding.getRoot();
    }

    private void refreshDynamic(){
        binding.fragmentSocialMainView.removeAllViews();
        for (int i = dynamics.size() - 1; i >= 0; i--){
            Dynamic dynamic = dynamics.get(i);
            DynamicComponent dynamicComponent =
                    new DynamicComponent(getContext(),dynamic, null);
            binding.fragmentSocialMainView.addView(dynamicComponent);
            refreshDynamic_addAction(dynamicComponent);
        }
    }

    private void refreshDynamic_addAction(final DynamicComponent dynamicComponent){
        dynamicComponent.titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserActivity.class);
                intent.putExtra("path", "user");
                intent.putExtra("userTelephone", dynamicComponent.dynamic.user.phoneNumber);
                intent.putExtra("userName", dynamicComponent.dynamic.user.userName);
                startActivityForResult(intent, resultCode);
            }
        });

        dynamicComponent.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/spaceTime/dynamic")
                        .withString("path", "comment")
                        .withString("dynamic", dynamicComponent.dynamic.getJSONString())
                        .navigation();
            }
        });
    }

    private void drawFragment(){
        setW(binding.fragmentSocialMainView, 375);
    }
}

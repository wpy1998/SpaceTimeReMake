package com.haixi.spacetime.DynamicModel.Fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haixi.spacetime.Entity.Cookies;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.DynamicModel.Components.TagComponent;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.Entity.Dynamic;
import com.haixi.spacetime.R;
import com.haixi.spacetime.DynamicModel.Components.DynamicComponent;
import com.haixi.spacetime.databinding.FragmentSocialBinding;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Entity.Cookies.owner;

@SuppressLint("ValidFragment")
public class SocialFragment extends BasicFragment{
    private FragmentSocialBinding binding;
    private final String intentAction = "com.haixi.spacetime.DynamicModel" +
            ".Fragments.SocialFragment";
    private final int intentAction_selectTag = 1, intentAction_getDynamic = 2;
    private User user;
    private String currentTag = "全部";
    private List<Dynamic> dynamics;

    public SocialFragment(){
        this.user = null;
    }

    public SocialFragment(User user){
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_social,
                null, false);
        drawFragment();
        userInfoBroadcastReceiver = new ControlBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);
        dynamics = new ArrayList<Dynamic>();

        refreshDynamic();
        if (user != null){
            binding.fragmentSocialView.removeView(binding.fragmentSocialTagView);
            binding.fragmentSocialView.removeView(binding.fragmentSocialTop);
            return binding.getRoot();
        }
        refreshTag();
        return binding.getRoot();
    }

    private void refreshDynamic(){
        binding.fragmentSocialMainView.removeAllViews();
        if (user != null){
            for (Dynamic dynamic: dynamics){
                DynamicComponent dynamicComponent = new DynamicComponent(getContext(),dynamic);
                binding.fragmentSocialMainView.addView(dynamicComponent);
            }
        }else {
            for (Dynamic dynamic: dynamics){
                if (dynamic.isTag(currentTag)) {
                    DynamicComponent dynamicComponent = new DynamicComponent(getContext(),dynamic);
                    binding.fragmentSocialMainView.addView(dynamicComponent);
                }
            }
        }
    }

    private class ControlBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(intentAction)){
                return;
            }
            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_selectTag:
                    currentTag = intent.getStringExtra("name");
                    refreshDynamic();
                    break;
                case intentAction_getDynamic:
                    break;
                default:
                    break;
            }
        }
    }

    //user！=owner时使用
    private void refreshTag(){
        binding.fragmentSocialTagView.removeAllViews();
        for (String tag: Cookies.tags){
            TagComponent tagComponent = new TagComponent(getContext(), tag);
            tagComponent.setIntent(intentAction, 0);
            binding.fragmentSocialTagView.addView(tagComponent);
            if (tagComponent.getName().equals(currentTag)) {
                tagComponent.refresh();
            }
        }
    }

    //非User时使用
    private void drawFragment(){
        setMargin(binding.fragmentSocialTop, 0, 43,
                0, 0, false);
    }
}

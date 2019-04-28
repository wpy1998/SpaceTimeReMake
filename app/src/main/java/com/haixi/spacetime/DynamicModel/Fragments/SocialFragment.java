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

import com.haixi.spacetime.Common.Entity.User;
import com.haixi.spacetime.DynamicModel.Components.TagComponent;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.DynamicModel.Entity.Dynamic;
import com.haixi.spacetime.DynamicModel.Entity.DynamicCookies;
import com.haixi.spacetime.R;
import com.haixi.spacetime.DynamicModel.Components.DynamicContentView;
import com.haixi.spacetime.databinding.FragmentSocialBinding;

import java.util.List;

import static com.haixi.spacetime.Common.Entity.Cookies.ownerId;
import static com.haixi.spacetime.Common.Settings.setMargin;

@SuppressLint("ValidFragment")
public class SocialFragment extends BasicFragment{
    private FragmentSocialBinding binding;
    private List<TagComponent> tagComponents;
    private final String intentAction = "com.haixi.spacetime.DynamicModel" +
            ".Fragments.SocialFragment";
    private User user;

    public SocialFragment(){
        user = new User();
        user.userId = -2;
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

        refreshDynamic();

        if (user.userId == ownerId) return binding.getRoot();

        refreshTag();

        return binding.getRoot();
    }

    private void addDynamicContent(Dynamic dynamic){
        DynamicContentView dynamicContentView = new DynamicContentView(getContext(),dynamic);
        binding.fragmentSocialMainView.addView(dynamicContentView);
    }

    private void refreshDynamic(){
        binding.fragmentSocialMainView.removeAllViews();
        int number = 0;
        for (Dynamic dynamic: DynamicCookies.circleDynamics){
            if (number == 10) break;
            if (dynamic.isTag(DynamicCookies.currentTag)){
                addDynamicContent(dynamic);
                number++;
            }
        }
    }

    private void refreshTag(){
        binding.fragmentSocialTagView.removeAllViews();
        for (String tag: DynamicCookies.tags){
            TagComponent tagComponent = new TagComponent(getContext(), tag);
            tagComponent.setIntent(intentAction);
            binding.fragmentSocialTagView.addView(tagComponent);
            if (tagComponent.getName().equals(DynamicCookies.currentTag)) {
                tagComponent.refresh();
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
            refreshDynamic();
        }
    }

    private void drawFragment(){
        if (user.userId != ownerId){
            setMargin(binding.fragmentSocialTop, 0, 43,
                    0, 0, false);
        }
    }
}

package com.haixi.spacetime.DynamicModel.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.DynamicModel.Components.DynamicContentView;
import com.haixi.spacetime.DynamicModel.Components.UserComponent;
import com.haixi.spacetime.DynamicModel.Entity.Dynamic;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentFollowBinding;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Common.Settings.setMargin;

public class FollowFragment extends BasicFragment {
    private FragmentFollowBinding binding;
    private final String intentAction = "com.haixi.spacetime.DynamicModel" +
            ".Fragments.FollowFragment";
    private List<Dynamic> dynamics;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_follow,
                null, false);
        drawFragment();
        dynamics = new ArrayList<Dynamic>();

        String s1 = "吃鸡党", s2 = "大社联", s3 = "俱乐部", s4 = "潮牌", s5 = "王者荣耀";
        Dynamic dynamic1 = new Dynamic(0);
        dynamic1.imageId = R.drawable.jack;
        dynamic1.tags = new ArrayList<>();
        dynamic1.tags.add(s1);
        dynamic1.tags.add(s2);
        dynamics.add(dynamic1);
        addDynamicContent(dynamic1);

        Dynamic dynamic2 = new Dynamic(0);
        dynamic2.imageId = R.drawable.william;
        dynamic2.tags = new ArrayList<>();
        dynamic2.tags.add(s1);
        dynamic2.tags.add(s3);
        dynamics.add(dynamic2);
        addDynamicContent(dynamic2);

        Dynamic dynamic3 = new Dynamic(0);
        dynamic3.imageId = R.drawable.daniel;
        dynamic3.tags = new ArrayList<>();
        dynamic3.tags.add(s1);
        dynamic3.tags.add(s4);
        dynamics.add(dynamic3);
        addDynamicContent(dynamic3);

        Dynamic dynamic4 = new Dynamic(0);
        dynamic4.imageId = R.drawable.jack;
        dynamic4.tags = new ArrayList<>();
        dynamic4.tags.add(s2);
        dynamic4.tags.add(s3);
        dynamics.add(dynamic4);
        addDynamicContent(dynamic4);

        Dynamic dynamic5 = new Dynamic(0);
        dynamic5.imageId = R.drawable.william;
        dynamic5.tags = new ArrayList<>();
        dynamic5.tags.add(s2);
        dynamic5.tags.add(s4);
        dynamics.add(dynamic5);
        addDynamicContent(dynamic5);

        Dynamic dynamic6 = new Dynamic(0);
        dynamic6.imageId = R.drawable.daniel;
        dynamic6.tags = new ArrayList<>();
        dynamic6.tags.add(s3);
        dynamic6.tags.add(s4);
        dynamics.add(dynamic6);
        addDynamicContent(dynamic6);

        binding.fragmentFollowUserView.addView(new UserComponent(getContext()));
        binding.fragmentFollowUserView.addView(new UserComponent(getContext()));
        binding.fragmentFollowUserView.addView(new UserComponent(getContext()));
        binding.fragmentFollowUserView.addView(new UserComponent(getContext()));
        binding.fragmentFollowUserView.addView(new UserComponent(getContext()));
        binding.fragmentFollowUserView.addView(new UserComponent(getContext()));
        binding.fragmentFollowUserView.addView(new UserComponent(getContext()));
        binding.fragmentFollowUserView.addView(new UserComponent(getContext()));
        return binding.getRoot();
    }

    private void addDynamicContent(Dynamic dynamic){
        DynamicContentView dynamicContentView = new DynamicContentView(getContext(),dynamic);
        dynamics.add(dynamic);
        binding.fragmentFollowMainView.addView(dynamicContentView);
    }

    private void drawFragment(){
        setMargin(binding.fragmentFollowTop,0,43,0,0,false);
    }
}

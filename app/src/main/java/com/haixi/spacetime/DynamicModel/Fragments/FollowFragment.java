package com.haixi.spacetime.DynamicModel.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.DynamicModel.Components.DynamicComponent;
import com.haixi.spacetime.Entity.Dynamic;
import com.haixi.spacetime.Entity.User;
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
        return binding.getRoot();
    }

    private void addDynamicContent(Dynamic dynamic){
        DynamicComponent dynamicComponent = new DynamicComponent(getContext(),dynamic, null);
        dynamics.add(dynamic);
        binding.fragmentFollowMainView.addView(dynamicComponent);
    }

    private void drawFragment(){
        setMargin(binding.fragmentFollowTop,0,43,0,0,false);
    }
}

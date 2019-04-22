package com.haixi.spacetime.DynamicModel.Fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haixi.spacetime.Others.BasicFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.Components.DynamicContentView;
import com.haixi.spacetime.databinding.FragmentDynamic2Binding;

import static com.haixi.spacetime.Others.Settings.setMargin;

@SuppressLint("ValidFragment")
public class Dynamic2Fragment extends BasicFragment{
    private FragmentDynamic2Binding binding;
    private boolean isUserView;

    public Dynamic2Fragment(){
        isUserView = false;
    }

    public Dynamic2Fragment(boolean isUserView){
        this.isUserView = isUserView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dynamic2,
                null, false);
        drawFragment();
        binding.fragmentDynamic2MainView.addView(new DynamicContentView(getContext(),
                isUserView));
        binding.fragmentDynamic2MainView.addView(new DynamicContentView(getContext(),
                isUserView));
        binding.fragmentDynamic2MainView.addView(new DynamicContentView(getContext(),
                isUserView));
        binding.fragmentDynamic2MainView.addView(new DynamicContentView(getContext(),
                isUserView));
        binding.fragmentDynamic2MainView.addView(new DynamicContentView(getContext(),
                isUserView));
        binding.fragmentDynamic2MainView.addView(new DynamicContentView(getContext(),
                isUserView));

        return binding.getRoot();
    }

    private void drawFragment(){
        if (isUserView)
        setMargin(binding.fragmentDynamic2Top, 0, 43,
                0, 0, false);
    }
}

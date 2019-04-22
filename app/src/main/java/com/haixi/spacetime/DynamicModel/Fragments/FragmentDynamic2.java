package com.haixi.spacetime.DynamicModel.Fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haixi.spacetime.R;
import com.haixi.spacetime.Components.DynamicContentView;
import com.haixi.spacetime.databinding.FragmentDynamic2Binding;

@SuppressLint("ValidFragment")
public class FragmentDynamic2 extends Fragment {
    private FragmentDynamic2Binding binding;
    private boolean isUserView;

    public FragmentDynamic2(){
        isUserView = false;
    }

    public FragmentDynamic2(boolean isUserView){
        this.isUserView = isUserView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dynamic2,
                null, false);
        binding.fragmentDynamicMainView.addView(new DynamicContentView(getContext(),
                isUserView));
        binding.fragmentDynamicMainView.addView(new DynamicContentView(getContext(),
                isUserView));
        binding.fragmentDynamicMainView.addView(new DynamicContentView(getContext(),
                isUserView));
        binding.fragmentDynamicMainView.addView(new DynamicContentView(getContext(),
                isUserView));
        binding.fragmentDynamicMainView.addView(new DynamicContentView(getContext(),
                isUserView));
        binding.fragmentDynamicMainView.addView(new DynamicContentView(getContext(),
                isUserView));
        return binding.getRoot();
    }
}

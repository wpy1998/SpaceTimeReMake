package com.example.spacetime.Fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spacetime.R;
import com.example.spacetime.Components.DynamicContentView;
import com.example.spacetime.databinding.FragmentDynamicBinding;

@SuppressLint("ValidFragment")
public class FragmentDynamic extends Fragment {
    private FragmentDynamicBinding binding;
    private boolean isUserView;

    public FragmentDynamic(){
        isUserView = false;
    }

    public FragmentDynamic(boolean isUserView){
        this.isUserView = isUserView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dynamic,
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

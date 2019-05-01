package com.haixi.spacetime.DynamicModel.Fragments;

import android.annotation.SuppressLint;
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
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentCommentBinding;

@SuppressLint("ValidFragment")
public class CommentFragment extends BasicFragment {
    private FragmentCommentBinding binding;
    private Dynamic dynamic;
    public CommentFragment(Dynamic dynamic){
        this.dynamic = dynamic;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment,
                null, false);
        binding.fragmentCommentMainView.
                addView(new DynamicComponent(getContext(), dynamic, null));
        return binding.getRoot();
    }
}

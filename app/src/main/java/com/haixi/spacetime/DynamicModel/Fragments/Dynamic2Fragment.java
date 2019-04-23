package com.haixi.spacetime.DynamicModel.Fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haixi.spacetime.DynamicModel.Components.TagView;
import com.haixi.spacetime.Others.BasicFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.Components.DynamicContentView;
import com.haixi.spacetime.databinding.FragmentDynamic2Binding;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Others.Settings.setMargin;

@SuppressLint("ValidFragment")
public class Dynamic2Fragment extends BasicFragment{
    private FragmentDynamic2Binding binding;
    private boolean isUserView;

    private List<TagView> tagViews;

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
        if (!isUserView) return binding.getRoot();

        TagView tagView = new TagView(getContext(), "全部");
        TagView tagView1 = new TagView(getContext(), "吃鸡党");
        TagView tagView2 = new TagView(getContext(), "大社联");
        TagView tagView3 = new TagView(getContext(), "俱乐部");
        TagView tagView4 = new TagView(getContext(), "潮牌");
        TagView tagView5 = new TagView(getContext(), "王者荣耀");
        tagView.refresh();

        binding.fragmentDynamic2TagView.addView(tagView);
        binding.fragmentDynamic2TagView.addView(tagView1);
        binding.fragmentDynamic2TagView.addView(tagView2);
        binding.fragmentDynamic2TagView.addView(tagView3);
        binding.fragmentDynamic2TagView.addView(tagView4);
        binding.fragmentDynamic2TagView.addView(tagView5);

        return binding.getRoot();
    }

    private void drawFragment(){
        if (isUserView){
            setMargin(binding.fragmentDynamic2Top, 0, 43,
                    0, 0, false);
        }
    }
}

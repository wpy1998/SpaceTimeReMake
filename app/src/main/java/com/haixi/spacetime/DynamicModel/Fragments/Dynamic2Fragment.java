package com.haixi.spacetime.DynamicModel.Fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haixi.spacetime.DynamicModel.Components.TagComponent;
import com.haixi.spacetime.Others.BasicFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.Components.DynamicContentView;
import com.haixi.spacetime.databinding.FragmentDynamic2Binding;

import java.util.List;

import static com.haixi.spacetime.Others.Settings.setMargin;

@SuppressLint("ValidFragment")
public class Dynamic2Fragment extends BasicFragment{
    private FragmentDynamic2Binding binding;
    private boolean isUserView;

    private List<TagComponent> tagComponents;

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

        TagComponent tagComponent = new TagComponent(getContext(), "全部");
        TagComponent tagComponent1 = new TagComponent(getContext(), "吃鸡党");
        TagComponent tagComponent2 = new TagComponent(getContext(), "大社联");
        TagComponent tagComponent3 = new TagComponent(getContext(), "俱乐部");
        TagComponent tagComponent4 = new TagComponent(getContext(), "潮牌");
        TagComponent tagComponent5 = new TagComponent(getContext(), "王者荣耀");
        tagComponent.refresh();

        binding.fragmentDynamic2TagView.addView(tagComponent);
        binding.fragmentDynamic2TagView.addView(tagComponent1);
        binding.fragmentDynamic2TagView.addView(tagComponent2);
        binding.fragmentDynamic2TagView.addView(tagComponent3);
        binding.fragmentDynamic2TagView.addView(tagComponent4);
        binding.fragmentDynamic2TagView.addView(tagComponent5);

        return binding.getRoot();
    }

    private void drawFragment(){
        if (isUserView){
            setMargin(binding.fragmentDynamic2Top, 0, 43,
                    0, 0, false);
        }
    }
}

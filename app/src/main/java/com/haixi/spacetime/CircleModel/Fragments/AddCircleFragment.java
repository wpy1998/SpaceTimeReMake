package com.haixi.spacetime.CircleModel.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.CircleModel.Components.CircleComponent;
import com.haixi.spacetime.Entity.BasicFragment;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentAddCircleBinding;

import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.setTextSize;

public class AddCircleFragment extends BasicFragment{
    private FragmentAddCircleBinding binding;
    private LinearLayout mainView;
    private CircleComponent addCircleID , createCircle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_circle,
                null, false);
        mainView = binding.getRoot().findViewById(R.id.titleSecondCircle_container);
        addCircleID = new CircleComponent(getContext(), new Circle("扫码添加"));
        createCircle = new CircleComponent(getContext(), new Circle("创建圈子"));
        mainView.addView(addCircleID);
        mainView.addView(createCircle);

        binding.addCircleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        createCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/spaceTime/circle")
                        .withString("path", "createCircle")
                        .navigation();
            }
        });
        addCircleID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/spaceTime/code")
                        .navigation();
            }
        });
        drawFragment();
        return binding.getRoot();
    }

    private void drawFragment(){
        setH(binding.addCircleTitle, 40);
        setMargin(binding.addCircleTitle, 0, 10, 0, 10, false);
        setTextSize(binding.addCircleTitle, 24);

        setHW(binding.addCircleLeft, 24, 24);
        setMargin(binding.addCircleLeft, 15, 18, 15, 18, false);

        setHW(binding.addCircleRight, 24, 24);
        setMargin(binding.addCircleRight, 15, 18, 15, 18, false);

        setMargin(binding.getRoot().findViewById(R.id.titleSecondCircle_container),
                15, 0, 15, 0, false);
    }
}

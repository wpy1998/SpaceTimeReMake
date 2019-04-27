package com.haixi.spacetime.CircleModel.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentCreateCircleBinding;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.setTextSize;
import static com.haixi.spacetime.Common.Settings.setW;

public class CreateCircleFragment extends BasicFragment implements View.OnClickListener {
    private FragmentCreateCircleBinding binding;
    private LinearLayout mainView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_circle,
                null, false);
        mainView = binding.getRoot().findViewById(R.id.titleSecondCircle_container);

        drawFragment();

        binding.createCircleLeft.setOnClickListener(this);
        binding.createCircleSave.setOnClickListener(this);
        return binding.getRoot();
    }

    private void drawFragment(){
        setH(binding.createCircleTitle, 40);
        setMargin(binding.createCircleTitle, 0, 10, 0, 10, false);
        setTextSize(binding.createCircleTitle, 24);

        setHW(binding.createCircleLeft, 24, 24);
        setMargin(binding.createCircleLeft, 15, 18, 15, 18, false);

        setHW(binding.createCircleRight, 24, 24);
        setMargin(binding.createCircleRight, 15, 18, 15, 18, false);

        setMargin(mainView, 15, 0, 15, 0, false);

        setH(binding.createCircleTag, 40);
        setMargin(binding.createCircleTag, 0, 15, 0, 0, true);
        setTextSize(binding.createCircleTag, 24);

        setW(binding.createCircleName, 303);
        setMargin(binding.createCircleName, 36, 60, 36, 0, false);
        setTextSize(binding.createCircleName, 24);

        setHW(binding.createCircleSave, 47, 284);
        setMargin(binding.createCircleSave, 44, 40, 47, 0, false);
        setTextSize(binding.createCircleSave, 20);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createCircle_left:
                getActivity().finish();
                break;
            case R.id.createCircle_save:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}

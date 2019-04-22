package com.haixi.spacetime.InterestModel.Fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentInterestBinding;

import static com.haixi.spacetime.Others.Settings.adaptView;
import static com.haixi.spacetime.Others.Settings.getPx;
import static com.haixi.spacetime.Others.Settings.setHW;
import static com.haixi.spacetime.Others.Settings.setTextSize;

public class FragmentInterest extends Fragment implements View.OnClickListener {
    private FragmentInterestBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_interest,
                null, false);
        drawView();
        addInterest("吃鸡小分队");
        addInterest("王者战队");
        addInterest("大社联");
        addInterest("15届软院学生会");
        addInterest("漫圈");
        addInterest("潮牌圈");
        return binding.getRoot();
    }

    private void drawView() {
        binding.fragmentInterestTitle.getLayoutParams().height = getPx(41);
        adaptView(binding.fragmentInterestTitle, 15, 25, 0,
                9, false);
        setTextSize(binding.fragmentInterestTitle, 24);

        setHW(binding.fragmentInterestAdd, 30, 30);
        adaptView(binding.fragmentInterestAdd, 0, 25, 25,
                15, false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void addInterest(String interest){
        TextView textView = new TextView(getContext());
        binding.fragmentInterestContainer.addView(textView);
        textView.setText(interest);
        textView.getLayoutParams().height = getPx(28);
        textView.setTextColor(Color.parseColor("#000000"));
        adaptView(textView, 15, 16, 15, 16, false);
        setTextSize(textView, 20);
    }
}

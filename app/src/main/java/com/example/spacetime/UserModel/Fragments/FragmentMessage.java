package com.example.spacetime.UserModel.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentMessageBinding;

import static com.example.spacetime.Others.Settings.adaptView;
import static com.example.spacetime.Others.Settings.getPx;
import static com.example.spacetime.Others.Settings.setHW;
import static com.example.spacetime.Others.Settings.setTextSize;
import static com.example.spacetime.Others.Settings.windowsWidth;

public class FragmentMessage extends Fragment {
    private FragmentMessageBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message,
                null, false);
        drawView();
        return binding.getRoot();
    }

    private void drawView() {
        binding.fragmentMessageTagView.getLayoutParams().width = windowsWidth;
        setHW(binding.fragmentMessageTag, 24, 24);
        adaptView(binding.fragmentMessageTag, 10, 14, 10,
                10, false);
        binding.fragmentMessageTagTitle.getLayoutParams().height = getPx(22);
        adaptView(binding.fragmentMessageTagTitle, 0, 16, 0,
                10, false);
        setTextSize(binding.fragmentMessageTagTitle, 16);

        binding.fragmentMessageGraduationView.getLayoutParams().width=windowsWidth;
        setHW(binding.fragmentMessageGraduation, 24, 24);
        adaptView(binding.fragmentMessageGraduation, 12, 10, 10,
                10, false);
        binding.fragmentMessageGraduationTitle.getLayoutParams().height =
                getPx(22);
        adaptView(binding.fragmentMessageGraduationTitle, 0, 11, 0,
                11, false);
        setTextSize(binding.fragmentMessageGraduationTitle, 16);
        adaptView(binding.fragmentMessageGraduationContent, 20, 19,
                0, 19,false);
        setTextSize(binding.fragmentMessageGraduationContent, 16);

        binding.fragmentMessageBagView.getLayoutParams().width=windowsWidth;
        setHW(binding.fragmentMessageBag, 24, 24);
        adaptView(binding.fragmentMessageBag, 12, 10, 10,
                10, false);
        binding.fragmentMessageBagTitle.getLayoutParams().height = getPx(22);
        adaptView(binding.fragmentMessageBagTitle, 0, 11, 0,
                11, false);
        setTextSize(binding.fragmentMessageBagTitle, 16);
        adaptView(binding.fragmentMessageBagContent, 20, 19, 0,
                19,false);
        setTextSize(binding.fragmentMessageBagContent, 16);

        binding.fragmentMessageProjectView.getLayoutParams().width=windowsWidth;
        setHW(binding.fragmentMessageProject, 24, 24);
        adaptView(binding.fragmentMessageProject, 12, 10, 10,
                10, false);
        binding.fragmentMessageProjectTitle.getLayoutParams().height =
                getPx(22);
        adaptView(binding.fragmentMessageProjectTitle, 0, 11, 0,
                11, false);
        setTextSize(binding.fragmentMessageProjectTitle, 16);
        adaptView(binding.fragmentMessageProjectContent, 20, 19, 0,
                19,false);
        setTextSize(binding.fragmentMessageProjectContent, 16);
    }
}
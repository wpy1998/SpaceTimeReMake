package com.haixi.spacetime.UserModel.Fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentMessageBinding;

import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;
import static com.haixi.spacetime.Common.Settings.windowsWidth;
import static com.haixi.spacetime.Entity.Cookies.owner;

@SuppressLint("ValidFragment")
public class MessageFragment extends BasicFragment {
    private FragmentMessageBinding binding;
    private User user;

    public MessageFragment(User user){
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message,
                null, false);
        drawView();
        refresh();
        return binding.getRoot();
    }

    @Override
    public void refresh() {
        binding.fragmentMessageGraduationContent.setText(user.school + " | " + user.major);
        binding.fragmentMessageBagContent.setText(user.profession + " | " + user.position);
        binding.fragmentMessageProjectContent.setText(user.signature);
    }

    private void drawView() {
        binding.fragmentMessageTagView.getLayoutParams().width = windowsWidth;
        setHW(binding.fragmentMessageTag, 24, 24);
        setMargin(binding.fragmentMessageTag, 10, 14, 10,
                10, false);
        binding.fragmentMessageTagTitle.getLayoutParams().height = getPx(22);
        setMargin(binding.fragmentMessageTagTitle, 0, 16, 0,
                10, false);
        setTextSize(binding.fragmentMessageTagTitle, 16);

        binding.fragmentMessageGraduationView.getLayoutParams().width=windowsWidth;
        setHW(binding.fragmentMessageGraduation, 24, 24);
        setMargin(binding.fragmentMessageGraduation, 12, 10, 10,
                10, false);
        binding.fragmentMessageGraduationTitle.getLayoutParams().height =
                getPx(22);
        setMargin(binding.fragmentMessageGraduationTitle, 0, 11, 0,
                11, false);
        setTextSize(binding.fragmentMessageGraduationTitle, 16);
        setMargin(binding.fragmentMessageGraduationContent, 20, 19,
                0, 19,false);
        setTextSize(binding.fragmentMessageGraduationContent, 16);

        binding.fragmentMessageBagView.getLayoutParams().width=windowsWidth;
        setHW(binding.fragmentMessageBag, 24, 24);
        setMargin(binding.fragmentMessageBag, 12, 10, 10,
                10, false);
        binding.fragmentMessageBagTitle.getLayoutParams().height = getPx(22);
        setMargin(binding.fragmentMessageBagTitle, 0, 11, 0,
                11, false);
        setTextSize(binding.fragmentMessageBagTitle, 16);
        setMargin(binding.fragmentMessageBagContent, 20, 19, 0,
                19,false);
        setTextSize(binding.fragmentMessageBagContent, 16);

        binding.fragmentMessageProjectView.getLayoutParams().width=windowsWidth;
        setHW(binding.fragmentMessageProject, 24, 24);
        setMargin(binding.fragmentMessageProject, 12, 10, 10,
                10, false);
        binding.fragmentMessageProjectTitle.getLayoutParams().height =
                getPx(22);
        setMargin(binding.fragmentMessageProjectTitle, 0, 11, 0,
                11, false);
        setTextSize(binding.fragmentMessageProjectTitle, 16);
        setMargin(binding.fragmentMessageProjectContent, 20, 19, 0,
                19,false);
        setTextSize(binding.fragmentMessageProjectContent, 16);
    }
}

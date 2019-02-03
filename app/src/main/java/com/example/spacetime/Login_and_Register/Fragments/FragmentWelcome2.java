package com.example.spacetime.Login_and_Register.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentWelcome2Binding;

public class FragmentWelcome2 extends Fragment {
    private FragmentWelcome2Binding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome2, null, false);
        return binding.getRoot();
    }
}

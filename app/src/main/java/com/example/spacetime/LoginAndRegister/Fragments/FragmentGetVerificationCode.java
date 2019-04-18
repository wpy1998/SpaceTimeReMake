package com.example.spacetime.LoginAndRegister.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentGetVerificationCodeBinding;

public class FragmentGetVerificationCode extends Fragment{
    private FragmentGetVerificationCodeBinding binding;

    private TextView chooseArea;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_get_verification_code,
                null, false);

        chooseArea = binding.verificationCodeContent;
        chooseArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = chooseArea.getText().toString();
                if (content.length() == 4){
                    ARouter.getInstance()
                            .build("/spaceTime/login")
                            .withString("path", "resetPassword")
                            .navigation();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return binding.getRoot();
    }
}

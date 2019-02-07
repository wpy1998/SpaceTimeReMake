package com.example.spacetime.UserModel.Fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.R;
import com.example.spacetime.UserModel.Components.OptionLayoutChoose;
import com.example.spacetime.UserModel.Components.OptionLayoutTurn;
import com.example.spacetime.databinding.FragmentUserBinding;

import static com.example.spacetime.Components.Settings.adaptView;
import static com.example.spacetime.Components.Settings.getPx;
import static com.example.spacetime.Components.Settings.setHW;
import static com.example.spacetime.Components.Settings.setTextSize;

public class FragmentUser extends Fragment implements View.OnClickListener {
    private FragmentUserBinding binding;

    private Button setting;
    private TextView dynamic, message, name, ageLocation;
    private LinearLayout userView, chooseView;
    private ImageView image, gender;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, null, false);
        setting = binding.getRoot().findViewById(R.id.fragment_user_setting);
        dynamic = binding.getRoot().findViewById(R.id.fragment_user_dynamic);
        message = binding.getRoot().findViewById(R.id.fragment_user_message);
        name = binding.getRoot().findViewById(R.id.fragment_user_name);
        ageLocation = binding.getRoot().findViewById(R.id.fragment_user_age_and_loaction);
        userView = binding.getRoot().findViewById(R.id.fragment_user_userView);
        chooseView = binding.getRoot().findViewById(R.id.fragment_user_choose);
        image = binding.getRoot().findViewById(R.id.fragment_user_image);
        gender = binding.getRoot().findViewById(R.id.fragment_user_gender);

        init();
        setting.setOnClickListener(this);
        dynamic.setOnClickListener(this);
        message.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_user_setting:
                ARouter.getInstance()
                        .build("/spaceTime/user")
                        .withString("path", "setting")
                        .navigation();
                break;
            case R.id.fragment_user_dynamic:
                dynamic.setTextColor(Color.parseColor("#3E66FB"));
                message.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.fragment_user_message:
                dynamic.setTextColor(Color.parseColor("#000000"));
                message.setTextColor(Color.parseColor("#3E66FB"));
                break;
            default:
                Toast.makeText(getContext(), "waiting for coming true", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void init() {
        userView.getLayoutParams().height = getPx(118);
        chooseView.getLayoutParams().height = getPx(51);

        setHW(image, 70, 70);
        adaptView(image, 20, 17, 21, 0, false);

        name.getLayoutParams().height = getPx(41);
        setTextSize(name, 16);

        setHW(gender, 24, 24);
        adaptView(gender, 0, 25, 0, 0, false);

        setHW(ageLocation, 22, 73);
        adaptView(ageLocation, 0, 0, 0, 31, false);

        setHW(setting, 33, 57);
        adaptView(setting, 0, 21, 22, 3, false);

        dynamic.getLayoutParams().height = getPx(41);
        setTextSize(dynamic, 16);
    }
}

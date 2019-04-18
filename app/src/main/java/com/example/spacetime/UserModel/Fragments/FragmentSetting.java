package com.example.spacetime.UserModel.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.R;
import com.example.spacetime.UserModel.Components.OptionLayoutChoose;
import com.example.spacetime.UserModel.Components.OptionLayoutTurn;
import com.example.spacetime.databinding.FragmentSettingBinding;

import static com.example.spacetime.Others.BasicActivity.closeCUT;
import static com.example.spacetime.Others.Settings.adaptView;
import static com.example.spacetime.Others.Settings.setHW;

public class FragmentSetting extends Fragment implements
        View.OnClickListener {
    private FragmentSettingBinding binding;

    private TextView title;
    private ImageView back;
    private OptionLayoutTurn editUserMessage, accountAndSafety, feedback, aboutUs;
    private OptionLayoutChoose openNotification;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting,
                null, false);
        title = binding.getRoot().findViewById(R.id.fragment_setting_title);
        back = binding.getRoot().findViewById(R.id.fragment_setting_back);

        editUserMessage = new OptionLayoutTurn(getContext(), "修改个人资料");
        accountAndSafety = new OptionLayoutTurn(getContext(), "账号与安全");
        openNotification = new OptionLayoutChoose(getContext(), "开启推送");
        feedback = new OptionLayoutTurn(getContext(), "意见反馈");
        aboutUs = new OptionLayoutTurn(getContext(), "关于我们");
        binding.fragmentSettingMainView.addView(editUserMessage);
        binding.fragmentSettingMainView.addView(accountAndSafety);
        binding.fragmentSettingMainView.addView(openNotification);
        binding.fragmentSettingMainView.addView(feedback);
        binding.fragmentSettingMainView.addView(aboutUs);

        init();
        binding.fragmentSettingExit.setOnClickListener(this);
        back.setOnClickListener(this);
        editUserMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/spaceTime/user")
                        .withString("path", "editUserMessage")
                        .navigation();
            }
        });

        accountAndSafety.setOnClickListener(this);

        openNotification.binding.optionLayoutChooseGetNotification.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (openNotification.isAllowed){
                            openNotification.isAllowed = false;
                            Toast.makeText(getContext(), "推送已关闭",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            openNotification.isAllowed = true;
                            Toast.makeText(getContext(), "推送已开启",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/spaceTime/user")
                        .withString("path", "feedback")
                        .navigation();
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/spaceTime/forbidden")
                        .navigation();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_setting_exit:
                ARouter.getInstance()
                        .build("/spaceTime/start")
                        .navigation();
                closeCUT();
                break;
            case R.id.fragment_setting_back:
                getActivity().finish();
                break;
            default:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void init() {
        setHW(back, 24, 24);
        adaptView(back, 13, 10, 0, 0, false);

        setHW(title, 52, 61);
        adaptView(title, 20, 0, 0, 3, false);

        setHW(binding.fragmentSettingExit, 50, 160);
        adaptView(binding.fragmentSettingExit, 0, 50, 0, 0, true);

        setHW(binding.fragmentSettingVersion, 23, 85);
        adaptView(binding.fragmentSettingVersion, 0, 10, 0,
                19, true);

        editUserMessage.drawView();
        aboutUs.drawView();
        openNotification.drawView();
        feedback.drawView();
        accountAndSafety.drawView();
    }
}

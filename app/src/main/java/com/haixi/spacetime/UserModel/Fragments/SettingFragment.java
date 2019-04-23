package com.haixi.spacetime.UserModel.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Others.BasicFragment;
import com.haixi.spacetime.Others.OkHttpAction;
import com.haixi.spacetime.R;
import com.haixi.spacetime.UserModel.Components.OptionLayoutChoose;
import com.haixi.spacetime.UserModel.Components.OptionLayoutTurn;
import com.haixi.spacetime.databinding.FragmentSettingBinding;

import static com.haixi.spacetime.Others.BasicActivity.closeCUT;
import static com.haixi.spacetime.Others.Cookies.initCookies;
import static com.haixi.spacetime.Others.Settings.setMargin;
import static com.haixi.spacetime.Others.Settings.setHW;
import static com.haixi.spacetime.Others.Settings.setTextSize;

public class SettingFragment extends BasicFragment implements
        View.OnClickListener {
    private FragmentSettingBinding binding;
    private TextView title;
    private ImageView back;
    private OptionLayoutTurn editUserMessage, accountAndSafety, feedback, aboutUs;
    private OptionLayoutChoose openNotification;

    private final String intentAction = "com.example.spacetime.UserModel.Fragments.SettingFragment";
    private final int intentAction_exitAccount = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting,
                null, false);
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);

        okHttpAction = new OkHttpAction(getContext());

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

        drawFragment();
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
                            Toast.makeText(getContext(), "推送已开启",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            openNotification.isAllowed = true;
                            Toast.makeText(getContext(), "推送已关闭",
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
                okHttpAction.exitAccount(intentAction_exitAccount, intentAction);
                break;
            case R.id.fragment_setting_back:
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    private void drawFragment() {
        setHW(back, 24, 24);
        setMargin(back, 13, 10, 0, 0, false);

        setHW(title, 52, 61);
        setMargin(title, 20, 0, 0, 3, false);

        setHW(binding.fragmentSettingExit, 50, 160);
        setMargin(binding.fragmentSettingExit, 0, 50, 0, 0, true);

        setHW(binding.fragmentSettingVersion, 23, 85);
        setMargin(binding.fragmentSettingVersion, 0, 10, 0,
                19, true);

        editUserMessage.drawView();
        aboutUs.drawView();
        openNotification.drawView();
        feedback.drawView();
        accountAndSafety.drawView();

        TextView theme = title.findViewById(R.id.fragment_setting_title);
        setTextSize(theme, 30);
    }

    private class UserBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(intentAction)){
                return;
            }
            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_exitAccount:
                    if (isFastClick()) return;
                    ARouter.getInstance()
                            .build("/spaceTime/start")
                            .withInt("type", 1)
                            .navigation();
                    initCookies();
                    closeCUT();
                    break;
                default:
                    break;
            }
        }
    }
}

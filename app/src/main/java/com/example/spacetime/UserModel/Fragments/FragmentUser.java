package com.example.spacetime.UserModel.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.R;
import com.example.spacetime.UserModel.Components.OptionLayoutChoose;
import com.example.spacetime.UserModel.Components.OptionLayoutTurn;
import com.example.spacetime.databinding.FragmentUserBinding;

import static com.example.spacetime.Components.BasicActivity.closeCUT;

public class FragmentUser extends Fragment implements View.OnClickListener {
    private FragmentUserBinding binding;

    private OptionLayoutTurn changeUserMessage;
    private OptionLayoutTurn accountAndSafety;
    private OptionLayoutChoose requestLocation;
    private OptionLayoutChoose openNotification;
    private OptionLayoutTurn feedBack;
    private OptionLayoutTurn aboutUs;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, null, false);
        binding.fragmentUserExit.setOnClickListener(this);
        changeUserMessage=new OptionLayoutTurn(getContext(), "修改个人资料");
        accountAndSafety=new OptionLayoutTurn(getContext(), "账号与安全");
        requestLocation=new OptionLayoutChoose(getContext(), "允许获取地理位置");
        openNotification=new OptionLayoutChoose(getContext(), "开启推送");
        feedBack=new OptionLayoutTurn(getContext(), "意见反馈");
        aboutUs=new OptionLayoutTurn(getContext(), "关于我们");

        binding.fragmentUserFunction.addView(changeUserMessage);
        binding.fragmentUserFunction.addView(accountAndSafety);
        binding.fragmentUserFunction.addView(requestLocation);
        binding.fragmentUserFunction.addView(openNotification);
        binding.fragmentUserFunction.addView(feedBack);
        binding.fragmentUserFunction.addView(aboutUs);

        changeUserMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/spaceTime/user")
                        .navigation();
            }
        });

        accountAndSafety.setOnClickListener(this);
        requestLocation.binding.optionLayoutChooseGetNotification.setOnClickListener(this);
        openNotification.binding.optionLayoutChooseGetNotification.setOnClickListener(this);
        feedBack.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_user_exit:
                ARouter.getInstance()
                        .build("/spaceTime/start")
                        .navigation();
                closeCUT();
                break;
            default:
                Toast.makeText(getContext(), "waiting for coming true", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

package com.haixi.spacetime.LoginAndRegister.Fragments;

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

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Entity.BasicFragment;
import com.haixi.spacetime.Entity.Cookies;
import com.haixi.spacetime.Entity.OkHttpAction;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentResetPasswordBinding;

import static com.haixi.spacetime.Entity.Cookies.newPassword;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setTextSize;

public class ResetPasswordFragment extends BasicFragment implements View.OnClickListener {
    private FragmentResetPasswordBinding binding;
    private final String intentAction = "com.example.spacetime.LoginAndRegister.Fragments" +
            ".ResetPasswordFragment";
    private final int intentAction_resetPassword = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password,
                null, false);
        drawFragment();

        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadCastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);

        okHttpAction = new OkHttpAction(getContext());

        binding.resetPasswordNextPage.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resetPassword_nextPage:
                if (isFastClick()){
                    return;
                }
                newPassword = binding.resetPasswordPassword.getText().toString();
                okHttpAction.resetPassword(intentAction_resetPassword, intentAction);
                break;
            default:
                break;
        }
    }

    private class UserInfoBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(intentAction)){
                return;
            }

            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_resetPassword:
                    Cookies.password = Cookies.newPassword;
                    ARouter.getInstance()
                            .build("/spaceTime/main")
                            .withString("path", "loginBegin")
                            .navigation();
                    break;
                default:
                    break;
            }
        }
    }

    private void drawFragment(){
        setH(binding.resetPasswordTitle, 52);
        setMargin(binding.resetPasswordTitle, 20, 44, 20, 0, true);
        setTextSize(binding.resetPasswordTitle, 36);

        setHW(binding.resetPasswordLine1, 70, 335);
        setMargin(binding.resetPasswordLine1, 20, 40, 20, 0, false);

        setHW(binding.resetPasswordPassword, 52, 285);
        setMargin(binding.resetPasswordPassword, 25, 9, 25, 9, false);
        setTextSize(binding.resetPasswordPassword, 24);

        setHW(binding.resetPasswordNextPage, 50, 316);
        setMargin(binding.resetPasswordNextPage, 30, 0, 29, 24, true);
        setTextSize(binding.resetPasswordNextPage, 16);
    }
}

package com.example.spacetime.LoginAndRegister.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.example.spacetime.Others.BasicFragment;
import com.example.spacetime.Others.OkHttpAction;
import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentResetPasswordBinding;

public class FragmentResetPassword extends BasicFragment implements View.OnClickListener {
    private FragmentResetPasswordBinding binding;
    private final String intentAction = "com.example.spacetime.LoginAndRegister.Fragments" +
            ".FragmentResetPassword";
    private final int intentAction_resetPassword = 1;
    private IntentFilter intentFilter;
    private UserBroadcastReceiver userBroadcastReceiver;
    private OkHttpAction okHttpAction;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password,
                null, false);

        intentFilter = new IntentFilter();
        userBroadcastReceiver = new UserBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userBroadcastReceiver, intentFilter);

        okHttpAction = new OkHttpAction(getContext());

        binding.resetPasswordNextPage.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        getContext().unregisterReceiver(userBroadcastReceiver);
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset_password_nextPage:
                if (isFastClick()){
                    return;
                }
                okHttpAction.resetPassword(binding.resetPasswordPassword.getText().toString(),
                        intentAction_resetPassword, intentAction);
                break;
            default:
                break;
        }
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
                case intentAction_resetPassword:
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
}

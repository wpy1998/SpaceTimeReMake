package com.example.spacetime.LoginAndRegister.Fragments;

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
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.Others.BasicFragment;
import com.example.spacetime.Others.Cookies;
import com.example.spacetime.Others.OkHttpAction;
import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentResetPasswordBinding;

import java.security.acl.Owner;

import static com.example.spacetime.Others.Cookies.newPassword;

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
            case R.id.reset_password_nextPage:
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
}

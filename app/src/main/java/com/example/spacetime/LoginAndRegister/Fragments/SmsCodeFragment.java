package com.example.spacetime.LoginAndRegister.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.Others.BasicFragment;
import com.example.spacetime.Others.OkHttpAction;
import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentSmsCodeBinding;

import org.json.JSONObject;

import static com.example.spacetime.Others.Cookies.token;
import static com.example.spacetime.Others.Settings.isReset;

public class SmsCodeFragment extends BasicFragment {
    private FragmentSmsCodeBinding binding;
    private final String intentAction = "com.example.spacetime.LoginAndRegister.Fragments" +
            ".SmsCodeFragment";
    private final int intentAction_authorizeWithSmsCode = 1;

    private TextView chooseArea;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sms_code,
                null, false);
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);

        okHttpAction = new OkHttpAction(getContext());

        chooseArea = binding.verificationCodeContent;
        chooseArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = chooseArea.getText().toString();
                if (content.length() == 4){
                    chooseArea.setInputType(InputType.TYPE_NULL);
                    okHttpAction.authorizeWithSmsCode(content, intentAction_authorizeWithSmsCode,
                            intentAction);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return binding.getRoot();
    }

    void refresh(){
        chooseArea.setText("");
        chooseArea.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private class UserInfoBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(), data;
            if (!action.equals(intentAction)){
                return;
            }
            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_authorizeWithSmsCode:
                    try {
                        data = intent.getStringExtra("data");
                        JSONObject jsonObject = new JSONObject(data);
                        String data1 = jsonObject.getString("data");
                        JSONObject jsonObject1 = new JSONObject(data1);
                        token = jsonObject1.getString("token");
                        if (isReset){
                            isReset = false;
                            ARouter.getInstance()
                                    .build("/spaceTime/login")
                                    .withString("path", "resetPassword")
                                    .navigation();
                            return;
                        }
                        ARouter.getInstance()
                                .build("/spaceTime/main")
                                .withString("path", "loginBegin")
                                .navigation();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    refresh();
                    break;
            }
        }
    }
}

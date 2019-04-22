package com.haixi.spacetime.LoginAndRegister.Fragments;

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

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Others.BasicFragment;
import com.haixi.spacetime.Others.OkHttpAction;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentSmsCodeBinding;

import org.json.JSONObject;

import static com.haixi.spacetime.Others.Cookies.token;
import static com.haixi.spacetime.Others.Settings.setMargin;
import static com.haixi.spacetime.Others.Settings.isReset;
import static com.haixi.spacetime.Others.Settings.setH;
import static com.haixi.spacetime.Others.Settings.setHW;
import static com.haixi.spacetime.Others.Settings.setTextSize;

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
        drawFragment();
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

    public void drawFragment(){
        setH(binding.verificationCodeTitle, 53);
        setMargin(binding.verificationCodeTitle, 20, 44, 20, 0, true);
        setTextSize(binding.verificationCodeTitle, 36);

        setHW(binding.verificationCodeLine1, 70, 335);
        setMargin(binding.verificationCodeLine1, 20, 40, 20, 0, false);

        setHW(binding.verificationCodeContent, 52, 285);
        setMargin(binding.verificationCodeContent, 25, 9, 25, 9, false);
        setTextSize(binding.verificationCodeContent, 24);
    }
}

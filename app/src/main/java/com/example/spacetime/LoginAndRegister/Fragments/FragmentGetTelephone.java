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
import com.example.spacetime.Others.OkHttpAction;
import com.example.spacetime.Others.Cookies;
import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentGetTelephoneBinding;

import org.json.JSONObject;

import static com.example.spacetime.Others.Cookies.phoneNumber;

public class FragmentGetTelephone extends BasicFragment implements View.OnClickListener {
    private FragmentGetTelephoneBinding binding;
    private final String intentAction = "com.example.spacetime.Login_and_Register.Fragments." +
            "FragmentGetTelephone";
    private final int intentAction_CheckExistence = 1, intentAction_SmsCode = 2;
    private IntentFilter intentFilter;
    private OkHttpAction okHttpAction;
    private MyBroadcastReceiver myBroadcastReceiver;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        intentFilter = new IntentFilter();
        myBroadcastReceiver = new MyBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(myBroadcastReceiver, intentFilter);

        okHttpAction = new OkHttpAction(getContext());

        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_get_telephone, null,
                false);
        binding.getTelephoneNext.setOnClickListener(this);
        binding.getTelephoneChooseArea.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(myBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getTelephone_next:
                String phoneNumber = binding.getTelephoneTelephoneNumber.getText().toString();
                if (phoneNumber.length() != 11){
                    Toast.makeText(getContext(), "请输入正确号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isFastClick()){
                    return;
                }
                Cookies.phoneNumber = phoneNumber;
                okHttpAction.checkExistence(binding.getTelephoneTelephoneNumber.getText().toString(),
                        intentAction_CheckExistence, intentAction);
                break;
            default:
                break;
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(), data;
            int type = intent.getIntExtra("type", 0);
            if (!action.equals(intentAction)){
                return;
            }

            switch (type){
                case intentAction_CheckExistence:
                    data = intent.getStringExtra("data");
                    try {
                        JSONObject object = new JSONObject(data);
                        String data1 = object.getString("data");
                        JSONObject object1 = new JSONObject(data1);
                        boolean isExist = object1.getBoolean("existence");

                        if (!isExist){
                            Toast.makeText(getContext(), "该手机号尚未被注册",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        phoneNumber = binding.getTelephoneTelephoneNumber.getText().toString();
                        okHttpAction.getSmsCode(intentAction_SmsCode, intentAction);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case intentAction_SmsCode:
                    Toast.makeText(getContext(), "验证码已发送", Toast.LENGTH_SHORT).show();
                    ARouter.getInstance()
                            .build("/spaceTime/login")
                            .withString("path", "getVerificationCode")
                            .navigation();
                    break;
                default:
                    break;
            }
        }
    }
}

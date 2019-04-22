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
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Others.BasicFragment;
import com.haixi.spacetime.Others.OkHttpAction;
import com.haixi.spacetime.Others.Cookies;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentGetTelephoneBinding;

import org.json.JSONObject;

import static com.haixi.spacetime.Others.Cookies.phoneNumber;
import static com.haixi.spacetime.Others.Settings.adaptView;
import static com.haixi.spacetime.Others.Settings.setH;
import static com.haixi.spacetime.Others.Settings.setHW;
import static com.haixi.spacetime.Others.Settings.setTextSize;

public class GetTelephoneFragment extends BasicFragment implements View.OnClickListener {
    private FragmentGetTelephoneBinding binding;
    private final String intentAction = "com.example.spacetime.Login_and_Register.Fragments." +
            "GetTelephoneFragment";
    private final int intentAction_CheckExistence = 1, intentAction_SmsCode = 2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_get_telephone, null,
                false);

        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadCastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);
        okHttpAction = new OkHttpAction(getContext());

        drawFragment();
        binding.getTelephoneNext.setOnClickListener(this);
        binding.getTelephoneChooseArea.setOnClickListener(this);
        return binding.getRoot();
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
                okHttpAction.checkExistence(intentAction_CheckExistence, intentAction);
                break;
            default:
                break;
        }
    }

    private class UserInfoBroadCastReceiver extends BroadcastReceiver{

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

    private void drawFragment(){
        setH(binding.getTelephoneTitle, 52);
        adaptView(binding.getTelephoneTitle, 31, 49, 31, 0, true);
        setTextSize(binding.getTelephoneTitle, 36);

        setHW(binding.getTelephoneLine1, 50, 346);
        adaptView(binding.getTelephoneLine1, 15, 35, 14, 0, false);

        setH(binding.getTelephoneChooseArea, 32);
        adaptView(binding.getTelephoneChooseArea, 16, 9, 19, 9, true);
        setTextSize(binding.getTelephoneChooseArea, 16);

        setH(binding.getTelephoneTelephoneNumber, 32);
        adaptView(binding.getTelephoneTelephoneNumber, 18,9,18,9,false);
        setTextSize(binding.getTelephoneTelephoneNumber, 14);

        setHW(binding.getTelephoneNext, 50, 315);
        adaptView(binding.getTelephoneNext, 30, 0, 30, 15, true);
        setTextSize(binding.getTelephoneNext, 16);
    }
}

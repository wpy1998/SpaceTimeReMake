package com.example.spacetime.Login_and_Register.Fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.Others.OkHttpAction;
import com.example.spacetime.R;
import com.example.spacetime.Others.BasicFragment;
import com.example.spacetime.databinding.FragmentRegisterBeginBinding;

import org.json.JSONObject;

import static com.example.spacetime.Others.Owner.password;
import static com.example.spacetime.Others.Owner.phoneNumber;
import static com.example.spacetime.Others.Owner.setMessage;
import static com.example.spacetime.Others.Owner.token;

public class RegisterBeginFragment extends BasicFragment implements View.OnClickListener {
    private FragmentRegisterBeginBinding binding;
    private int areaWhich;
    private String intentAction = "com.example.spacetime.Login_and_Register.Fragments." +
            "RegisterBeginFragment";
    private final int intentAction_CheckExistence = 1, intentAction_CheckSmsCode = 2,
            intentAction_Register = 3, intentAction_getToken = 4;
    OkHttpAction okHttpAction;
    private IntentFilter intentFilter;
    private LGBroadCastReceiver lgBroadCastReceiver;

    private TextView areaCode;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_begin,
                null, false);

        okHttpAction = new OkHttpAction(getContext());
        intentFilter = new IntentFilter();
        lgBroadCastReceiver = new LGBroadCastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(lgBroadCastReceiver, intentFilter);

        binding.registerBeginChooseArea.setOnClickListener(this);
        binding.registerBeginLogin.setOnClickListener(this);
        binding.registerBeginGetVerificationCode.setOnClickListener(this);
        binding.registerBeginNextPage.setOnClickListener(this);
        binding.registerBeginAreaCode.setOnClickListener(this);
        binding.registerBeginAreaCode.setOnClickListener(this);

        areaCode=binding.registerBeginAreaCode;
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        getContext().unregisterReceiver(lgBroadCastReceiver);
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_begin_login:
                ARouter.getInstance()
                        .build("/spaceTime/login")
                        .withString("path", "loginBegin")
                        .navigation();
                break;
            case R.id.register_begin_chooseArea:
                new AlertDialog.Builder(getContext()).setTitle("请选择您的地区号码").setIcon(
                        R.drawable.myperson).setSingleChoiceItems(
                        new String[] { "中国", "日本1", "日本2", "悉尼", "英国", "印度"},
                        areaWhich, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                areaWhich = which;
                                if (areaWhich == 0){
                                    areaCode.setText("+86");
                                }else if (areaWhich == 1){
                                    areaCode.setText("+080");
                                }else if (areaWhich == 2){
                                    areaCode.setText("+090");
                                }else if (areaWhich == 3){
                                    areaCode.setText("+04");
                                }else if (areaWhich == 4){
                                    areaCode.setText("+91");
                                }else if (areaWhich == 5){
                                    areaCode.setText("+98");
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.register_begin_areaCode:
                binding.registerBeginChooseArea.performClick();
                break;
            case R.id.register_begin_nextPage:
                String password = binding.registerBeginSetPassword.getText().toString();
                if (password.equals("")){
                    Toast.makeText(getContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isFastClick()) return;
                okHttpAction.checkSmsCode(binding.registerBeginTelephoneNumber.getText().toString(),
                        binding.registerBeginVerificationCode.getText().toString(),
                        intentAction_CheckSmsCode, intentAction);
            case R.id.register_begin_getVerificationCode:
                String phoneNumber = binding.registerBeginTelephoneNumber
                        .getText().toString();
                if (phoneNumber.length() != 11){
                    Toast.makeText(getContext(), "请输入正确号码", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (isFastClick()) return;
                okHttpAction.checkExistence(phoneNumber, intentAction_CheckExistence, intentAction);
                break;
            default:
                break;
        }
    }

    private class LGBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(), data;
            if (action.equals(intentAction)){
                int type = intent.getIntExtra("type", 0);
                switch (type){
                    case intentAction_CheckExistence:
                        data = intent.getStringExtra("data");
                        try {
                            JSONObject object = new JSONObject(data);
                            String data1 = object.getString("data");
                            JSONObject object1 = new JSONObject(data1);
                            boolean isExist = object1.getBoolean("existence");

                            if (isExist){
                                Toast.makeText(getContext(), "该号码已被使用，请重新输入手机号码",
                                        Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getContext(), "验证码已发送",
                                        Toast.LENGTH_LONG).show();
                                okHttpAction.getSmsCode(binding.registerBeginTelephoneNumber.getText()
                                        .toString(), 0, intentAction);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;

                    case intentAction_CheckSmsCode:
                        data = intent.getStringExtra("data");
                        try {
                            JSONObject object = new JSONObject(data);
                            String data1 = object.getString("data");
                            JSONObject jsonObject = new JSONObject(data1);
                            if (jsonObject.getBoolean("correction") == false){
                                Toast.makeText(getContext(), "验证码错误，请重新输入",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                            okHttpAction.registerUsers(binding.registerBeginVerificationCode.getText()
                                            .toString(), binding.registerBeginTelephoneNumber.getText().toString(),
                                    binding.registerBeginSetPassword.getText().toString(),
                                    intentAction_Register, intentAction);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;

                    case intentAction_Register:
                        data = intent.getStringExtra("data");
                        System.out.println(data);
                        try {
                            JSONObject object = new JSONObject(data);
                            String data1 = object.getString("data");
                            int status = object.getInt("status");
                            if (status >= 300){
                                Toast.makeText(getContext(), "发生异常，可能号码已被注册",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                            setMessage(data1);
                            password = binding.registerBeginSetPassword.getText().toString();
                            okHttpAction.authorizeWithPassword(phoneNumber, password, intentAction_getToken,
                                    intentAction);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;

                    case intentAction_getToken:
                        data = intent.getStringExtra("data");
                        try {
                            JSONObject object = new JSONObject(data);
                            String data1 = object.getString("data");
                            JSONObject object1 = new JSONObject(data1);
                            token = object1.getString("token");
                            ARouter.getInstance()
                                    .build("/spaceTime/register")
                                    .withString("path", "completeMessage")
                                    .navigation();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;

                    default:
                        break;
                }
            }else {
                return;
            }
        }
    }
}

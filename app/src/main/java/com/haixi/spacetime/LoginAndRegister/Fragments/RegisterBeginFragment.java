package com.haixi.spacetime.LoginAndRegister.Fragments;

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
import com.haixi.spacetime.Others.OkHttpAction;
import com.haixi.spacetime.R;
import com.haixi.spacetime.Others.BasicFragment;
import com.haixi.spacetime.databinding.FragmentRegisterBeginBinding;

import org.json.JSONObject;

import static com.haixi.spacetime.Others.Cookies.password;
import static com.haixi.spacetime.Others.Cookies.phoneNumber;
import static com.haixi.spacetime.Others.Cookies.setMessage;
import static com.haixi.spacetime.Others.Cookies.token;
import static com.haixi.spacetime.Others.Settings.setMargin;
import static com.haixi.spacetime.Others.Settings.setH;
import static com.haixi.spacetime.Others.Settings.setHW;
import static com.haixi.spacetime.Others.Settings.setTextSize;

public class RegisterBeginFragment extends BasicFragment implements View.OnClickListener {
    private FragmentRegisterBeginBinding binding;
    private int areaWhich;
    private String intentAction = "com.example.spacetime.Login_and_Register.Fragments." +
            "RegisterBeginFragment";
    private final int intentAction_CheckExistence = 1, intentAction_CheckSmsCode = 2,
            intentAction_Register = 3, intentAction_getToken = 4;

    private TextView areaCode;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_begin,
                null, false);

        okHttpAction = new OkHttpAction(getContext());
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadCastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);

        drawFragment();

        binding.registerBeginChooseArea.setOnClickListener(this);
        binding.registerBeginLogin.setOnClickListener(this);
        binding.registerBeginGetSmsCode.setOnClickListener(this);
        binding.registerBeginNextPage.setOnClickListener(this);
        binding.registerBeginAreaCode.setOnClickListener(this);
        binding.registerBeginAreaCode.setOnClickListener(this);

        areaCode=binding.registerBeginAreaCode;
        return binding.getRoot();
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
                phoneNumber = binding.registerBeginTelephoneNumber.getText().toString();
                okHttpAction.checkSmsCode(binding.registerBeginSmsCode.getText().toString(),
                        intentAction_CheckSmsCode, intentAction);
            case R.id.register_begin_getSmsCode:
                phoneNumber = binding.registerBeginTelephoneNumber
                        .getText().toString();
                if (phoneNumber.length() != 11){
                    Toast.makeText(getContext(), "请输入正确号码", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (isFastClick()) return;
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
            if (action.equals(intentAction)){
                return;
            }
            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_CheckExistence:
                    data = intent.getStringExtra("data");
                    try {
                        JSONObject object = new JSONObject(data);
                        String data1 = object.getString("data");
                        JSONObject object1 = new JSONObject(data1);
                        boolean isExist = object1.getBoolean("existence");
                        System.out.println(isExist);

                        if (isExist){
                            Toast.makeText(getContext(), "该号码已被使用，请重新输入手机号码",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(getContext(), "验证码已发送",
                                Toast.LENGTH_SHORT).show();
                        okHttpAction.getSmsCode(0, intentAction);
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
                        phoneNumber = binding.registerBeginTelephoneNumber.getText().toString();
                        password = binding.registerBeginSetPassword.getText().toString();
                        okHttpAction.registerUsers(binding.registerBeginSmsCode.getText()
                                        .toString(), intentAction_Register, intentAction);
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
                        okHttpAction.authorizeWithPassword(intentAction_getToken, intentAction);
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
        }
    }

    private void drawFragment(){
        setHW(binding.registerBeginLine0, 52, 327);
        setMargin(binding.registerBeginLine0, 28, 34, 22, 0, false);

        setH(binding.registerBeginTitle, 52);
        setMargin(binding.registerBeginTitle, 0, 0, 0, 0, true);
        setTextSize(binding.registerBeginTitle, 36);

        setH(binding.registerBeginLogin, 23);
        setMargin(binding.registerBeginLogin, 0, 15, 0, 14, true);
        setTextSize(binding.registerBeginLogin, 16);

        setHW(binding.registerBeginLine1, 50, 331);
        setMargin(binding.registerBeginLine1, 22, 30, 22, 0, false);

        setH(binding.registerBeginAreaCode, 23);
        setMargin(binding.registerBeginAreaCode, 12, 14, 0, 13, true);
        setTextSize(binding.registerBeginAreaCode, 14);

        setHW(binding.registerBeginChooseArea, 24, 24);
        setMargin(binding.registerBeginChooseArea, 0, 13, 0, 13, false);

        setHW(binding.registerBeginTelephoneNumber, 23, 218);
        setMargin(binding.registerBeginTelephoneNumber,27,14,12,13,true);
        setTextSize(binding.registerBeginTelephoneNumber, 14);

        setHW(binding.registerBeginLine2, 50, 331);
        setMargin(binding.registerBeginLine2, 22, 30, 22, 0, false);

        setHW(binding.registerBeginSmsCode, 23, 187);
        setMargin(binding.registerBeginSmsCode, 12, 14, 12, 13, true);
        setTextSize(binding.registerBeginSmsCode, 14);

        setHW(binding.registerBeginGetSmsCode, 23, 82);
        setMargin(binding.registerBeginGetSmsCode, 19, 14, 19, 13, true);
        setTextSize(binding.registerBeginGetSmsCode, 14);

        setHW(binding.registerBeginLine3, 50, 331);
        setMargin(binding.registerBeginLine3, 22, 34, 22, 0, false);

        setHW(binding.registerBeginSetPassword, 26, 305);
        setMargin(binding.registerBeginSetPassword, 13, 12, 13, 12, true);
        setTextSize(binding.registerBeginSetPassword, 14);

        setHW(binding.registerBeginNextPage, 50, 320);
        setMargin(binding.registerBeginNextPage, 27, 0, 28, 14, true);
        setTextSize(binding.registerBeginNextPage, 16);
    }
}

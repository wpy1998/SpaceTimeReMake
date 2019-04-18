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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.Others.OkHttpAction;
import com.example.spacetime.R;
import com.example.spacetime.UserModel.Components.BasicFragment;
import com.example.spacetime.databinding.FragmentLoginBeginBinding;

import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

import static com.example.spacetime.Others.Owner.birthday;
import static com.example.spacetime.Others.Owner.comeFrom;
import static com.example.spacetime.Others.Owner.gender;
import static com.example.spacetime.Others.Owner.interests;
import static com.example.spacetime.Others.Owner.labels;
import static com.example.spacetime.Others.Owner.major;
import static com.example.spacetime.Others.Owner.ownerId;
import static com.example.spacetime.Others.Owner.password;
import static com.example.spacetime.Others.Owner.phoneNumber;
import static com.example.spacetime.Others.Owner.profession;
import static com.example.spacetime.Others.Owner.school;
import static com.example.spacetime.Others.Owner.setMessage;
import static com.example.spacetime.Others.Owner.userName;

public class LoginBeginFragment extends BasicFragment implements View.OnClickListener {
    private FragmentLoginBeginBinding binding;
    private int areaWhich;
    private final String intentAction = "com.example.spacetime.Login_and_Register.Fragments." +
            "LoginBeginFragment";
    private final int intentAction_AuthorizationWithPassword = 0;
    OkHttpAction okHttpAction;

    private MyBroadcastReceiver myBroadcastReceiver;
    private IntentFilter intentFilter;
    private TextView areaCode;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        intentFilter = new IntentFilter();
        myBroadcastReceiver = new MyBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(myBroadcastReceiver, intentFilter);

        okHttpAction = new OkHttpAction(getContext());

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_begin, null,
                false);
        binding.loginChooseArea.setOnClickListener(this);
        binding.loginGetVerificationCode.setOnClickListener(this);
        binding.loginForgetPassword.setOnClickListener(this);
        binding.loginLogin.setOnClickListener(this);
        binding.loginRegisterNewAccount.setOnClickListener(this);
        binding.loginTelephoneArea.setOnClickListener(this);

        areaCode = binding.loginTelephoneArea;
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
            case R.id.login_login:
                String phoneNumber = binding.loginTelephoneNumber.getText().toString();
                String password = binding.loginPassword.getText().toString();
                if (phoneNumber.length() != 11){
                    Toast.makeText(getContext(), "请输入正确电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isFastClick()){
                    return;
                }
                okHttpAction.authorizeWithPassword(phoneNumber, password,
                        intentAction_AuthorizationWithPassword, intentAction);
                break;
            case R.id.login_registerNewAccount:
                ARouter.getInstance()
                        .build("/spaceTime/register")
                        .withString("path", "registerBegin")
                        .navigation();
                break;
            case R.id.login_chooseArea:
                new AlertDialog.Builder(getContext()).setTitle("请选择您的地区号码").setIcon(
                        R.drawable.myperson).setSingleChoiceItems(
                        new String[] { "中国", "日本1", "日本2", "悉尼", "英国", "印度"}, areaWhich,
                        new DialogInterface.OnClickListener() {
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
            case R.id.login_telephoneArea:
                binding.loginChooseArea.performClick();
                break;
            case R.id.login_forgetPassword:
                ARouter.getInstance()
                        .build("/spaceTime/login")
                        .withString("path", "getTelephone")
                        .navigation();
                break;
            case R.id.login_getVerificationCode:
                ARouter.getInstance()
                        .build("/spaceTime/login")
                        .withString("path", "getTelephone")
                        .navigation();
                break;
            default:
                break;
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(), data;
            if(action.equals(intentAction)){

                int type = intent.getIntExtra("type", 0);
                switch (type){
                    case intentAction_AuthorizationWithPassword:
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
                            password = binding.loginPassword.getText().toString();
                            ARouter.getInstance()
                                    .build("/spaceTime/main")
                                    .withString("path", "loginBegin")
                                    .navigation();
                            break;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    default:
                        break;
                }
            }else {
                return;
            }
        }
    }
}

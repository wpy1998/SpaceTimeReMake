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
import com.example.spacetime.Others.OkHttpActionLG;
import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentRegisterBeginBinding;

import org.json.JSONObject;

public class RegisterBeginFragment extends Fragment implements View.OnClickListener {
    private FragmentRegisterBeginBinding binding;
    private int areaWhich;
    private String intentAction = "com.example.spacetime." +
            "Login_and_Register.Fragments.RegisterBeginFragment";
    private final int intentAction_GetVerification = 1, intentAction_Login = 2;
    OkHttpActionLG okHttpActionLG;
    private IntentFilter intentFilter;

    private TextView areaCode;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_begin,
                null, false);

        okHttpActionLG = new OkHttpActionLG(getContext());
        intentFilter = new IntentFilter();
        LGBroadCastReceiver lgBroadCastReceiver = new LGBroadCastReceiver();
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
                ARouter.getInstance()
                        .build("/spaceTime/register")
                        .withString("path", "completeMessage")
                        .navigation();
            case R.id.register_begin_getVerificationCode:
                String phoneNumber = binding.registerBeginTelephoneNumber
                        .getText().toString();
                if (phoneNumber.length() != 11){
                    Toast.makeText(getContext(), "请输入正确号码", Toast.LENGTH_SHORT)
                            .show();
                }else {
                    okHttpActionLG.checkExistence(phoneNumber, intentAction_GetVerification,
                            intentAction);
                }
                break;
            default:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class LGBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_GetVerification:
                    String data = intent.getStringExtra("data");
                    try {
                        JSONObject object = new JSONObject(data);
                        String message = object.getString("message");
                        if (message.equals("success")){
                            String data1 = object.getString("data");
                            JSONObject object1 = new JSONObject(data1);
                            boolean isExist = object1.getBoolean("existence");

                            if (isExist){
                                Toast.makeText(getContext(), "该号码已被使用，请重新输入",
                                        Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getContext(), "检测成功，请设置您的密码",
                                        Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getContext(), "网络请求错误",
                                    Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

                case intentAction_Login:
                    break;

                default:
                    Toast.makeText(getContext(), "网络请求错误",
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}

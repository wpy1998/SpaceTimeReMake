package com.haixi.spacetime.LoginAndRegister.Fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Entity.Cookies;
import com.haixi.spacetime.Common.OkHttpAction;
import com.haixi.spacetime.R;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.databinding.FragmentLoginBeginBinding;

import org.json.JSONObject;

import static com.haixi.spacetime.Entity.Cookies.password;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.isReset;
import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;

public class LoginBeginFragment extends BasicFragment implements View.OnClickListener {
    private FragmentLoginBeginBinding binding;
    private int areaWhich;
    private final String intentAction = "com.example.spacetime.Login_and_Register.Fragments." +
            "LoginBeginFragment";
    private final int intentAction_AuthorizationWithPassword = 0;
    private TextView areaCode;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_begin, null,
                false);

        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadCastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);

        okHttpAction = new OkHttpAction(getContext());

        drawFragment();
        binding.loginChooseArea.setOnClickListener(this);
        binding.loginGetVerificationCode.setOnClickListener(this);
        binding.loginForgetPassword.setOnClickListener(this);
        binding.loginLogin.setOnClickListener(this);
        binding.loginRegisterNewAccount.setOnClickListener(this);
        binding.loginTelephoneArea.setOnClickListener(this);

        areaCode = binding.loginTelephoneArea;

        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String account1 = pref.getString("phoneNumber", "");
        String password1 = pref.getString("password", "");
        binding.loginTelephoneNumber.setText(account1);
        binding.loginPassword.setText(password1);
        binding.loginTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/spaceTime/forbidden")
                        .navigation();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_login:
                phoneNumber = binding.loginTelephoneNumber.getText().toString();
                password = binding.loginPassword.getText().toString();
                if (phoneNumber.length() != 11){
                    Toast.makeText(getContext(), "请输入正确电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isFastClick()){
                    return;
                }
                okHttpAction.authorizeWithPassword(intentAction_AuthorizationWithPassword, intentAction);
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
                isReset = true;
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

    private class UserInfoBroadCastReceiver extends BroadcastReceiver{

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
                            if (status >= 400){
                                Toast.makeText(getContext(), "账户或密码错误",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                            JSONObject jsonObject = new JSONObject(data1);
                            Cookies.token = jsonObject.getString("token");
                            password = binding.loginPassword.getText().toString();

                            editor = pref.edit();
                            editor.putString("phoneNumber", phoneNumber);
                            editor.putString("password", password);
                            editor.apply();

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

    private void drawFragment(){
        setHW(binding.loginLine0, 52, 321);
        setMargin(binding.loginLine0, 26, 34, 28, 0, false);

        setH(binding.loginTitle, 52);
        setTextSize(binding.loginTitle, 36);

        setH(binding.loginRegisterNewAccount, 23);
        setMargin(binding.loginRegisterNewAccount, 0, 14, 0, 15, true);

        setHW(binding.loginLine1, 50, 331);
        setMargin(binding.loginLine1, 22, 50, 22, 0, false);

        setHW(binding.loginTelephoneArea, 23, 38);
        setMargin(binding.loginTelephoneArea, 12, 14, 0, 13, true);
        setTextSize(binding.loginTelephoneArea, 14);

        setHW(binding.loginChooseArea, 24, 24);
        setMargin(binding.loginChooseArea, 0, 13, 0, 13, false);

        setHW(binding.loginTelephoneNumber, 23, 218);
        setMargin(binding.loginTelephoneNumber,27,14,12,13,true);
        setTextSize(binding.loginTelephoneNumber, 14);

        setHW(binding.loginLine2, 50, 331);
        setMargin(binding.loginLine2, 22, 34, 22, 0, false);

        setHW(binding.loginPassword, 26, 307);
        setMargin(binding.loginPassword, 12, 12, 12, 12, true);
        setTextSize(binding.loginPassword, 14);

        setHW(binding.loginLine3, 23, 331);
        setMargin(binding.loginLine3, 22, 17, 22, 0, false);

        setH(binding.loginForgetPassword, 23);
        setTextSize(binding.loginForgetPassword, 14);

        setHW(binding.loginLogin, 50, 320);
        setMargin(binding.loginLogin, 27, 0, 28, 14, true);
        setTextSize(binding.loginLogin, 16);

        setH(binding.loginGetVerificationCode, 23);
        setMargin(binding.loginGetVerificationCode, 131, 17, 131, 34, true);
        setTextSize(binding.loginGetVerificationCode, 16);
    }
}

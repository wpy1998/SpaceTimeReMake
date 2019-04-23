package com.haixi.spacetime.LoginAndRegister;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Others.BasicActivity;
import com.haixi.spacetime.Others.Cookies;
import com.haixi.spacetime.Others.OkHttpAction;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ActivityStartBinding;

import org.json.JSONObject;

import static com.haixi.spacetime.Others.Cookies.password;
import static com.haixi.spacetime.Others.Cookies.phoneNumber;

@Route(path = "/spaceTime/start")
public class StartActivity extends BasicActivity implements View.OnClickListener {
    private ActivityStartBinding binding;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private OkHttpAction okHttpAction;
    private IntentFilter intentFilter;
    private final String intentAction = "com.haixi.spacetime.LoginAndRegister.StartActivity";
    private final int intentAction_login = 1;
    private UserInfoBroadcastReceiver userInfoBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_start);
        activityList0.add(this);

        okHttpAction = new OkHttpAction(this);
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        intentFilter.addAction(intentAction);
        registerReceiver(userInfoBroadcastReceiver, intentFilter);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        String account1 = pref.getString("phoneNumber", "");
        String password1 = pref.getString("password", "");
        Intent intentFront = getIntent();
        int type = intentFront.getIntExtra("type", 0);
        if (account1.equals("")==false && password1.equals("")==false && type== 0){
            phoneNumber = account1;
            password = password1;
            okHttpAction.authorizeWithPassword(intentAction_login, intentAction);
            binding.startMainView.removeAllViews();
        }

        if (type == 1){
            editor = pref.edit();
            editor.putString("phoneNumber", "");
            editor.putString("password", "");
            editor.apply();
        }

        drawActivity();

        binding.startLogin.setOnClickListener(this);
        binding.startRegister.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(userInfoBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_login:
                ARouter.getInstance()
                        .build("/spaceTime/login")
                        .withString("path", "loginBegin")
                        .navigation();
                break;
            case R.id.start_register:
                ARouter.getInstance()
                        .build("/spaceTime/register")
                        .withString("path", "registerBegin")
                    .navigation();
                break;
            default:
                break;
        }
    }

    private void drawActivity(){
    }

    private class UserInfoBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(),data;
            if (!action.equals(intentAction)){
                return;
            }

            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_login:
                    data = intent.getStringExtra("data");
                    System.out.println(data);
                    try {
                        JSONObject object = new JSONObject(data);
                        String data1 = object.getString("data");
                        int status = object.getInt("status");
                        if (status >= 400){
                            Toast.makeText(binding.getRoot().getContext(), "账户或密码错误",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = new JSONObject(data1);
                        Cookies.token = jsonObject.getString("token");

                        editor = pref.edit();
                        editor.putString("phoneNumber", phoneNumber);
                        editor.putString("password", password);
                        editor.apply();

                        ARouter.getInstance()
                                .build("/spaceTime/main")
                                .withTransition(R.anim.fade_in, R.anim.fade_out)
                                .navigation();
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}

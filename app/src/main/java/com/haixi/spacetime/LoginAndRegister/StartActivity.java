package com.haixi.spacetime.LoginAndRegister;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.allen.android.lib.PermissionUtils;
import com.haixi.spacetime.LoginAndRegister.Fragments.StartFragment;
import com.haixi.spacetime.LoginAndRegister.Fragments.WelcomeFragment;
import com.haixi.spacetime.Common.BasicActivity;
import com.haixi.spacetime.Entity.Cookies;
import com.haixi.spacetime.Common.OkHttpAction;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ActivityStartBinding;

import org.json.JSONObject;

import static com.haixi.spacetime.Entity.Cookies.password;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;

@Route(path = "/spaceTime/start")
public class StartActivity extends BasicActivity{
    private ActivityStartBinding binding;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private OkHttpAction okHttpAction;
    private IntentFilter intentFilter;
    private final String intentAction = "com.haixi.spacetime.LoginAndRegister.StartActivity";
    private final int intentAction_login = 1;
    private UserInfoBroadcastReceiver userInfoBroadcastReceiver;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_start);
        activityList0.add(this);
        setStatusBarColor(this, R.color.colorWhite, true);

        Intent intentFront = getIntent();
        int type = intentFront.getIntExtra("type", 0);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        String account1 = pref.getString("phoneNumber", "");
        String password1 = pref.getString("password", "");

        if (type == 1){
            originFragment = new StartFragment();
            replaceFragment(R.id.start_frameLayout);
            editor = pref.edit();
            editor.putString("phoneNumber", "");
            editor.putString("password", "");
            editor.apply();
            return;
        }

        okHttpAction = new OkHttpAction(this);
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        intentFilter.addAction(intentAction);
        registerReceiver(userInfoBroadcastReceiver, intentFilter);

        if (account1.equals("")==false && password1.equals("")==false){
            phoneNumber = account1;
            password = password1;
            okHttpAction.authorizeWithPassword(intentAction_login, intentAction);
        }
        originFragment = new WelcomeFragment();
        replaceFragment(R.id.start_frameLayout);
    }

    @Override
    protected void onDestroy() {
        if (userInfoBroadcastReceiver != null){
            unregisterReceiver(userInfoBroadcastReceiver);
        }
        super.onDestroy();
    }

    private class UserInfoBroadcastReceiver extends BroadcastReceiver {
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

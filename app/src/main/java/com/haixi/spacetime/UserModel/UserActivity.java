package com.haixi.spacetime.UserModel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haixi.spacetime.Common.BasicActivity;
import com.haixi.spacetime.Common.OkHttpAction;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.R;
import com.haixi.spacetime.UserModel.Fragments.FeedbackFragment;
import com.haixi.spacetime.UserModel.Fragments.EditNameFragment;
import com.haixi.spacetime.UserModel.Fragments.EditSignFragment;
import com.haixi.spacetime.UserModel.Fragments.EditUserFragment;
import com.haixi.spacetime.UserModel.Fragments.SettingFragment;
import com.haixi.spacetime.UserModel.Fragments.UserFragment;
import com.haixi.spacetime.databinding.ActivityUserBinding;

import org.json.JSONObject;

import static com.haixi.spacetime.Entity.Cookies.owner;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;
import static com.haixi.spacetime.Entity.Cookies.resultCode;
import static com.haixi.spacetime.Entity.User.setMessage;

@Route(path = "/spaceTime/user")
public class UserActivity extends BasicActivity {
    private ActivityUserBinding binding;
    private final String intentAction = "com.haixi.spacetime.UserModel.UserActivity";
    private final int intentAction_getUserMessage = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_user);
        activityList1.add(this);
        okHttpAction = new OkHttpAction(this);
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        intentFilter.addAction(intentAction);
        registerReceiver(userInfoBroadcastReceiver, intentFilter);

        Intent intentFront = getIntent();
        fragmentName = intentFront.getStringExtra("path");
        choosePath(fragmentName, intentFront);
    }

    private void choosePath(String path, Intent intent) {
        switch (path){
            case "changeUserMessage":
                originFragment = new EditUserFragment();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "feedback":
                originFragment = new FeedbackFragment();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "setting":
                originFragment = new SettingFragment();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "editName":
                originFragment = new EditNameFragment();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "editSign":
                originFragment = new EditSignFragment();
                replaceFragment(R.id.user_frameLayout);
                break;
            case "user":
                int userId = intent.getIntExtra("userId", 0);
                User user = new User();
                user.userId = userId;
                originFragment = new UserFragment(user);
                replaceFragment(R.id.user_frameLayout);
                break;
            default:
                replaceFragment(R.id.user_frameLayout);
                break;
        }
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        this.setResult(resultCode, intent);
        super.finish();
    }

    private class UserInfoBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(), data;
            if (!action.equals(intentAction)){
                return;
            }
            data = intent.getStringExtra("data");
            try{
                JSONObject object = new JSONObject(data);
                String data1 = object.getString("data");
                setMessage(data1, owner);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public String getIntentAction(){
        return intentAction;
    }
}

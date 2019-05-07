package com.haixi.spacetime.CircleModel.Components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Entity.OkHttpAction;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ComponentCircleBinding;

import org.json.JSONObject;

import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.setTextSize;
import static com.haixi.spacetime.Entity.User.setMessage;

public class CircleComponent extends LinearLayout {
    private ComponentCircleBinding binding;
    private Circle circle;
    private User user;
    private String intentAction = "";
    private int intentAction_type;

    private OkHttpAction okHttpAction;
    private IntentFilter intentFilter;
    private UserInfoBroadcastReceiver userInfoBroadcastReceiver;

    public CircleComponent(Context context, Circle circle) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_circle, this, true);
        this.circle = circle;
        binding.componentCircleName.setText(this.circle.name);
        drawComponent();
    }

    public CircleComponent(Context context, User user){
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_circle, this, true);
        this.user = user;
        drawComponent();
        drawImage(user.imageId);
        binding.componentCircleName.setText(user.phoneNumber);
        intentAction = "com.haixi.spacetime.CircleModel.Components.CircleComponent"
                + this.user.phoneNumber;
        intentAction_type = 1;
        okHttpAction = new OkHttpAction(getContext());
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);
        okHttpAction.getUserMessage(this.user.phoneNumber, intentAction_type, intentAction);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (userInfoBroadcastReceiver!= null){
            getContext().unregisterReceiver(userInfoBroadcastReceiver);
        }
        super.onDetachedFromWindow();
    }

    private void drawComponent(){
        setH(binding.componentCircleName, 30);
        setMargin(binding.componentCircleName,0,19,0,10,false);
        setTextSize(binding.componentCircleName, 20);

        setH(binding.componentCircleLine, 1);
    }

    //以下函数应用于circleMessageFragment添加用户
    private void drawImage(int imageId){
        setHW(binding.componentCircleImage, 50, 50);
        setMargin(binding.componentCircleImage, 0, 10, 15, 9, false);

        if (imageId == 0) imageId = R.drawable.william;
        binding.componentCircleImage.setImageResource(imageId);
    }

    private class UserInfoBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(intentAction)){
                return;
            }
            int type = intent.getIntExtra("type", 0);
            if (type == intentAction_type){
                String data = intent.getStringExtra("data");
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String data1 = jsonObject.getString("data");
                    setMessage(data1, user);
                    binding.componentCircleName.setText(user.userName);

                    binding.componentCircleMainView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance()
                                    .build("/spaceTime/user")
                                    .withString("path", "user")
                                    .withString("userTelephone", user.phoneNumber)
                                    .withString("userName", user.userName)
                                    .navigation();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    //以下函数应用于circleFragment添加圈子
    public void setIntentAction(String intentAction, int intentAction_type){
        this.intentAction = intentAction;
        this.intentAction_type = intentAction_type;
        setAction();
    }

    private void setAction(){
        binding.componentCircleMainView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intentAction.equals("")) return;
                Intent intent = new Intent(intentAction);
                intent.putExtra("circleId", circle.id);
                intent.putExtra("circleName", circle.name);
                intent.putExtra("type", intentAction_type);
                getContext().sendBroadcast(intent);
            }
        });
    }
}

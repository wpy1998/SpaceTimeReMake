package com.haixi.spacetime.DynamicModel.Components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ComponentUserBinding;

import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setMargin;

public class UserComponent extends LinearLayout {
    private ComponentUserBinding binding;
    private boolean isChoose = false;
    private String intentAction;
    private IntentFilter intentFilter;
    private ControlBroadcastReceiver controlBroadcastReceiver;
    private User user;
    public UserComponent(Context context, User user) {
        super(context);
        this.user = user;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.component_user,
                this, true);
        drawComponent();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (controlBroadcastReceiver != null)
            getContext().unregisterReceiver(controlBroadcastReceiver);
        super.onDetachedFromWindow();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void refresh(){
        if (isChoose){
            isChoose = false;
            binding.userComponentMainView.setBackground(getResources().getDrawable(R.drawable.shape_white));
        }else {
            isChoose = true;
            binding.userComponentMainView.setBackground(getResources().getDrawable(R.drawable.shape_blue));
        }
    }

    public void setImage(int imageId){
        binding.userComponentImage.setImageResource(imageId);
    }

    public void setIntent(String intentAction){
        this.intentAction = intentAction;
        if (controlBroadcastReceiver != null)
            getContext().unregisterReceiver(controlBroadcastReceiver);
        registerControlReceiver();
    }

    private void registerControlReceiver(){
        intentFilter = new IntentFilter();
        controlBroadcastReceiver = new ControlBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(controlBroadcastReceiver, intentFilter);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(intentAction);
                intent.putExtra("userId", user.userId);
                getContext().sendBroadcast(intent);
            }
        });
    }

    private class ControlBroadcastReceiver extends BroadcastReceiver {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(intentAction)){
                int data = intent.getIntExtra("userId", 0);
                if (data == user.userId){
                    if (!isChoose) refresh();
                }else {
                    if (isChoose) refresh();
                }
            }
        }
    }

    private void drawComponent(){
        setHW(binding.userComponentMainView, 56, 56);
        setMargin(binding.userComponentMainView, 8, 5, 8, 5, false);

        setHW(binding.userComponentImage, 50, 50);
        setMargin(binding.userComponentImage, 3, 3, 3, 3, false);
    }
}

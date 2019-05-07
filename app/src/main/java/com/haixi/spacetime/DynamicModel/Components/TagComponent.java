package com.haixi.spacetime.DynamicModel.Components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ComponentTagBinding;

import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.setTextSize;

public class TagComponent extends LinearLayout{
    private Context context;
    private ComponentTagBinding binding;
    private boolean isChoosen;
    private String intentAction;
    private int intentAction_Type;
    private IntentFilter intentFilter;
    private ControlBroadcastReceiver controlBroadcastReceiver;

    private Circle circle;
    private int size;

    public TagComponent(Context context, Circle circle, int size) {
        super(context);
        this.context = context;
        this.size = size;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_tag, this, true);
        drawLinearLayout();
        this.circle = circle;
        binding.tagViewName.setText(circle.name);

        isChoosen = true;
        refresh();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (controlBroadcastReceiver != null)
            getContext().unregisterReceiver(controlBroadcastReceiver);
        super.onDetachedFromWindow();
    }

    public String getName(){
        return circle.name;
    }

    public void setIntent(String intentAction, int type){
        this.intentAction = intentAction;
        intentAction_Type = type;
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
                intent.putExtra("name", circle.name);
                intent.putExtra("circleId", circle.id);
                intent.putExtra("type", intentAction_Type);
                intent.putExtra("size", size);
                getContext().sendBroadcast(intent);
            }
        });
    }

    public void refresh(){
        if (isChoosen){
            isChoosen = false;
            binding.tagViewMainView.setBackgroundResource(R.drawable.background_circle_gray);
        }else {
            isChoosen = true;
            binding.tagViewMainView.setBackgroundResource(R.drawable.background_circle_blue);
        }
    }

    private class ControlBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(intentAction)){
                String data = intent.getStringExtra("name");
                int circleId = intent.getIntExtra("circleId", -1);
                if (circle.name.equals(data) && circleId == circle.id){
                    if (!isChoosen) refresh();
                }else {
                    if (isChoosen) refresh();
                }
            }
        }
    }

    private void drawLinearLayout(){
        setMargin(binding.tagViewMainView, 6, 10, 6, 10, false);

        setH(binding.tagViewName, 40);
        setMargin(binding.tagViewName, 0, 0, 0, 0, true);
        setTextSize(binding.tagViewName, 16);
    }
}

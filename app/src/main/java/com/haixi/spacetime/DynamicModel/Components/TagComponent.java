package com.haixi.spacetime.DynamicModel.Components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ComponentTagBinding;

import static com.haixi.spacetime.Others.Settings.setH;
import static com.haixi.spacetime.Others.Settings.setMargin;
import static com.haixi.spacetime.Others.Settings.setTextSize;

public class TagComponent extends LinearLayout{
    private Context context;
    private ComponentTagBinding binding;
    private boolean isChoosen;
    private String intentAction = "com.haixi.spacetime.DynamicModel.Components.TagComponent";
    private IntentFilter intentFilter;
    private ControlBroadcastReceiver controlBroadcastReceiver;

    private String name;

    public TagComponent(Context context, String name) {
        super(context);
        this.context = context;
        initLinearLayout(context);
        drawLinearLayout();
        this.name = name;
        binding.tagViewName.setText(name);

        isChoosen = true;
        refresh();
    }

    @Override
    protected void onDetachedFromWindow() {
        getContext().unregisterReceiver(controlBroadcastReceiver);
        super.onDetachedFromWindow();
    }

    private void initLinearLayout(Context context){
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_tag, this, true);

        intentFilter = new IntentFilter();
        controlBroadcastReceiver = new ControlBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(controlBroadcastReceiver, intentFilter);

        binding.tagViewName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(intentAction);
                intent.putExtra("name", name);
                getContext().sendBroadcast(intent);
            }
        });
    }

    private void drawLinearLayout(){
        setMargin(binding.tagViewMainView, 6, 10, 6, 10, false);

        setH(binding.tagViewName, 40);
        setMargin(binding.tagViewName, 0, 0, 0, 0, true);
        setTextSize(binding.tagViewName, 16);
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
                if (name.equals(data)){
                    if (!isChoosen) refresh();
                }else {
                    if (isChoosen) refresh();
                }
            }
        }
    }
}

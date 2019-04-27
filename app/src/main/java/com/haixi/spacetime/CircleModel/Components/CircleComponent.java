package com.haixi.spacetime.CircleModel.Components;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ComponentCircleBinding;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.setTextSize;

public class CircleComponent extends LinearLayout {
    private ComponentCircleBinding binding;
    private String name;
    private String intentAction = "";
    private int intentAction_type;
    public CircleComponent(Context context, String name) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_circle, this, true);
        this.name = name;
        drawComponent();
        initLinearLayout();
    }

    public CircleComponent(Context context, String name, int imageId){
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_circle, this, true);
        this.name = name;
        drawComponent();
        initLinearLayout();
        drawImage(imageId);
    }

    private void initLinearLayout(){
        binding.componentCircleName.setText(name);
    }

    private void drawComponent(){
        setH(binding.componentCircleName, 30);
        setMargin(binding.componentCircleName, 0, 19, 0, 10, false);
        setTextSize(binding.componentCircleName, 20);

        setH(binding.componentCircleLine, 1);
    }

    private void drawImage(int imageId){
        setHW(binding.componentCircleImage, 50, 50);
        setMargin(binding.componentCircleImage, 0, 10, 15, 9, false);

        binding.componentCircleImage.setImageResource(imageId);
    }

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
                intent.putExtra("data", name);
                intent.putExtra("type", intentAction_type);
                getContext().sendBroadcast(intent);
            }
        });
    }
}

package com.haixi.spacetime.UserModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ComponentChooseBinding;

import static com.haixi.spacetime.Others.Settings.setH;
import static com.haixi.spacetime.Others.Settings.setMargin;
import static com.haixi.spacetime.Others.Settings.setHW;
import static com.haixi.spacetime.Others.Settings.setTextSize;

public class ChooseComponent extends LinearLayout {
    private Context context;
    public boolean isAllowed;

    public ComponentChooseBinding binding;
    public ChooseComponent(Context context, String text) {
        super(context);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(this.context),
                R.layout.component_choose, this, true);
        binding.optionLayoutChooseText.setText(text + "");
        isAllowed = false;
    }

    public void drawView(){
        setMargin(this, 13, 10, 13, 10, true);

        setH(binding.optionLayoutChooseText, 26);
        setMargin(binding.optionLayoutChooseText, 0,12,0,12,true);
        setTextSize(binding.optionLayoutChooseText,18);

        setHW(binding.optionLayoutChooseGetNotification, 30, 50);
        setMargin(binding.optionLayoutChooseGetNotification, 0, 10, 0, 10,
                false);

        setH(binding.optionLayoutChooseLine, 3);
        setMargin(binding.optionLayoutChooseLine,0,5,0,5,false);
    }
}

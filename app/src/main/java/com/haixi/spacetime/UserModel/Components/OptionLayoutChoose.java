package com.haixi.spacetime.UserModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.OptionLayoutChooseBinding;

import static com.haixi.spacetime.Others.Settings.setMargin;
import static com.haixi.spacetime.Others.Settings.getPx;
import static com.haixi.spacetime.Others.Settings.setHW;
import static com.haixi.spacetime.Others.Settings.setTextSize;

public class OptionLayoutChoose extends LinearLayout {
    private Context context;
    public boolean isAllowed;

    public OptionLayoutChooseBinding binding;
    public OptionLayoutChoose(Context context, String text) {
        super(context);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(this.context),
                R.layout.option_layout_choose, this, true);
        binding.optionLayoutChooseText.setText(text + "");
        isAllowed = false;
    }

    public void drawView(){
        getLayoutParams().height = getPx(60);
        setMargin(this, 20, 24, 18, 10, true);
        setTextSize(binding.optionLayoutChooseText,18);

        setHW(binding.optionLayoutChooseGetNotification, 30, 50);
    }
}

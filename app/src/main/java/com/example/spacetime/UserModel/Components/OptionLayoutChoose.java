package com.example.spacetime.UserModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.spacetime.R;
import com.example.spacetime.databinding.OptionLayoutChooseBinding;

import static com.example.spacetime.Components.Settings.adaptView;
import static com.example.spacetime.Components.Settings.getPx;
import static com.example.spacetime.Components.Settings.setHW;
import static com.example.spacetime.Components.Settings.setTextSize;

public class OptionLayoutChoose extends LinearLayout {
    private Context context;
    private boolean isAllowed = false;

    public OptionLayoutChooseBinding binding;
    public OptionLayoutChoose(Context context, String text) {
        super(context);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(this.context),
                R.layout.option_layout_choose, this, true);
        binding.optionLayoutChooseText.setText(text + "");
    }

    public void refresh(boolean isAllowed){
        if (isAllowed == this.isAllowed){
            return;
        }else {
            binding.optionLayoutChooseGetNotification.performClick();
        }
    }

    public void drawView(){
        getLayoutParams().height = getPx(60);
        adaptView(this, 20, 24, 18, 10, true);
        setTextSize(binding.optionLayoutChooseText,18);

        setHW(binding.optionLayoutChooseGetNotification, 30, 50);
    }
}

package com.haixi.spacetime.UserModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ComponentTurnBinding;

import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setTextSize;

public class TurnComponent extends LinearLayout {
    public ComponentTurnBinding binding;
    private Context context;
    public TurnComponent(Context context, String text) {
        super(context);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(this.context),
                R.layout.component_turn, this, true);
        binding.optionLayoutTurnText.setText(text + "");
    }

    public void drawView(){
        setMargin(this, 13, 10, 13, 10, true);

        setH(binding.optionLayoutTurnText, 26);
        setMargin(binding.optionLayoutTurnText,0,12,0,12,true);
        setTextSize(binding.optionLayoutTurnText,18);

        setHW(binding.optionLayoutTurnImage, 24, 24);
        setMargin(binding.optionLayoutTurnImage,0,13,0,13,false);

        setH(binding.optionLayoutTurnLine, 3);
        setMargin(binding.optionLayoutTurnLine, 0, 5, 0, 5, false);
    }
}

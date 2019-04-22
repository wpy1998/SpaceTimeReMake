package com.haixi.spacetime.UserModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.OptionLayoutTurnBinding;

import static com.haixi.spacetime.Others.Settings.setH;
import static com.haixi.spacetime.Others.Settings.setMargin;
import static com.haixi.spacetime.Others.Settings.getPx;
import static com.haixi.spacetime.Others.Settings.setHW;
import static com.haixi.spacetime.Others.Settings.setTextSize;

public class OptionLayoutTurn extends LinearLayout {
    public OptionLayoutTurnBinding binding;
    private Context context;
    public OptionLayoutTurn(Context context, String text) {
        super(context);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(this.context),
                R.layout.option_layout_turn, this, true);
        binding.optionLayoutTurnText.setText(text + "");
    }

    public void drawView(){
        setH(this, 60);
        setMargin(this, 13, 10, 13, 10, true);

        setH(binding.optionLayoutTurnText, 26);
        setMargin(binding.optionLayoutTurnText, 0, 12, 0, 12, false);
        setTextSize(binding.optionLayoutTurnText,18);

        setHW(binding.optionLayoutTurnImage, 24, 24);
        setMargin(binding.optionLayoutTurnImage, 0, 12, 0, 12, false);

        setH(binding.optionLayoutTurnLine, 4);
    }
}

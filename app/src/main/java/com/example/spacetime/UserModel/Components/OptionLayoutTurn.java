package com.example.spacetime.UserModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.spacetime.R;
import com.example.spacetime.databinding.OptionLayoutTurnBinding;

import static com.example.spacetime.Components.Settings.adaptView;
import static com.example.spacetime.Components.Settings.getPx;
import static com.example.spacetime.Components.Settings.setHW;
import static com.example.spacetime.Components.Settings.setTextSize;

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
        getLayoutParams().height = getPx(60);
        adaptView(this, 20, 24, 18, 10, true);
        setTextSize(binding.optionLayoutTurnText,18);

        setHW(binding.optionLayoutTurnImage, 24, 24);
    }
}

package com.example.spacetime.UserModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.spacetime.R;
import com.example.spacetime.databinding.OptionLayoutTurnBinding;

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
}

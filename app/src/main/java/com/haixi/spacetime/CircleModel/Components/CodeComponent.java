package com.haixi.spacetime.CircleModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ComponentCodeBinding;

import static com.haixi.spacetime.Others.Settings.setH;
import static com.haixi.spacetime.Others.Settings.setHW;
import static com.haixi.spacetime.Others.Settings.setMargin;
import static com.haixi.spacetime.Others.Settings.setTextSize;

public class CodeComponent extends LinearLayout {
    private ComponentCodeBinding binding;
    private String name;
    public CodeComponent(Context context, String name) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_code, this, true);
        this.name = name;
        drawComponent();
        initComponent();
    }

    private void initComponent(){
        binding.codeTheme.setText(name);
    }

    private void drawComponent(){
        setHW(binding.codeImage, 160, 160);
        setMargin(binding.codeImage, 0, 40, 0, 0, false);

        setH(binding.codeTheme, 36);
        setMargin(binding.codeTheme, 0, 15, 0, 40, true);
        setTextSize(binding.codeTheme, 25);

        setHW(binding.codeDownImage, 30, 30);
        setMargin(binding.codeDownImage, 0, 25, 0, 25, false);

        setH(binding.codeDownload, 30);
        setMargin(binding.codeDownload, 5, 35, 0, 35, false);
        setTextSize(binding.codeDownload, 20);
    }
}

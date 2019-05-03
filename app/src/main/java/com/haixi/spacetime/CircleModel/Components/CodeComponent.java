package com.haixi.spacetime.CircleModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ComponentCodeBinding;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.setTextSize;

public class CodeComponent extends LinearLayout {
    private ComponentCodeBinding binding;
    private String name;
    private Bitmap bitmap;
    public CodeComponent(Context context, String name, Bitmap bitmap) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_code, this, true);
        this.name = name;
        this.bitmap = bitmap;
        drawComponent();
        initComponent();
    }

    private void initComponent(){
        binding.codeTheme.setText(name);
        binding.codeImage.setImageBitmap(bitmap);
    }

    private void drawComponent(){
        setHW(binding.codeImage, 300, 300);
        setMargin(binding.codeImage, 0, 0, 0, 0, false);

        setH(binding.codeTheme, 36);
        setMargin(binding.codeTheme, 0, 0, 0, 30, true);
        setTextSize(binding.codeTheme, 25);

        setHW(binding.codeDownImage, 30, 30);
        setMargin(binding.codeDownImage, 0, 25, 0, 25, false);

        setH(binding.codeDownload, 30);
        setMargin(binding.codeDownload, 5, 35, 0, 35, false);
        setTextSize(binding.codeDownload, 20);
    }
}

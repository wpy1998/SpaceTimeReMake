package com.haixi.spacetime;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haixi.spacetime.databinding.ComponentPictureBinding;

import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setMargin;

public class PictureComponent extends LinearLayout {
    private ComponentPictureBinding binding;
    public ImageView imageView;

    public PictureComponent(Context context) {
        super(context);
        init();
    }

    public PictureComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PictureComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.component_picture, this, true);
        imageView = binding.componentPictureImage;
        drawComponent();
    }

    private void drawComponent(){
        setHW(binding.componentPictureView, 113, 113);
        setMargin(binding.componentPictureView,1, 1, 1,
                1, false);
    }
}

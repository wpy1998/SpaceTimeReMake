package com.haixi.spacetime.DynamicModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ComponentUserBinding;

import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setMargin;

public class UserComponent extends LinearLayout implements View.OnClickListener {
    private ComponentUserBinding binding;
    private boolean isChoose = false;
    public UserComponent(Context context) {
        super(context);
        initComponent();
        drawComponent();
    }

    private void initComponent(){
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.component_user,
                this, true);
        binding.userComponentMainView.setOnClickListener(this);
        binding.userComponentImage.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void refresh(){
        if (isChoose){
            isChoose = false;
            binding.userComponentMainView.setBackground(getResources().getDrawable(R.drawable.shape_white));
        }else {
            isChoose = true;
            binding.userComponentMainView.setBackground(getResources().getDrawable(R.drawable.shape_blue));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userComponent_mainView:
                refresh();
                break;
            case R.id.userComponent_image:
                refresh();
                break;
            default:
                break;
        }
    }

    private void drawComponent(){
        setHW(binding.userComponentMainView, 56, 56);
        setMargin(binding.userComponentMainView, 8, 5, 8, 5, false);

        setHW(binding.userComponentImage, 50, 50);
        setMargin(binding.userComponentImage, 3, 3, 3, 3, false);
    }
}

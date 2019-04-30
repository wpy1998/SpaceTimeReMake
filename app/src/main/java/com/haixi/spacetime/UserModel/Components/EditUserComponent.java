package com.haixi.spacetime.UserModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ComponentEditUserBinding;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;

public class EditUserComponent extends LinearLayout {
    private ComponentEditUserBinding binding;
    private Context context;
    private boolean isSetImage = false;

    public EditUserComponent(Context context) {
        super(context);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(this.context),
                R.layout.component_edit_user, this, true);
    }

    public void setTitle(String title){
        binding.componentEditUserTitle.setText(title + "");
    }

    public void setImage(int imageId){
        binding.componentEditUserImage.setImageResource(imageId);
        isSetImage = true;
    }

    public void setContent(String content){
        binding.componentEditUserContent.setText(content + "");
    }

    public void drawComponent(){
        setMargin(binding.componentEditUserView, 20, 0, 20, 0, false);

        setHW(binding.componentEditUserTitle, 30, 41);
        setMargin(binding.componentEditUserTitle, 0, 18, 0,
                18, true);
        setTextSize(binding.componentEditUserTitle, 20);

        setH(binding.componentEditUserContent, 26);
        binding.componentEditUserContent.setMaxWidth(getPx(233));
        setMargin(binding.componentEditUserContent, 17, 20, 0,
                20, true);
        setTextSize(binding.componentEditUserContent, 18);

        if (isSetImage){
            setHW(binding.componentEditUserImage, 60, 60);
            setMargin(binding.componentEditUserImage, 0, 10, 0,
                    10, true);
        }else {
            binding.componentEditUserMainView.removeView(binding.componentEditUserImage);
        }

        setHW(binding.componentEditUserNext, 24, 24);
        setMargin(binding.componentEditUserNext, 20, 21, 0,
                21, false);

        setH(binding.componentEditUserLine, 2);
        setMargin(binding.componentEditUserLine, 0, 5, 0,
                5, false);
    }
}

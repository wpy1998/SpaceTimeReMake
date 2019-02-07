package com.example.spacetime.UserModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.spacetime.R;
import com.example.spacetime.databinding.ItemUserImageViewBinding;

public class UserImageView extends LinearLayout{
    public String message = "userImageView", picturePath;
    public int width, height;
    Context context;

    private ItemUserImageViewBinding binding;
    public UserImageView(Context context) {
        super(context);
        this.context = context;
        initLinearLayout();
    }

    public UserImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initLinearLayout();
    }

    private void initLinearLayout() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(this.context),
                R.layout.item_user_image_view, this, true);
    }
}

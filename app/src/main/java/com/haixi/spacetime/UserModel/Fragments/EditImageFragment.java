package com.haixi.spacetime.UserModel.Fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haixi.spacetime.Entity.BasicFragment;
import com.haixi.spacetime.Entity.FileOperation;
import com.haixi.spacetime.Entity.OkHttpAction;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentEditImageBinding;

import static com.haixi.spacetime.Entity.Cookies.setBitmap;
import static com.haixi.spacetime.Entity.Settings.getPx;
import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.setTextSize;

@SuppressLint("ValidFragment")
public class EditImageFragment extends BasicFragment {
    private FragmentEditImageBinding binding;
    private TextView title, save;
    private ImageView back;
    private String path;
    public EditImageFragment(String path){
        this.path = path;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_image, null, false);
        title = binding.getRoot().findViewById(R.id.fragmentEditUser_title);
        save = binding.getRoot().findViewById(R.id.fragmentEditUser_save);
        back = binding.getRoot().findViewById(R.id.fragmentEditUser_back);
        title.setText("编辑头像");
        drawFragment();
        FileOperation fileOperation = new FileOperation(getContext());
        Bitmap bitmap = fileOperation.cutBitmap(setBitmap(path));
        binding.fragmentEditImagePicture.setImageBitmap(bitmap);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpAction okHttpAction = new OkHttpAction(getContext());
                if (path != null){
                    okHttpAction.setAvatar(path, 0, "");
                }
                getActivity().finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return binding.getRoot();
    }

    private void drawFragment(){
        setH(binding.getRoot().findViewById(R.id.fragmentEditUser_line0), 2);

        setHW(back, 24, 24);
        setMargin(back, 13, 10, 11, 0, false);

        setTextSize(title,18);
        title.getLayoutParams().height = getPx(26);
        setMargin(title, 0, 11, 0, 8, false);

        save.getLayoutParams().height = getPx(23);
        setMargin(save, 0, 11, 23, 11, false);
        setTextSize(save, 16);

        setHW(binding.fragmentEditImagePicture, 301, 301);
        setMargin(binding.fragmentEditImagePicture, 37, 37,
                37, 37, false);
    }
}

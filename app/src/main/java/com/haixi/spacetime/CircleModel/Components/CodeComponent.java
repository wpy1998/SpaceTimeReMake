package com.haixi.spacetime.CircleModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.haixi.spacetime.Entity.Cookies;
import com.haixi.spacetime.Entity.FileOperation;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ComponentCodeBinding;

import java.io.ByteArrayOutputStream;

import static com.haixi.spacetime.Entity.Cookies.filePath;
import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.setTextSize;

public class CodeComponent extends LinearLayout {
    private ComponentCodeBinding binding;
    private String name;
    private Bitmap bitmap;
    public CodeComponent(Context context, final String name, final Bitmap bitmap) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_code, this, true);
        this.name = name;
        this.bitmap = bitmap;
        drawComponent();
        initComponent();
        binding.codeDownView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] bytes = baos.toByteArray();
                    FileOperation.saveCode(Cookies.owner.userName + "." + name + ".png", bytes);
                    baos.close();
                    Toast.makeText(getContext(), "已保存到" + filePath + "Save", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
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

package com.example.spacetime.Login_and_Register.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentGetVerificationCodeBinding;

public class FragmentGetVerificationCode extends Fragment implements View.OnClickListener {
    private FragmentGetVerificationCodeBinding binding;
    private int areaWhich;

    private TextView chooseArea;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_get_verification_code, null, false);
        binding.getTelephoneChooseArea.setOnClickListener(this);
        binding.getTelephoneNext.setOnClickListener(this);

        chooseArea = binding.getTelephoneChooseArea;
        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getTelephone_chooseArea:
                new AlertDialog.Builder(getContext()).setTitle("请选择您的地区号码").setIcon(
                        R.drawable.myperson).setSingleChoiceItems(
                        new String[] { "中国", "日本1", "日本2", "悉尼", "英国", "印度"}, areaWhich,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                areaWhich = which;
                                if (areaWhich == 0){
                                    chooseArea.setText("+86");
                                }else if (areaWhich == 1){
                                    chooseArea.setText("+080");
                                }else if (areaWhich == 2){
                                    chooseArea.setText("+090");
                                }else if (areaWhich == 3){
                                    chooseArea.setText("+04");
                                }else if (areaWhich == 4){
                                    chooseArea.setText("+91");
                                }else if (areaWhich == 5){
                                    chooseArea.setText("+98");
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.getTelephone_next:
                ARouter.getInstance()
                        .build("/spaceTime/login")
                        .withString("path", "resetPassword")
                        .navigation();
                break;
            default:
                break;
        }
    }
}

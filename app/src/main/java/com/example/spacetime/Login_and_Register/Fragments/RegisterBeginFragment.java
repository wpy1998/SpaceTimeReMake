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
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentRegisterBeginBinding;

public class RegisterBeginFragment extends Fragment implements View.OnClickListener {
    private FragmentRegisterBeginBinding binding;
    private int areaWhich;

    private TextView areaCode;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_begin, null, false);
        binding.registerBeginChooseArea.setOnClickListener(this);
        binding.registerBeginLogin.setOnClickListener(this);
        binding.registerBeginGetVerificationCode.setOnClickListener(this);
        binding.registerBeginNextPage.setOnClickListener(this);
        binding.registerBeginAreaCode.setOnClickListener(this);
        binding.registerBeginAreaCode.setOnClickListener(this);

        areaCode=binding.registerBeginAreaCode;
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_begin_login:
                ARouter.getInstance()
                        .build("/spaceTime/login")
                        .withString("path", "loginBegin")
                        .navigation();
                break;
            case R.id.register_begin_chooseArea:
                new AlertDialog.Builder(getContext()).setTitle("请选择您的地区号码").setIcon(
                        R.drawable.myperson).setSingleChoiceItems(
                        new String[] { "中国", "日本1", "日本2", "悉尼", "英国", "印度"}, areaWhich,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                areaWhich = which;
                                if (areaWhich == 0){
                                    areaCode.setText("+86");
                                }else if (areaWhich == 1){
                                    areaCode.setText("+080");
                                }else if (areaWhich == 2){
                                    areaCode.setText("+090");
                                }else if (areaWhich == 3){
                                    areaCode.setText("+04");
                                }else if (areaWhich == 4){
                                    areaCode.setText("+91");
                                }else if (areaWhich == 5){
                                    areaCode.setText("+98");
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.register_begin_areaCode:
                binding.registerBeginChooseArea.performClick();
                break;
            case R.id.register_begin_nextPage:
                ARouter.getInstance()
                        .build("/spaceTime/register")
                        .withString("path", "completeMessage")
                        .navigation();
            default:
                Toast.makeText(getContext(), "waiting for coming true", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

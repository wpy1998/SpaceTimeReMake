package com.haixi.spacetime.UserModel.Fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentEditSignBinding;

import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;
import static com.haixi.spacetime.Entity.Cookies.owner;

public class EditSignFragment extends BasicFragment implements View.OnClickListener {
    private FragmentEditSignBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_edit_sign,
                null, false);
        drawFragment();
        binding.editSignContent.setText(owner.signature);

        binding.editSignBack.setOnClickListener(this);
        binding.editSignSave.setOnClickListener(this);
        return binding.getRoot();
    }

    private void drawFragment() {
        binding.editSignTitleView.getLayoutParams().height = getPx(58);

        setHW(binding.editSignBack, 24, 24);
        setMargin(binding.editSignBack,13, 10, 0, 24,
                false);

        binding.editSignTitle.getLayoutParams().height = getPx(29);
        setMargin(binding.editSignTitle, 0, 8, 0, 21,
                false);
        setTextSize(binding.editSignTitle, 20);

        setHW(binding.editSignSave,29, 37);
        setMargin(binding.editSignSave, 0, 8, 28, 21,
                false);
        setTextSize(binding.editSignSave, 18);

        setMargin(binding.editSignContent, 20, 0, 20, 0,
                false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_sign_back:
                getActivity().finish();
                break;
            case R.id.edit_sign_save:
                String message = binding.editSignContent.getText().toString();
                owner.signature = message;
                Toast.makeText(getContext(), "已保存到本地, 提交后即可保存",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
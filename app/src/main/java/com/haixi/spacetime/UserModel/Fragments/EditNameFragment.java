package com.haixi.spacetime.UserModel.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.haixi.spacetime.Entity.BasicFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentEditNameBinding;

import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.getPx;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setTextSize;
import static com.haixi.spacetime.Entity.Cookies.owner;

public class EditNameFragment extends BasicFragment implements View.OnClickListener {
    private FragmentEditNameBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_edit_name,
                null, false);
        drawFragment();
        binding.editNameContent.setText(owner.userName);
        binding.editNameBack.setOnClickListener(this);
        binding.editNameSave.setOnClickListener(this);
        return binding.getRoot();
    }

    private void drawFragment() {
        binding.editNameTitleView.getLayoutParams().height = getPx(58);

        setHW(binding.editNameBack, 24, 24);
        setMargin(binding.editNameBack,13, 10, 0, 24,
                false);

        binding.editNameTitle.getLayoutParams().height = getPx(29);
        setMargin(binding.editNameTitle, 0, 8, 0, 21,
                false);
        setTextSize(binding.editNameTitle, 20);

        setHW(binding.editNameSave,29, 37);
        setMargin(binding.editNameSave, 0, 8, 28, 21,
                false);
        setTextSize(binding.editNameSave, 18);

        setMargin(binding.editNameContent, 20, 0, 20, 0,
                false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_name_back:
                getActivity().finish();
                break;
            case R.id.edit_name_save:
                String message = binding.editNameContent.getText().toString();
                owner.userName = message;
                Toast.makeText(getContext(), "已保存到本地, 提交后即可保存",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}

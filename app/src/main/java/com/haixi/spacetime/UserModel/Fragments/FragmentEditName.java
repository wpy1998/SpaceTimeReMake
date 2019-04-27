package com.haixi.spacetime.UserModel.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentEditNameBinding;

import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;

public class FragmentEditName extends Fragment implements View.OnClickListener {
    private FragmentEditNameBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_edit_name,
                null, false);
        init();
        binding.editNameBack.setOnClickListener(this);
        binding.editNameSave.setOnClickListener(this);
        return binding.getRoot();
    }

    private void init() {
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
            case R.id.feedback_back:
                getActivity().finish();
                break;
            case R.id.feedback_save:
                String message = binding.editNameContent.getText().toString();
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

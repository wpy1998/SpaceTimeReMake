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
import com.haixi.spacetime.databinding.FragmentEditSignBinding;

import static com.haixi.spacetime.Others.Settings.setMargin;
import static com.haixi.spacetime.Others.Settings.getPx;
import static com.haixi.spacetime.Others.Settings.setHW;
import static com.haixi.spacetime.Others.Settings.setTextSize;

public class FragmentEditSign extends Fragment implements View.OnClickListener {
    private FragmentEditSignBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_edit_sign,
                null, false);
        init();
        binding.editSignBack.setOnClickListener(this);
        binding.editSignSave.setOnClickListener(this);
        return binding.getRoot();
    }

    private void init() {
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
            case R.id.feedback_back:
                getActivity().finish();
                break;
            case R.id.feedback_save:
                String message = binding.editSignContent.getText().toString();
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

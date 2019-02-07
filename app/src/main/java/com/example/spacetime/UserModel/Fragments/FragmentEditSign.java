package com.example.spacetime.UserModel.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentEditSignBinding;

import static com.example.spacetime.Components.Settings.adaptView;
import static com.example.spacetime.Components.Settings.getPx;
import static com.example.spacetime.Components.Settings.setHW;

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
        adaptView(binding.editSignBack,13, 10, 0, 0, false);

        binding.editSignTitle.getLayoutParams().height = getPx(29);
        adaptView(binding.editSignTitle, 0, 8, 0, 0, false);

        setHW(binding.editSignSave,29, 37);
        adaptView(binding.editSignSave, 0, 8, 0, 0, false);

        binding.editSignContent.getLayoutParams().height = getPx(160);
        adaptView(binding.editSignContent, 20, 0, 20, 0, false);
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

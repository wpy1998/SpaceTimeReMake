package com.example.spacetime.UserModel.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentFeedbackBinding;

import static com.example.spacetime.Others.Settings.adaptView;
import static com.example.spacetime.Others.Settings.getPx;
import static com.example.spacetime.Others.Settings.setHW;
import static com.example.spacetime.Others.Settings.setTextSize;

public class FragmentFeedback extends Fragment implements View.OnClickListener {
    private FragmentFeedbackBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback,
                null, false);
        init();
        binding.feedbackBack.setOnClickListener(this);
        binding.feedbackSave.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feedback_back:
                getActivity().finish();
                break;
            case R.id.feedback_save:
                String message = binding.feedbackContent.getText().toString();
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void init() {
        binding.feedbackTitleView.getLayoutParams().height = getPx(58);

        setHW(binding.feedbackBack, 24, 24);
        adaptView(binding.feedbackBack,13, 10, 0, 24,
                false);

        binding.feedbackTitle.getLayoutParams().height = getPx(29);
        adaptView(binding.feedbackTitle, 0, 8, 0, 21,
                false);
        setTextSize(binding.feedbackTitle, 20);

        binding.feedbackSave.getLayoutParams().height = getPx(29);
        adaptView(binding.feedbackSave, 0, 8, 28, 21,
                false);
        setTextSize(binding.feedbackSave, 18);

        adaptView(binding.feedbackContent, 20, 0, 20, 0,
                false);
        binding.feedbackContent.setMinimumHeight(getPx(160));
    }
}

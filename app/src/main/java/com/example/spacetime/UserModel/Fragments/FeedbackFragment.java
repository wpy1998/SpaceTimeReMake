package com.example.spacetime.UserModel.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spacetime.Others.BasicFragment;
import com.example.spacetime.Others.OkHttpAction;
import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentFeedbackBinding;

import static com.example.spacetime.Others.Settings.adaptView;
import static com.example.spacetime.Others.Settings.getPx;
import static com.example.spacetime.Others.Settings.setHW;
import static com.example.spacetime.Others.Settings.setTextSize;

public class FeedbackFragment extends BasicFragment implements View.OnClickListener {
    private FragmentFeedbackBinding binding;
    private final String intentAction = "com.example.spacetime.UserModel.Fragments.FeedbackFragment";
    private final int intentAction_feedBack = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback,
                null, false);
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadCastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);

        okHttpAction = new OkHttpAction(getContext());

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

    private class UserInfoBroadCastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String data, action = intent.getAction();
            if (!action.equals(intentAction)){
                return;
            }
            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_feedBack:
                    break;
                default:
                    break;
            }
        }
    }
}
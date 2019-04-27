package com.haixi.spacetime.CircleModel.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haixi.spacetime.CircleModel.Components.CircleComponent;
import com.haixi.spacetime.CircleModel.Components.CodeComponent;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentCircleMessageBinding;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.setTextSize;

@SuppressLint("ValidFragment")
public class CircleMessageFragment extends BasicFragment implements View.OnClickListener {
    private FragmentCircleMessageBinding binding;
    private LinearLayout mainView;
    private String name;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    private CodeComponent codeComponent;

    public CircleMessageFragment(){
        this.name = "";
    }

    public CircleMessageFragment(String name){
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_circle_message,
                null, false);
        mainView = binding.getRoot().findViewById(R.id.titleSecondCircle_container);
        drawFragment();
        binding.circleMessageTitle.setText(name);
        mainView.addView(new CircleComponent(getContext(), "William", R.drawable.william));
        mainView.addView(new CircleComponent(getContext(), "Daniel", R.drawable.daniel));
        mainView.addView(new CircleComponent(getContext(), "Jack", R.drawable.jack));

        binding.circleMessageBack.setOnClickListener(this);
        binding.circleMessageRight.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circleMessage_back:
                getActivity().finish();
                break;
            case R.id.circleMessage_right:
                codeComponent = new CodeComponent(getContext(), name);
                builder = new AlertDialog.Builder(getContext());
                builder.setView(codeComponent);
                builder.show();
                break;
            default:
                break;
        }
    }

    private void drawFragment(){
        setHW(binding.circleMessageBack, 24, 24);
        setMargin(binding.circleMessageBack, 15, 18, 5, 18, false);

        setH(binding.circleMessageTitle, 40);
        setMargin(binding.circleMessageTitle, 0, 10, 0, 10, true);
        setTextSize(binding.circleMessageTitle, 24);

        setHW(binding.circleMessageRight, 30, 30);
        setMargin(binding.circleMessageRight, 15, 15, 15, 15, false);

        setMargin(binding.getRoot().findViewById(R.id.titleSecondCircle_container),
                15, 0, 15, 0, false);
    }
}

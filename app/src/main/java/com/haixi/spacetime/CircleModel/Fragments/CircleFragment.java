package com.haixi.spacetime.CircleModel.Fragments;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.CircleModel.Components.CircleComponent;
import com.haixi.spacetime.Common.Components.BasicFragment;
import com.haixi.spacetime.Common.Components.OkHttpAction;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentCircleBinding;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;

public class CircleFragment extends BasicFragment implements View.OnClickListener {
    private FragmentCircleBinding binding;
    private LinearLayout mainView;
    private final String intentAction = "com.haixi.spacetime.CircleModel.Fragments.CircleFragment";
    private final int intentAction_getCircleData = 1, intentAction_addCircle = 2;
    private IntentFilter intentFilter;
    private OkHttpAction okHttpAction;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_circle,
                null, false);

        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);

        okHttpAction = new OkHttpAction(getContext());
        mainView = binding.getRoot().findViewById(R.id.titleSecondCircle_container);
        drawView();
        setCircle("吃鸡小分队");
        setCircle("王者战队");
        setCircle("大社联");
        setCircle("15届软院学生会");
        setCircle("漫圈");
        setCircle("潮牌圈");

        binding.fragmentCircleAdd.setOnClickListener(this);

        return binding.getRoot();
    }

    private void drawView() {
        setH(binding.fragmentCircleTitle, 40);
        setMargin(binding.fragmentCircleTitle, 15, 10, 0,
                10, false);
        setTextSize(binding.fragmentCircleTitle, 24);

        setHW(binding.fragmentCircleAdd, 30, 30);
        setMargin(binding.fragmentCircleAdd, 0, 15, 25,
                15, false);

        setMargin(binding.getRoot().findViewById(R.id.titleSecondCircle_container),
                15, 0, 15, 0, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragmentCircle_add:
                ARouter.getInstance()
                        .build("/spaceTime/circle")
                        .withString("path", "addCircle")
                        .navigation();
                break;
            default:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setCircle(String name){
        CircleComponent view = new CircleComponent(getContext(), name);
        view.setIntentAction(intentAction, intentAction_getCircleData);
        mainView.addView(view);
    }

    private class UserInfoBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(), data;
            if (!action.equals(intentAction)){
                return;
            }
            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_getCircleData:
                    data = intent.getStringExtra("data");
                    ARouter.getInstance()
                            .build("/spaceTime/circle")
                            .withString("path", "circleMessage")
                            .withString("data", data)
                            .navigation();
                    break;
                default:
                    break;
            }
        }
    }
}

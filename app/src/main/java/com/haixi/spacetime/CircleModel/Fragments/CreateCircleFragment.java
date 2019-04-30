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
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.Common.OkHttpAction;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentCreateCircleBinding;

import org.json.JSONObject;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.setTextSize;
import static com.haixi.spacetime.Common.Settings.setW;

public class CreateCircleFragment extends BasicFragment implements View.OnClickListener {
    private FragmentCreateCircleBinding binding;
    private LinearLayout mainView;
    private final int intentAction_createCircle = 1;
    private final String intentAction = "com.haixi.spacetime.CircleModel." +
            "Fragments.CreateCircleFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_circle,
                null, false);
        okHttpAction = new OkHttpAction(getContext());
        intentFilter = new IntentFilter();
        intentFilter.addAction(intentAction);
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);

        mainView = binding.getRoot().findViewById(R.id.titleSecondCircle_container);
        drawFragment();
        binding.createCircleLeft.setOnClickListener(this);
        binding.createCircleSave.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createCircle_left:
                getActivity().finish();
                break;
            case R.id.createCircle_save:
                String circleName = binding.createCircleName.getText().toString();
                okHttpAction.createCircle(circleName, intentAction_createCircle, intentAction);
                break;
            default:
                break;
        }
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
                case intentAction_createCircle:
                    data = intent.getStringExtra("data");
                    try {
                        JSONObject object = new JSONObject(data);
                        String data1 = object.getString("data");
                        JSONObject object1 = new JSONObject(data1);
                        String name = object1.getString("name");
                        int id = object1.getInt("id");
                        getActivity().finish();
                        ARouter.getInstance()
                                .build("/spaceTime/circle")
                                .withString("path", "circleMessage")
                                .withString("name", name)
                                .withInt("id", id)
                                .navigation();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void drawFragment(){
        setH(binding.createCircleTitle, 40);
        setMargin(binding.createCircleTitle, 0, 10, 0, 10, false);
        setTextSize(binding.createCircleTitle, 24);

        setHW(binding.createCircleLeft, 24, 24);
        setMargin(binding.createCircleLeft, 15, 18, 15, 18, false);

        setHW(binding.createCircleRight, 24, 24);
        setMargin(binding.createCircleRight, 15, 18, 15, 18, false);

        setMargin(mainView, 15, 0, 15, 0, false);

        setH(binding.createCircleTag, 40);
        setMargin(binding.createCircleTag, 0, 15, 0, 0, true);
        setTextSize(binding.createCircleTag, 24);

        setW(binding.createCircleName, 303);
        setMargin(binding.createCircleName, 36, 60, 36, 0, false);
        setTextSize(binding.createCircleName, 24);

        setHW(binding.createCircleSave, 47, 284);
        setMargin(binding.createCircleSave, 44, 40, 47, 0, false);
        setTextSize(binding.createCircleSave, 20);
    }
}

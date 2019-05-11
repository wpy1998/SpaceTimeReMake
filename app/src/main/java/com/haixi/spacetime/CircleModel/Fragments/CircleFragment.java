package com.haixi.spacetime.CircleModel.Fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.CircleModel.CircleActivity;
import com.haixi.spacetime.CircleModel.Components.CircleComponent;
import com.haixi.spacetime.Entity.BasicFragment;
import com.haixi.spacetime.Entity.OkHttpAction;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentCircleBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setTextSize;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;
import static com.haixi.spacetime.Entity.Cookies.resultCode;

public class CircleFragment extends BasicFragment implements View.OnClickListener {
    private FragmentCircleBinding binding;
    private LinearLayout mainView;
    private final String intentAction = "com.haixi.spacetime.CircleModel.Fragments.CircleFragment";
    private final int intentAction_turnToCircle = 1, intentAction_getUserCircles = 2;
    private IntentFilter intentFilter;
    private OkHttpAction okHttpAction;
    @SuppressLint("ResourceAsColor")
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
        okHttpAction.getUserCircles(phoneNumber,intentAction_getUserCircles, intentAction);

        drawView();
        mainView = binding.getRoot().findViewById(R.id.titleSecondCircle_container);
        binding.fragmentCircleAdd.setOnClickListener(this);
        binding.fragmentCircleSwipeRefreshLayout
                .setProgressBackgroundColorSchemeColor(R.color.colorWhite);
        binding.fragmentCircleSwipeRefreshLayout.setColorSchemeResources(R.color.colorBackGround,
                R.color.colorBlue, R.color.colorWhite);
        binding.fragmentCircleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout
                .OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void refresh() {
        okHttpAction = new OkHttpAction(getContext());
        okHttpAction.getUserCircles(phoneNumber, intentAction_getUserCircles, intentAction);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragmentCircle_add:
                Intent intent = new Intent(getContext(), CircleActivity.class);
                intent.putExtra("path", "addCircle");
                startActivityForResult(intent, resultCode);
                break;
            default:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void addCircle(Circle circle){
        CircleComponent view = new CircleComponent(getContext(), circle);
        view.setIntentAction(intentAction, intentAction_turnToCircle);
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
                case intentAction_turnToCircle:
                    String circleName = intent.getStringExtra("circleName");
                    int circleId = intent.getIntExtra("circleId", -1);
                    ARouter.getInstance()
                            .build("/spaceTime/circle")
                            .withString("path", "circleMessage")
                            .withString("circleName", circleName)
                            .withInt("circleId", circleId)
                            .navigation();
                    break;

                case intentAction_getUserCircles:
                    data = intent.getStringExtra("data");
                    try {
                        mainView.removeAllViews();
                        JSONObject object = new JSONObject(data);
                        String circles = object.getString("data");
                        JSONArray array = new JSONArray(circles);
                        for (int i = 0; i < array.length(); i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            Circle circle = new Circle();
                            circle.name = jsonObject.getString("name");
                            circle.id = jsonObject.getInt("id");
                            addCircle(circle);
                        }
                        binding.fragmentCircleSwipeRefreshLayout.setRefreshing(false);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private void drawView() {
        setH(binding.fragmentCircleTitle, 40);
        setMargin(binding.fragmentCircleTitle, 15, 10, 0,
                10, false);
        setTextSize(binding.fragmentCircleTitle, 24);

        setHW(binding.fragmentCircleAdd, 24, 24);
        setMargin(binding.fragmentCircleAdd, 0, 20, 25,
                16, false);

        setMargin(binding.getRoot().findViewById(R.id.titleSecondCircle_container),
                15, 0, 15, 0, false);
    }
}

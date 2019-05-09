package com.haixi.spacetime.DynamicModel.Fragments;

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
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Entity.BasicFragment;
import com.haixi.spacetime.Entity.OkHttpAction;
import com.haixi.spacetime.DynamicModel.Components.DynamicComponent;
import com.haixi.spacetime.DynamicModel.Components.TagComponent;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.Entity.Dynamic;
import com.haixi.spacetime.R;
import com.haixi.spacetime.UserModel.UserActivity;
import com.haixi.spacetime.databinding.FragmentFollowBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Entity.Cookies.phoneNumber;
import static com.haixi.spacetime.Entity.Cookies.resultCode;
import static com.haixi.spacetime.Entity.Dynamic.setDynamic;

public class FollowFragment extends BasicFragment {
    private FragmentFollowBinding binding;
    private final String intentAction = "com.haixi.spacetime.DynamicModel" +
            ".Fragments.FollowFragment";
    private List<Dynamic> dynamics;
    private final int intentAction_getDynamic = 1, intentAction_getCircle = 2;
    private List<Circle> tags;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_follow,
                null, false);
        userInfoBroadcastReceiver = new ControlBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);
        dynamics = new ArrayList<>();
        tags = new ArrayList<>();
        okHttpAction = new OkHttpAction(getContext());
        okHttpAction.getUserCircles(phoneNumber, intentAction_getCircle, intentAction);
        okHttpAction.getOwnerFollowUser(intentAction_getDynamic, intentAction);
        binding.fragmentFollowSwipeRefreshLayout
                .setProgressBackgroundColorSchemeColor(R.color.colorWhite);
        binding.fragmentFollowSwipeRefreshLayout.setColorSchemeResources(R.color.colorBackGround,
                R.color.colorBlue, R.color.colorWhite);
        binding.fragmentFollowSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout
                .OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

    @Override
    public void refresh() {
        okHttpAction.getUserCircles(phoneNumber, intentAction_getCircle, intentAction);
        okHttpAction.getOwnerFollowUser(intentAction_getDynamic, intentAction);
    }

    private void refreshDynamic(){
        binding.fragmentFollowMainView.removeAllViews();
        for (int i = dynamics.size() - 1; i >= 0; i--){
            Dynamic dynamic = dynamics.get(i);
            addDynamicContent(dynamic);
        }
        binding.fragmentFollowSwipeRefreshLayout.setRefreshing(false);
    }

    private void addDynamicContent(Dynamic dynamic){
        DynamicComponent dynamicComponent = new DynamicComponent(getContext(),dynamic, null);
        dynamics.add(dynamic);
        binding.fragmentFollowMainView.addView(dynamicComponent);
        refreshDynamic_addAction(dynamicComponent);
    }

    private void refreshDynamic_addAction(final DynamicComponent dynamicComponent){
        dynamicComponent.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserActivity.class);
                intent.putExtra("path", "user");
                intent.putExtra("userTelephone", dynamicComponent.dynamic.user.phoneNumber);
                intent.putExtra("userName", dynamicComponent.dynamic.user.userName);
                startActivityForResult(intent, resultCode);
            }
        });

        dynamicComponent.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicComponent.userName.performClick();
            }
        });

        dynamicComponent.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/spaceTime/dynamic")
                        .withString("path", "comment")
                        .withString("dynamic", dynamicComponent.dynamic.getJSONString())
                        .navigation();
            }
        });

        dynamicComponent.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private synchronized void synTagAndDynamic(){
        if (tags.size() == 0 || dynamics.size() == 0){
            return;
        }
        for (int i = 0; i < tags.size(); i++){
            Circle circle = tags.get(i);
            for (Dynamic dynamic: dynamics){
                if (dynamic.circle.id == circle.id){
                    dynamic.circle.name = circle.name;
                }
            }
        }
        refreshDynamic();
    }

    private class ControlBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(), data;
            if (!action.equals(intentAction)){
                return;
            }
            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_getDynamic:
                    data = intent.getStringExtra("data");
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        String data1 = jsonObject.getString("data");
                        JSONArray array = new JSONArray(data1);
                        dynamics.clear();
                        for (int i = 0; i < array.length(); i++){
                            JSONObject object = array.getJSONObject(i);
                            Dynamic dynamic = new Dynamic();
                            setDynamic(dynamic, object);
                            dynamics.add(dynamic);
                        }
                        synTagAndDynamic();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

                case intentAction_getCircle:
                    data = intent.getStringExtra("data");
                    try {
                        tags.clear();
                        Circle circle = new Circle();
                        circle.id = -1;
                        circle.name = "全部";
                        tags.add(circle);
                        JSONObject object = new JSONObject(data);
                        String circles = object.getString("data");
                        JSONArray array = new JSONArray(circles);
                        for (int i = 0; i < array.length(); i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            Circle circle1 = new Circle();
                            circle1.name = jsonObject.getString("name");
                            circle1.id = jsonObject.getInt("id");
                            tags.add(circle1);
                        }
                        synTagAndDynamic();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }
    }
}

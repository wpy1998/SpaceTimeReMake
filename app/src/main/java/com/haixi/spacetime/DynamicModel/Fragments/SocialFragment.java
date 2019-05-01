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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Common.OkHttpAction;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.DynamicModel.Components.TagComponent;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.Entity.Dynamic;
import com.haixi.spacetime.R;
import com.haixi.spacetime.DynamicModel.Components.DynamicComponent;
import com.haixi.spacetime.UserModel.UserActivity;
import com.haixi.spacetime.databinding.FragmentSocialBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.setW;
import static com.haixi.spacetime.Entity.Cookies.owner;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;
import static com.haixi.spacetime.Entity.Cookies.resultCode;
import static com.haixi.spacetime.Entity.Cookies.token;
import static com.haixi.spacetime.Entity.Dynamic.setDynamic;

@SuppressLint("ValidFragment")
public class SocialFragment extends BasicFragment{
    private FragmentSocialBinding binding;
    private String intentAction = "com.haixi.spacetime.DynamicModel" +
            ".Fragments.SocialFragment";
    private final int intentAction_selectTag = 1, intentAction_getDynamic = 2,
        intentAction_getCircle = 3;
    private User user;
    private Circle currentCircle;
    private List<Dynamic> dynamics;
    private List<Circle> tags;

    public SocialFragment(){
        this.user = null;
    }

    public SocialFragment(User user){
        this.user = user;
        intentAction = intentAction + "." + user.phoneNumber;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_social,
                null, false);
        drawFragment();
        currentCircle = new Circle();
        currentCircle.name = "全部";
        currentCircle.id = -1;
        okHttpAction = new OkHttpAction(getContext());
        userInfoBroadcastReceiver = new ControlBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);
        dynamics = new ArrayList<Dynamic>();
        tags = new ArrayList<Circle>();

        refreshDynamic();
        refresh();
        if (user != null){
            binding.fragmentSocialView.removeView(binding.fragmentSocialTagView);
            binding.fragmentSocialView.removeView(binding.fragmentSocialTop);
            return binding.getRoot();
        }
        refreshTag();
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

    @Override
    public void refresh() {
        if (token == null){
            return;
        }
        okHttpAction = new OkHttpAction(getContext());
        if (user == null){
            okHttpAction.getUserCircles(phoneNumber, intentAction_getCircle, intentAction);
            okHttpAction.getOwnerAllVisibleCircleDynamic(intentAction_getDynamic, intentAction);
            return;
        }else if (user.phoneNumber.equals(phoneNumber)){
            okHttpAction.getUserCircles(phoneNumber, intentAction_getCircle, intentAction);
            okHttpAction.getUserDynamic(phoneNumber, intentAction_getDynamic, intentAction);
        }else {
            okHttpAction.getUserCircles(user.phoneNumber, intentAction_getCircle, intentAction);
            okHttpAction.getUserDynamic(user.phoneNumber, intentAction_getDynamic, intentAction);
        }
    }

    public void setUser(User user){
        if (this.user == null)
            return;
        this.user = user;
    }

    private void refreshDynamic(){
        binding.fragmentSocialMainView.removeAllViews();
        if (user != null){
            for (int i = dynamics.size() - 1; i >= 0; i--){
                Dynamic dynamic = dynamics.get(i);
                DynamicComponent dynamicComponent = new DynamicComponent(getContext(),dynamic, user);
                binding.fragmentSocialMainView.addView(dynamicComponent);
            }
        }else {
            for (int i = dynamics.size() - 1; i >= 0; i--){
                Dynamic dynamic = dynamics.get(i);
                if (currentCircle.name.equals("全部") || currentCircle.id == dynamic.circle.id) {
                    final DynamicComponent dynamicComponent =
                            new DynamicComponent(getContext(),dynamic, null);
                    binding.fragmentSocialMainView.addView(dynamicComponent);
                    refreshDynamic_addAction(dynamicComponent);
                }
            }
        }
    }

    private void refreshDynamic_addAction(final DynamicComponent dynamicComponent){
        dynamicComponent.titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserActivity.class);
                intent.putExtra("path", "user");
                intent.putExtra("userTelephone", dynamicComponent.dynamic.user.phoneNumber);
                intent.putExtra("userName", dynamicComponent.dynamic.user.userName);
                startActivityForResult(intent, resultCode);
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
    }

    private void refreshTag() {
        if (user != null) return;
        binding.fragmentSocialTagView.removeAllViews();
        for (Circle tag: tags){
            TagComponent tagComponent = new TagComponent(getContext(), tag);
            tagComponent.setIntent(intentAction, intentAction_selectTag);
            binding.fragmentSocialTagView.addView(tagComponent);
            if (tagComponent.getName().equals(currentCircle.name)) {
                tagComponent.refresh();
            }
        }
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
        if (user != null){
            return;
        }
        refreshTag();
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
                case intentAction_selectTag:
                    currentCircle.name = intent.getStringExtra("name");
                    currentCircle.id = intent.getIntExtra("circleId", 0);
                    refreshDynamic();
                    break;

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

    private void drawFragment(){
        if (user != null){
            setW(binding.fragmentSocialMainView, 375);
        }
        setMargin(binding.fragmentSocialTop, 0, 43,
                0, 0, false);
    }
}

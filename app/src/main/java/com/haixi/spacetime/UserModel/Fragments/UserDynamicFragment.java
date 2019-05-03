package com.haixi.spacetime.UserModel.Fragments;

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
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.Common.OkHttpAction;
import com.haixi.spacetime.DynamicModel.Components.DynamicComponent;
import com.haixi.spacetime.DynamicModel.Components.TagComponent;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.Entity.Dynamic;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.R;
import com.haixi.spacetime.UserModel.UserActivity;
import com.haixi.spacetime.databinding.FragmentUserDynamicBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.setW;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;
import static com.haixi.spacetime.Entity.Cookies.resultCode;
import static com.haixi.spacetime.Entity.Cookies.token;
import static com.haixi.spacetime.Entity.Dynamic.setDynamic;

@SuppressLint("ValidFragment")
public class UserDynamicFragment extends BasicFragment {
    private FragmentUserDynamicBinding binding;
    private String intentAction = "com.haixi.spacetime.DynamicModel" +
            ".Fragments.UserDynamicFragment";
    private final int intentAction_getDynamic = 1, intentAction_getCircle = 2;
    private User user;
    private List<Dynamic> dynamics;
    private List<Circle> tags;

    public UserDynamicFragment(User user){
        this.user = user;
        intentAction = intentAction + "." + user.phoneNumber;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_dynamic,
                null, false);
        drawFragment();
        okHttpAction = new OkHttpAction(getContext());
        userInfoBroadcastReceiver = new ControlBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);
        dynamics = new ArrayList<>();
        tags = new ArrayList<>();
        okHttpAction.getUserDynamic(phoneNumber, intentAction_getDynamic, intentAction);

        refresh();
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
        if (user.phoneNumber.equals(phoneNumber)){
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
        refresh();
    }

    private void refreshDynamic(){
        binding.fragmentUserDynamicMainView.removeAllViews();
        for (int i = dynamics.size() - 1; i >= 0; i--){
            Dynamic dynamic = dynamics.get(i);
            DynamicComponent dynamicComponent = new DynamicComponent(getContext(),dynamic, user);
            binding.fragmentUserDynamicMainView.addView(dynamicComponent);
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

    private void drawFragment(){
        setW(binding.fragmentUserDynamicMainView, 375);
    }
}

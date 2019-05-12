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
import android.widget.Toast;

import com.haixi.spacetime.Entity.BasicFragment;
import com.haixi.spacetime.Entity.FileOperation;
import com.haixi.spacetime.Entity.OkHttpAction;
import com.haixi.spacetime.DynamicModel.Components.DynamicComponent;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.Entity.Dynamic;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentUserDynamicBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Entity.Cookies.accessKeyId;
import static com.haixi.spacetime.Entity.Cookies.accessKeySecret;
import static com.haixi.spacetime.Entity.Cookies.securityToken;
import static com.haixi.spacetime.Entity.Cookies.setImageToken;
import static com.haixi.spacetime.Entity.Settings.setW;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;
import static com.haixi.spacetime.Entity.Cookies.token;
import static com.haixi.spacetime.Entity.Dynamic.setDynamic;

@SuppressLint("ValidFragment")
public class UserDynamicFragment extends BasicFragment {
    private FragmentUserDynamicBinding binding;
    private String intentAction = "com.haixi.spacetime.DynamicModel" +
            ".Fragments.UserDynamicFragment";
    private final int intentAction_getDynamic = 1, intentAction_getCircle = 2,
            intentAction_downLoad = 3, intentAction_getImageToken = 4;
    public User user;
    private List<Dynamic> dynamics;
    private List<Circle> tags;
    private int number = 0, count = 0;
    private List<String> names;

    public UserDynamicFragment(User user){
        this.user = user;
    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_dynamic,
                null, false);
        intentAction = intentAction + "." + user.phoneNumber;
        userInfoBroadcastReceiver = new ControlBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);
        drawFragment();
        dynamics = new ArrayList<>();
        tags = new ArrayList<>();
        names = new ArrayList<>();
        refresh();
        return binding.getRoot();
    }

    @Override
    public void refresh() {
        if (token.equals("")){
            return;
        }
        number = 0;
        count = 0;
        okHttpAction = new OkHttpAction(getContext());
        if (accessKeyId.equals("") || accessKeySecret.equals("") || securityToken.equals("")){
            okHttpAction.getImageToken(intentAction_getImageToken, intentAction);
        }else if (user.phoneNumber.equals(phoneNumber)){
            okHttpAction.getUserCircles(phoneNumber, intentAction_getCircle, intentAction);
            okHttpAction.getUserDynamic(phoneNumber, intentAction_getDynamic, intentAction);
        }else {
            okHttpAction.getUserCircles(user.phoneNumber, intentAction_getCircle, intentAction);
            okHttpAction.getUserDynamic(user.phoneNumber, intentAction_getDynamic, intentAction);
        }
    }

    private void refreshDynamic(){
        binding.fragmentUserDynamicMainView.removeAllViews();
        for (int i = 0; i < dynamics.size(); i++){
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
        for (String name: names){
            FileOperation fileOperation = new FileOperation(getContext());
            if (!fileOperation.isFileExist(name)){
                fileOperation.downloadPicture(accessKeyId, accessKeySecret, securityToken, name
                        , intentAction_downLoad, intentAction);
            }else {
                Intent intent1 = new Intent(intentAction);
                intent1.putExtra("type", intentAction_downLoad);
                getContext().sendBroadcast(intent1);
            }
        }
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
                            if (!dynamic.imageUrls.equals("")){
                                count++;
                                names.add(dynamic.imageUrls);
                            }
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

                case intentAction_downLoad:
                    number++;
                    if (number == count){
                        refreshDynamic();
                    }
                    break;

                case intentAction_getImageToken:
                    data = intent.getStringExtra("data");
                    setImageToken(data);
                    refresh();
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

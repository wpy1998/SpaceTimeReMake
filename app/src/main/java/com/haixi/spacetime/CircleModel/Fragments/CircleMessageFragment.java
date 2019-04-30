package com.haixi.spacetime.CircleModel.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
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

import com.haixi.spacetime.CircleModel.Components.CircleComponent;
import com.haixi.spacetime.CircleModel.Components.CodeComponent;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.Common.OkHttpAction;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentCircleMessageBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.setTextSize;

@SuppressLint("ValidFragment")
public class CircleMessageFragment extends BasicFragment implements View.OnClickListener {
    private FragmentCircleMessageBinding binding;
    private LinearLayout mainView;
    private Circle circle;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    private CodeComponent codeComponent;
    private final String intentAction = "com.haixi.spacetime.CircleModel.Fragments." +
            "CircleMessageFragment";
    private final int intentAction_getCircleUserData = 1;

    private List<User> users;

    public CircleMessageFragment(Circle circle){
        this.circle = circle;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_circle_message,
                null, false);
        mainView = binding.getRoot().findViewById(R.id.titleSecondCircle_container);

        okHttpAction = new OkHttpAction(getContext());
        intentFilter = new IntentFilter();
        intentFilter.addAction(intentAction);
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        getActivity().registerReceiver(userInfoBroadcastReceiver, intentFilter);
        drawFragment();
        users = new ArrayList<User>();
        okHttpAction.getCircleUserMessage(circle.id, intentAction_getCircleUserData, intentAction);
        refresh();

        binding.circleMessageBack.setOnClickListener(this);
        binding.circleMessageRight.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void refresh() {
        binding.circleMessageTitle.setText(circle.name);
        for (User user: users){
            mainView.addView(new CircleComponent(getContext(), user));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circleMessage_back:
                getActivity().finish();
                break;
            case R.id.circleMessage_right:
                codeComponent = new CodeComponent(getContext(), circle.name);
                builder = new AlertDialog.Builder(getContext());
                builder.setView(codeComponent);
                builder.show();
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
                case intentAction_getCircleUserData:
                    data = intent.getStringExtra("data");
                    try {
                        JSONObject object = new JSONObject(data);
                        String data1 = object.getString("data");
                        JSONArray array = new JSONArray(data1);
                        users.clear();
                        for (int i = 0; i < array.length(); i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            String telephone = jsonObject.getString("memberPhoneNumber");
                            int circleId = (int) jsonObject.get("socialCircleId");
                            if (circleId != circle.id){
                                continue;
                            }
                            User user = new User();
                            user.phoneNumber = telephone;
                            users.add(user);
                        }
                        refresh();
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

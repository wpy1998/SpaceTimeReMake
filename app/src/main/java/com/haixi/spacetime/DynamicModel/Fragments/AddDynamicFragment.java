package com.haixi.spacetime.DynamicModel.Fragments;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.Common.OkHttpAction;
import com.haixi.spacetime.DynamicModel.Components.TagComponent;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentAddDynamicBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;

public class AddDynamicFragment extends BasicFragment implements View.OnClickListener {
    private FragmentAddDynamicBinding binding;
    private ImageView back;
    private TextView save;
    private final String intentAction = "com.haixi.spacetime.DynamicModel.Fragments" +
            ".AddDynamicFragment";
    private Circle circle;
    private final int intentAction_getUserCircles = 1, intentAction_circleName = 2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_dynamic,
                null, false);
        okHttpAction = new OkHttpAction(getContext());
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);
        okHttpAction.getUserCircles(phoneNumber,intentAction_getUserCircles, intentAction);
        circle = new Circle();
        circle.id = -1;

        save = binding.getRoot().findViewById(R.id.fragmentAddDynamic_save);
        back = binding.getRoot().findViewById(R.id.fragmentAddDynamic_back);

        drawView();
        save.setOnClickListener(this);
        back.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragmentAddDynamic_back:
                getActivity().finish();
                break;
            case R.id.fragmentAddDynamic_save:
                if (binding.fragmentAddDynamicContent.getText().toString().equals("")){
                    Toast.makeText(getContext(), "发布信息不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (circle.id == -1){
                    Toast.makeText(getContext(), "请选择发布的圈子", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getContext(), "动态已发布", Toast.LENGTH_SHORT).show();
                okHttpAction.addDynamicToCircle(circle.id,
                        binding.fragmentAddDynamicContent.getText().toString(),
                        0, intentAction);
                getActivity().finish();
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
                case intentAction_getUserCircles:
                    data = intent.getStringExtra("data");
                    try {
                        JSONObject object = new JSONObject(data);
                        String circles = object.getString("data");
                        JSONArray array = new JSONArray(circles);
                        for (int i = 0; i < array.length(); i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            Circle circle = new Circle();
                            circle.name = jsonObject.getString("name");
                            circle.id = jsonObject.getInt("id");
                            TagComponent tagComponent = new TagComponent(getContext(), circle);
                            tagComponent.setIntent(intentAction, intentAction_circleName);
                            binding.fragmentAddDynamicCircle
                                    .addView(tagComponent);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case intentAction_circleName:
                    circle.name = intent.getStringExtra("name");
                    circle.id = intent.getIntExtra("circleId", -1);
                    break;
                default:
                    break;
            }
        }
    }

    private void drawView() {
        setHW(back, 24, 24);
        setMargin(back, 19, 16, 0,26, false);

        save.getLayoutParams().height = getPx(25);
        setMargin(save, 0, 16, 35, 25, false);
        setTextSize(save, 18);

        setMargin(binding.fragmentAddDynamicContent, 29, 25,
                29, 25, false);
        setTextSize(binding.fragmentAddDynamicContent,16);

        setHW(binding.fragmentAddDynamicImage, 80, 80);
        setMargin(binding.fragmentAddDynamicImage,29, 0, 0,
                10, false);

        binding.fragmentAddDynamicChoose.getLayoutParams().height = getPx(22);
        setMargin(binding.fragmentAddDynamicChoose, 29, 0, 0,
                0, false);

        setMargin(binding.fragmentAddDynamicScroll, 15, 0,
                15, 0, false);
    }
}

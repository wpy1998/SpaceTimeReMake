package com.haixi.spacetime.DynamicModel.Fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Adapter.FragmentAdapter;
import com.haixi.spacetime.DynamicModel.Components.TagComponent;
import com.haixi.spacetime.Entity.BasicFragment;
import com.haixi.spacetime.DynamicModel.DynamicActivity;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.Entity.Dynamic;
import com.haixi.spacetime.Entity.FileOperation;
import com.haixi.spacetime.Entity.OkHttpAction;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentDynamicBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Entity.Cookies.accessKeyId;
import static com.haixi.spacetime.Entity.Cookies.accessKeySecret;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;
import static com.haixi.spacetime.Entity.Cookies.securityToken;
import static com.haixi.spacetime.Entity.Cookies.setImageToken;
import static com.haixi.spacetime.Entity.Cookies.token;
import static com.haixi.spacetime.Entity.Dynamic.setDynamic;
import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.setTextSize;
import static com.haixi.spacetime.Entity.Cookies.resultCode;

public class DynamicFragment extends BasicFragment implements View.OnClickListener{
    private FragmentDynamicBinding binding;

    private String intentAction = "com.haixi.spacetime.DynamicModel" +
            ".Fragments.DynamicFragment";
    private final int intentAction_selectTag = 1, intentAction_getDynamic = 2,
            intentAction_getCircle = 3, intentAction_downLoad = 4, intentAction_getImageToken = 5,
            intentAction_refresh = 6;
    private FragmentAdapter fragmentAdapter;
    private List<SocialFragment> fragments;
    private List<TagComponent> tagComponents;
    private Circle currentCircle;
    private List<Dynamic> dynamics;
    private List<Circle> tags;
    private int number = 0, count = 0;
    private List<String> names;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dynamic,
                null, false);
        fragments = new ArrayList<>();
        tagComponents = new ArrayList<>();
        currentCircle = new Circle();
        currentCircle.name = "全部";
        currentCircle.id = -1;
        userInfoBroadcastReceiver = new ControlBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);
        dynamics = new ArrayList<>();
        tags = new ArrayList<>();
        names = new ArrayList<>();
        drawFragment();
        binding.fragmentDynamicSocialCircle.setTextColor(Color.parseColor("#000000"));
        binding.fragmentDynamicFollow.setTextColor(Color.parseColor("#7F7E80"));
        binding.fragmentDynamicSocialCircle.setOnClickListener(this);
        binding.fragmentDynamicFollow.setOnClickListener(this);
        binding.fragmentDynamicAdd.setOnClickListener(this);
        binding.fragmentDynamicSocialCircle.performClick();
        refresh();
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragmentDynamic_socialCircle:
                refresh();
                break;
            case R.id.fragmentDynamic_follow:
                ARouter.getInstance()
                        .build("/spaceTime/dynamic")
                        .withString("path","follow")
                        .navigation();
                break;
            case R.id.fragmentDynamic_add:
                Intent intent = new Intent(getContext(), DynamicActivity.class);
                intent.putExtra("path", "addDynamic");
                startActivityForResult(intent, resultCode);
                break;
            default:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void refresh() {
        if (token == null){
            return;
        }
        okHttpAction = new OkHttpAction(getContext());
        if (accessKeyId.equals("") || accessKeySecret.equals("") || securityToken.equals("")){
            okHttpAction.getImageToken(intentAction_getImageToken, intentAction);
        }else {
            currentCircle.name = "全部";
            currentCircle.id = -1;
            number = 0;
            count = 0;
            okHttpAction = new OkHttpAction(getContext());
            okHttpAction.getUserCircles(phoneNumber, intentAction_getCircle, intentAction);
            okHttpAction.getOwnerAllVisibleCircleDynamic(intentAction_getDynamic, intentAction);
        }
    }

    public void refreshViewPager(){
        fragments.clear();
        for (Circle circle: tags){
            if (circle.name.equals("全部") && circle.id == -1){
                SocialFragment socialFragment = new SocialFragment(circle, dynamics);
                fragments.add(socialFragment);
            }else {
                SocialFragment socialFragment = new SocialFragment(circle,
                        getDynamics(circle.name));
                fragments.add(socialFragment);
            }
        }
        fragmentAdapter = new FragmentAdapter(
                getActivity().getSupportFragmentManager(), fragments);
        binding.fragmentDynamicViewPager.setAdapter(fragmentAdapter);
        binding.fragmentDynamicViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Rect scrollBounds = new Rect();
                binding.fragmentDynamicViewPager.getHitRect(scrollBounds);
                TagComponent tagComponent = tagComponents.get(i);
                if (!tagComponent.getLocalVisibleRect(scrollBounds)){
                    int move = 0;
                    for (int j = 0; j < i; j++) {
                        move = move + tagComponents.get(j).getWidth();
                    }
                    binding.fragmentDynamicScrollView.smoothScrollTo(move, 0);
                }

                SocialFragment socialFragment = fragments.get(i);
                Intent intent = new Intent(intentAction);
                intent.putExtra("name", socialFragment.circle.name);
                intent.putExtra("circleId", socialFragment.circle.id);
                intent.putExtra("type", intentAction_selectTag);
                intent.putExtra("size", i);
                getContext().sendBroadcast(intent);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public List<Dynamic> getDynamics(String name){
        List<Dynamic> dynamicList = new ArrayList<>();
        for (Dynamic dynamic: dynamics){
            if (dynamic.circle.name.equals(name)){
                dynamicList.add(dynamic);
            }
        }
        return dynamicList;
    }

    private void refreshTag() {
        tagComponents.clear();
        binding.fragmentDynamicTagView.removeAllViews();
        currentCircle.name = "全部";
        currentCircle.id = -1;
        for (int i = 0; i < tags.size(); i++){
            Circle tag = tags.get(i);
            TagComponent tagComponent = new TagComponent(getContext(), tag, i);
            tagComponent.setIntent(intentAction, intentAction_selectTag);
            binding.fragmentDynamicTagView.addView(tagComponent);
            tagComponents.add(tagComponent);
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
                case intentAction_selectTag:
                currentCircle.name = intent.getStringExtra("name");
                currentCircle.id = intent.getIntExtra("circleId", 0);
                for (int i = 0; i < fragments.size(); i++){
                    SocialFragment socialFragment = fragments.get(i);
                    if (socialFragment.circle.name.equals(currentCircle.name) && socialFragment
                            .circle.id == currentCircle.id){
                        binding.fragmentDynamicViewPager.setCurrentItem(i);
                        return;
                    }
                }
                break;

                case intentAction_getDynamic:
                    data = intent.getStringExtra("data");
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        String data1 = jsonObject.getString("data");
                        JSONArray array = new JSONArray(data1);
                        dynamics.clear();
                        names.clear();
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
                        refreshViewPager();
                        refreshTag();
                        Intent intent1 = new Intent(intentAction);
                        intent1.putExtra("type", intentAction_selectTag);
                        intent1.putExtra("circleId", -1);
                        intent1.putExtra("name", "全部");
                        getActivity().sendBroadcast(intent1);
                    }
                    break;

                case intentAction_getImageToken:
                    data = intent.getStringExtra("data");
                    setImageToken(data);
                    refresh();
                    break;

                case intentAction_refresh:
                    refresh();
                    break;

                default:
                    break;
            }
        }
    }

    private void drawFragment(){
        setH(binding.fragmentDynamicSocialCircle, 41);
        setTextSize(binding.fragmentDynamicSocialCircle, 20);
        setMargin(binding.fragmentDynamicSocialCircle, 18, 0, 19,
                2, false);

        setH(binding.fragmentDynamicFollow, 41);
        setTextSize(binding.fragmentDynamicFollow, 20);
        setMargin(binding.fragmentDynamicFollow, 0, 0, 0, 2, false);

        setHW(binding.fragmentDynamicAdd, 25, 25);
        setMargin(binding.fragmentDynamicAdd, 8, 8, 8, 8, true);
    }
}

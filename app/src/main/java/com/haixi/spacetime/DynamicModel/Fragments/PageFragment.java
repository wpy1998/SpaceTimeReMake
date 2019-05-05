package com.haixi.spacetime.DynamicModel.Fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.Common.OkHttpAction;
import com.haixi.spacetime.Common.Others.Adapter.FragmentAdapter;
import com.haixi.spacetime.DynamicModel.Components.DynamicComponent;
import com.haixi.spacetime.DynamicModel.Components.TagComponent;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.Entity.Dynamic;
import com.haixi.spacetime.R;
import com.haixi.spacetime.UserModel.UserActivity;
import com.haixi.spacetime.databinding.FragmentPageBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;
import static com.haixi.spacetime.Entity.Cookies.resultCode;
import static com.haixi.spacetime.Entity.Cookies.token;
import static com.haixi.spacetime.Entity.Dynamic.setDynamic;

public class PageFragment extends BasicFragment {
    private FragmentPageBinding binding;
    private FragmentAdapter fragmentAdapter;
    private List<SocialFragment> fragments;
    private List<TagComponent> tagComponents;

    private String intentAction = "com.haixi.spacetime.DynamicModel" +
            ".Fragments.PageFragment";
    private final int intentAction_selectTag = 1, intentAction_getDynamic = 2,
            intentAction_getCircle = 3;
    private Circle currentCircle;
    private List<Dynamic> dynamics;
    private List<Circle> tags;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_page, null,
                false);
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
        refresh();

        binding.fragmentPageSwipeRefreshLayout
                .setProgressBackgroundColorSchemeColor(R.color.colorWhite);
        binding.fragmentPageSwipeRefreshLayout.setColorSchemeResources(R.color.colorBackGround,
                R.color.colorBlue, R.color.colorWhite);
        binding.fragmentPageSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout
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
        if (token == null){
            return;
        }
        okHttpAction = new OkHttpAction(getContext());
        okHttpAction.getUserCircles(phoneNumber, intentAction_getCircle, intentAction);
        okHttpAction.getOwnerAllVisibleCircleDynamic(intentAction_getDynamic, intentAction);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

    public void refreshViewPager(){
        fragments.clear();
        for (Circle circle: tags){
            if (circle.name.equals("全部")){
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
        binding.fragmentPageViewPager.setAdapter(fragmentAdapter);
        binding.fragmentPageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Rect scrollBounds = new Rect();
                binding.fragmentPageScrollView.getHitRect(scrollBounds);
                TagComponent tagComponent = tagComponents.get(i);
                if (!tagComponent.getLocalVisibleRect(scrollBounds)){
                    int move = 0;
                    for (int j = 0; j < i; j++) {
                        move = move + tagComponents.get(j).getWidth();
                    }
                    binding.fragmentPageScrollView.smoothScrollTo(move, 0);
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
        binding.fragmentPageTagView.removeAllViews();
        for (int i = 0; i < tags.size(); i++){
            Circle tag = tags.get(i);
            TagComponent tagComponent = new TagComponent(getContext(), tag, i);
            tagComponent.setIntent(intentAction, intentAction_selectTag);
            binding.fragmentPageTagView.addView(tagComponent);
            if (tagComponent.getName().equals(currentCircle.name)) {
                tagComponent.refresh();
            }
            tagComponents.add(tagComponent);
        }
        binding.fragmentPageSwipeRefreshLayout.setRefreshing(false);
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
        refreshViewPager();
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
                    for (int i = 0; i < fragments.size(); i++){
                        SocialFragment socialFragment = fragments.get(i);
                        if (socialFragment.circle.name.equals(currentCircle.name) && socialFragment
                                .circle.id == currentCircle.id){
                            binding.fragmentPageViewPager.setCurrentItem(i);
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

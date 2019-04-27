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
import android.widget.Toast;

import com.haixi.spacetime.Common.Entity.User;
import com.haixi.spacetime.DynamicModel.Components.TagComponent;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.DynamicModel.Entity.Dynamic;
import com.haixi.spacetime.R;
import com.haixi.spacetime.DynamicModel.Components.DynamicContentView;
import com.haixi.spacetime.databinding.FragmentSocialBinding;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Common.Entity.Cookies.ownerId;
import static com.haixi.spacetime.Common.Settings.setMargin;

@SuppressLint("ValidFragment")
public class SocialFragment extends BasicFragment{
    private FragmentSocialBinding binding;
    private List<TagComponent> tagComponents;
    private final String intentAction = "com.haixi.spacetime.DynamicModel" +
            ".Fragments.SocialFragment";
    private List<Dynamic> dynamics;
    private User user;

    public SocialFragment(){
        user = new User();
        user.userId = -2;
    }

    public SocialFragment(User user){
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_social,
                null, false);
        drawFragment();
        dynamics = new ArrayList<Dynamic>();
        userInfoBroadcastReceiver = new ControlBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);

        String s1 = "吃鸡党", s2 = "大社联", s3 = "俱乐部", s4 = "潮牌", s5 = "王者荣耀";
        Dynamic dynamic1 = new Dynamic(user.userId);
        dynamic1.imageId = R.drawable.jack;
        dynamic1.name = "Jack";
        dynamic1.content = "过放荡不羁的生活，容易得像顺水推舟，但是要结识良朋益友" +
                "，却难如登天。 —— 巴尔扎克";
        dynamic1.tags = new ArrayList<>();
        dynamic1.tags.add(s1);
        dynamic1.tags.add(s2);
        dynamics.add(dynamic1);
        addDynamicContent(dynamic1);

        Dynamic dynamic2 = new Dynamic(user.userId);
        dynamic2.imageId = R.drawable.william;
        dynamic2.name = "William";
        dynamic2.content = "每当我为世界的现状感到沮丧时，我就会想到伦敦希思罗机场的接机大厅。";
        dynamic2.tags = new ArrayList<>();
        dynamic2.tags.add(s1);
        dynamic2.tags.add(s3);
        dynamics.add(dynamic2);
        addDynamicContent(dynamic2);

        Dynamic dynamic3 = new Dynamic(user.userId);
        dynamic3.imageId = R.drawable.daniel;
        dynamic3.name = "Daniel";
        dynamic3.content = "真理惟一可靠的标准就是永远自相符合。";
        dynamic3.tags = new ArrayList<>();
        dynamic3.tags.add(s1);
        dynamic3.tags.add(s4);
        dynamics.add(dynamic3);
        addDynamicContent(dynamic3);

        Dynamic dynamic4 = new Dynamic(user.userId);
        dynamic4.imageId = R.drawable.jack;
        dynamic4.name = "Jack";
        dynamic4.content = "土地是以它的肥沃和收获而被估价的；才能也是土地，不过它生产的不" +
                "是粮食，而是真理。如果只能滋生瞑想和幻想的话，即使再大的才能也只是砂地或盐池，" +
                "那上面连小草也长不出来的。 —— 别林斯基";
        dynamic4.tags = new ArrayList<>();
        dynamic4.tags.add(s2);
        dynamic4.tags.add(s3);
        dynamics.add(dynamic4);
        addDynamicContent(dynamic4);

        Dynamic dynamic5 = new Dynamic(user.userId);
        dynamic5.imageId = R.drawable.william;
        dynamic5.name = "William";
        dynamic5.content = "我需要三件东西：爱情友谊和图书。然而这三者之间何其相通！炽热的爱情" +
                "可以充实图书的内容，图书又是人们最忠实的朋友。 —— 蒙田";
        dynamic5.tags = new ArrayList<>();
        dynamic5.tags.add(s2);
        dynamic5.tags.add(s4);
        dynamics.add(dynamic5);
        addDynamicContent(dynamic5);

        Dynamic dynamic6 = new Dynamic(user.userId);
        dynamic6.imageId = R.drawable.daniel;
        dynamic6.name = "Daniel";
        dynamic6.content = "世界上一成不变的东西，只有“任何事物都是在不断变化的”" +
                "这条真理。 —— 斯里兰卡";
        dynamic6.tags = new ArrayList<>();
        dynamic6.tags.add(s3);
        dynamic6.tags.add(s4);
        dynamics.add(dynamic6);
        addDynamicContent(dynamic6);
        Toast.makeText(getContext(), "ownerId = " + ownerId, Toast.LENGTH_SHORT).show();

        if (user.userId == ownerId) return binding.getRoot();

        TagComponent tagComponent = new TagComponent(getContext(), "全部");
        tagComponent.setIntent(intentAction);
        TagComponent tagComponent1 = new TagComponent(getContext(), s1);
        tagComponent1.setIntent(intentAction);
        TagComponent tagComponent2 = new TagComponent(getContext(), s2);
        tagComponent2.setIntent(intentAction);
        TagComponent tagComponent3 = new TagComponent(getContext(), s3);
        tagComponent3.setIntent(intentAction);
        TagComponent tagComponent4 = new TagComponent(getContext(), s4);
        tagComponent4.setIntent(intentAction);
        TagComponent tagComponent5 = new TagComponent(getContext(), s5);
        tagComponent5.setIntent(intentAction);

        binding.fragmentSocialTagView.addView(tagComponent);
        binding.fragmentSocialTagView.addView(tagComponent1);
        binding.fragmentSocialTagView.addView(tagComponent2);
        binding.fragmentSocialTagView.addView(tagComponent3);
        binding.fragmentSocialTagView.addView(tagComponent4);
        binding.fragmentSocialTagView.addView(tagComponent5);

        tagComponent.performClick();

        return binding.getRoot();
    }

    private void addDynamicContent(Dynamic dynamic){
        DynamicContentView dynamicContentView = new DynamicContentView(getContext(),dynamic);
        binding.fragmentSocialMainView.addView(dynamicContentView);
    }

    private void refresh(String tag){
        binding.fragmentSocialMainView.removeAllViews();
        for (Dynamic dynamic: dynamics){
            if (dynamic.isTag(tag)){
                addDynamicContent(dynamic);
            }
        }
    }

    private class ControlBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(intentAction)){
                return;
            }
            String tag = intent.getStringExtra("tag");
            refresh(tag);
        }
    }

    private void drawFragment(){
        if (user.userId != ownerId){
            setMargin(binding.fragmentSocialTop, 0, 43,
                    0, 0, false);
        }
    }
}

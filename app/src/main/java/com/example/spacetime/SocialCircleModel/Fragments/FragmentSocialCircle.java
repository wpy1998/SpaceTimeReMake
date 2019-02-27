package com.example.spacetime.SocialCircleModel.Fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.Fragments.FragmentDynamic;
import com.example.spacetime.Login_and_Register.Adapter.FragmentAdapter;
import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentSocialCircleBinding;

import java.util.ArrayList;
import java.util.List;

import static com.example.spacetime.Others.Settings.getPx;
import static com.example.spacetime.Others.Settings.setTextSize;

public class FragmentSocialCircle extends Fragment implements View.OnClickListener {
    private FragmentSocialCircleBinding binding;

    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragments;
    private ViewPager viewPager;
    private Fragment collectionF;
    private FragmentDynamic recommendF;
    private TextView socialCircle, follow;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_social_circle,
                null, false);
        socialCircle = binding.getRoot().findViewById(
                R.id.fragment_topic_socialCircle);
        follow = binding.getRoot().findViewById(R.id.fragment_topic_follow);

        viewPager = binding.getRoot().findViewById(R.id.fragment_topic_viewPager);
        fragments = new ArrayList<Fragment>();
        recommendF = new FragmentDynamic(true);
        collectionF = new Fragment();
        fragments.add(recommendF);
        fragments.add(collectionF);
        fragmentAdapter = new FragmentAdapter(
                getActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getContext(), "position = " + position
                        , Toast.LENGTH_SHORT).show();
                if (position == 0){
                    socialCircle.performClick();
                }else if (position == 1){
                    follow.performClick();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        drawView();
        socialCircle.setOnClickListener(this);
        follow.setOnClickListener(this);
        binding.getRoot().findViewById(R.id.fragment_topic_add).
                setOnClickListener(this);

        socialCircle.performClick();

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_topic_socialCircle:
                socialCircle.setTextColor(Color.parseColor("#000000"));
                follow.setTextColor(Color.parseColor("#7F7E80"));
                viewPager.setCurrentItem(0);
                break;
            case R.id.fragment_topic_follow:
                socialCircle.setTextColor(Color.parseColor("#7F7E80"));
                follow.setTextColor(Color.parseColor("#000000"));
                viewPager.setCurrentItem(1);
                break;
            case R.id.fragment_topic_add:
                ARouter.getInstance()
                        .build("/spaceTime/topic")
                        .withString("path", "addDynamic")
                        .navigation();
            default:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void drawView(){
        socialCircle.getLayoutParams().height = getPx(41);
        setTextSize(socialCircle, 20);
        LinearLayout.LayoutParams params = new LinearLayout.
                LayoutParams(socialCircle.getLayoutParams());
        params.setMargins(getPx(18), 0, getPx(19), getPx(2));
        socialCircle.setLayoutParams(params);

        follow.getLayoutParams().height = getPx(41);
        setTextSize(follow, 20);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(follow.
                getLayoutParams());
        params2.setMargins(0, 0, 0, getPx(2));
        follow.setLayoutParams(params2);

        binding.getRoot().findViewById(R.id.fragment_topic_add).getLayoutParams()
                .height = getPx(25);
        binding.getRoot().findViewById(R.id.fragment_topic_add).getLayoutParams()
                .width = getPx(25);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(binding.
                getRoot().findViewById(R.id.fragment_topic_add).getLayoutParams());
        params3.setMargins(0, getPx(7), getPx(20), getPx(11));
        binding.getRoot().findViewById(R.id.fragment_topic_add)
                .setLayoutParams(params3);
    }
}

package com.haixi.spacetime.DynamicModel.Fragments;

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
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Others.Adapter.FragmentAdapter;
import com.haixi.spacetime.Others.BasicFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentDynamicBinding;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Others.Settings.setH;
import static com.haixi.spacetime.Others.Settings.setHW;
import static com.haixi.spacetime.Others.Settings.setMargin;
import static com.haixi.spacetime.Others.Settings.setTextSize;

public class DynamicFragment extends BasicFragment implements View.OnClickListener{
    private FragmentDynamicBinding binding;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragments;
    private ViewPager viewPager;
    private Fragment collectionF;
    private Dynamic2Fragment recommendF;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dynamic,
                null, false);

        viewPager = binding.getRoot().findViewById(R.id.fragment_topic_viewPager);
        fragments = new ArrayList<Fragment>();
        recommendF = new Dynamic2Fragment(true);
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
                    binding.fragmentDynamicSocialCircle.performClick();
                }else if (position == 1){
                    binding.fragmentDynamicFollow.performClick();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        drawFragment();

        binding.fragmentDynamicSocialCircle.setOnClickListener(this);
        binding.fragmentDynamicFollow.setOnClickListener(this);
        binding.fragmentDynamicAdd.setOnClickListener(this);
        binding.fragmentDynamicFab1.setOnClickListener(this);
        binding.fragmentDynamicFab2.setOnClickListener(this);
        binding.fragmentDynamicFab3.setOnClickListener(this);
        binding.fragmentDynamicFab4.setOnClickListener(this);

        binding.fragmentDynamicSocialCircle.performClick();

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragmentDynamic_socialCircle:
                binding.fragmentDynamicSocialCircle.setTextColor(Color.parseColor("#000000"));
                binding.fragmentDynamicFollow.setTextColor(Color.parseColor("#7F7E80"));
                viewPager.setCurrentItem(0);
                break;
            case R.id.fragmentDynamic_follow:
                binding.fragmentDynamicSocialCircle.setTextColor(Color.parseColor("#7F7E80"));
                binding.fragmentDynamicFollow.setTextColor(Color.parseColor("#000000"));
                viewPager.setCurrentItem(1);
                break;
            case R.id.fragmentDynamic_add:
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

    private void drawFragment(){
        setH(binding.fragmentDynamicSocialCircle, 41);
        setTextSize(binding.fragmentDynamicSocialCircle, 20);
        setMargin(binding.fragmentDynamicSocialCircle, 18, 0, 19,
                2, false);

        setH(binding.fragmentDynamicFollow, 41);
        setTextSize(binding.fragmentDynamicFollow, 20);
        setMargin(binding.fragmentDynamicFollow, 0, 0, 0, 2, false);

        setHW(binding.getRoot().findViewById(R.id.fragmentDynamic_add), 25, 25);
        setMargin(binding.getRoot().findViewById(R.id.fragmentDynamic_add), 0,
                7,20, 11, false);
    }
}

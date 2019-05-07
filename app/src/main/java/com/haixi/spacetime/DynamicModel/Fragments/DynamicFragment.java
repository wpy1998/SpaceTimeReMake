package com.haixi.spacetime.DynamicModel.Fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.haixi.spacetime.Entity.BasicFragment;
import com.haixi.spacetime.DynamicModel.DynamicActivity;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentDynamicBinding;

import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.setTextSize;
import static com.haixi.spacetime.Entity.Cookies.resultCode;

public class DynamicFragment extends BasicFragment implements View.OnClickListener{
    private FragmentDynamicBinding binding;
    private FollowFragment followDynamic;
    private PageFragment allCircleDynamic;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dynamic,
                null, false);
        allCircleDynamic = new PageFragment();
        followDynamic = new FollowFragment();
        drawFragment();
        binding.fragmentDynamicSocialCircle.setOnClickListener(this);
        binding.fragmentDynamicFollow.setOnClickListener(this);
        binding.fragmentDynamicAdd.setOnClickListener(this);
        binding.fragmentDynamicSocialCircle.performClick();
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragmentDynamic_socialCircle:
                binding.fragmentDynamicSocialCircle.setTextColor(Color.parseColor("#000000"));
                binding.fragmentDynamicFollow.setTextColor(Color.parseColor("#7F7E80"));
                switchFragment(allCircleDynamic);
                break;
            case R.id.fragmentDynamic_follow:
                binding.fragmentDynamicSocialCircle.setTextColor(Color.parseColor("#7F7E80"));
                binding.fragmentDynamicFollow.setTextColor(Color.parseColor("#000000"));
                switchFragment(followDynamic);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void switchFragment(BasicFragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!allCircleDynamic.isAdded()){
            transaction.add(R.id.fragmentDynamic_frameLayout, allCircleDynamic);
        }
        if (!followDynamic.isAdded()){
            transaction.add(R.id.fragmentDynamic_frameLayout, followDynamic);
        }
        if (fragment == allCircleDynamic){
            transaction.hide(followDynamic).show(allCircleDynamic);
        }else {
            transaction.hide(allCircleDynamic).show(followDynamic);
        }
        transaction.commitAllowingStateLoss();
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

package com.haixi.spacetime.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.haixi.spacetime.DynamicModel.Fragments.SocialFragment;

import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<SocialFragment> fragments;
    public FragmentAdapter(FragmentManager fm, List<SocialFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

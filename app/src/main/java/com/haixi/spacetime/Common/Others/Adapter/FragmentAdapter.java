package com.haixi.spacetime.Common.Others.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.haixi.spacetime.Common.BasicFragment;

import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<BasicFragment> fragments;
    public FragmentAdapter(FragmentManager fm, List<BasicFragment> fragments) {
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

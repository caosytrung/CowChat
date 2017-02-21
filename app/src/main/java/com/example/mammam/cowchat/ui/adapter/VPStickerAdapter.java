package com.example.mammam.cowchat.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dee on 16/02/2017.
 */

public class VPStickerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    public VPStickerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);

    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

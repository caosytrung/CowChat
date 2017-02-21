package com.example.mammam.cowchat.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mam  Mam on 12/22/2016.
 */

public class ViewPagerInforAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;


    public ViewPagerInforAdapter(FragmentManager fm) {


        super(fm);
        fragments = new ArrayList<>();
    }
    public void addFragment(Fragment fragment){
        fragments.add(fragment);
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

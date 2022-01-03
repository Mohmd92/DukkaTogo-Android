package com.dukan.dukkan.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import java.util.ArrayList;

public class TabAdapter extends FragmentPagerAdapter {
    private ArrayList<Tabs> tabsArrayList;

    public TabAdapter(@NonNull FragmentManager fm, ArrayList<Tabs> tabsArrayList) {
        super(fm);
        this.tabsArrayList = tabsArrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return tabsArrayList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return tabsArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabsArrayList.get(position).getTitle();
    }
}

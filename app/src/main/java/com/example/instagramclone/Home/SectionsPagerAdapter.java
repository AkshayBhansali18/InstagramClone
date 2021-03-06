package com.example.instagramclone.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList=new ArrayList<>();
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
       return fragmentList.get(i);

    }

    @Override
    public int getCount() {
        return fragmentList.size() ;
    }
    public void addFragment(Fragment fragment)
    {
        fragmentList.add(fragment);
    }
}

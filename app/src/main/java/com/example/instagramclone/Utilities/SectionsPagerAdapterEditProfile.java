package com.example.instagramclone.Utilities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SectionsPagerAdapterEditProfile extends FragmentStatePagerAdapter {
    HashMap<Fragment,Integer> fragmentNumber=new HashMap<>();
    HashMap<String,Integer> fragmentNumFromString=new HashMap<>();
    HashMap<Integer,String>fragmentName=new HashMap<>();
    List<Fragment> fragmentList=new ArrayList<>();
    public SectionsPagerAdapterEditProfile(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    //add fragments to list which contains al fragments
    //add fragments to hashmaps
    public void addFragment(Fragment fragment,String fragmentname)
    {
        fragmentList.add(fragment);
        fragmentNumber.put(fragment,fragmentList.size()-1);
        fragmentNumFromString.put(fragmentname,fragmentList.size()-1);
        fragmentName.put(fragmentList.size()-1,fragmentname);
    }
    //get fragment number from fragment
    public int getFragmentNumber(Fragment fragment)
    {
        if(fragmentNumber.containsKey(fragment))
            return fragmentNumber.get(fragment);
        else
            return -1;
    }
    //get fragment number from fragment name
    public int getFragmentNumber(String fragmentname)
    {
        if(fragmentName.containsKey(fragmentname))
            return fragmentNumber.get(fragmentname);
        else
            return -1;
    }
    //get fragment name from fragment number
    public String getFragmentName(int fragmentnumber)
    {
        if(fragmentName.containsKey(fragmentnumber))
            return fragmentName.get(fragmentnumber);
        else
            return null;
    }
}


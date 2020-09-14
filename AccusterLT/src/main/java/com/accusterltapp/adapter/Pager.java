package com.accusterltapp.adapter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.accusterltapp.Tab1;
import com.accusterltapp.Tab2;
import com.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class Pager extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    int TabCount;
    public Pager(FragmentManager fm, int TabCount) {
        super(fm);
        this.TabCount = TabCount;
    }

    @Override
    public Fragment getItem(int position) {
//        switch(position){
//            case 0:
//                Tab1 tab1 = new Tab1();
//                return tab1;
//            case 1:
//                Tab2 tab2 = new Tab2();
//                return tab2;
//                default:
//                    tab1 = new Tab1();
//                    return tab1;
//        }
        return mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}

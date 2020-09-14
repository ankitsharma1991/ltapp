package com.accusterltapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.accusterltapp.fragment.QcDataFragment;
import com.accusterltapp.fragment.RejectedQcFragment;

public class QcPageAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    public QcPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public  Fragment getItem(int i) {
        switch (i) {
            case 0:
                QcDataFragment tab=new QcDataFragment();
                return tab;
            case 1:
                RejectedQcFragment tab2 = new RejectedQcFragment();
                return tab2;
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}



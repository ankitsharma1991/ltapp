package com.accusterltapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accusterltapp.R;
import com.accusterltapp.adapter.QcPageAdapter;

public class QcFragmentmain extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.qc_main_fragment,container,false);
        viewPager= view.findViewById(R.id.pager);
        tabLayout= view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("My Qc"));
        tabLayout.addTab(tabLayout.newTab().setText("Rejected Qc"));
        QcPageAdapter qcPageAdapter=new QcPageAdapter(getChildFragmentManager(),tabLayout.getChildCount());
        return view;
    }
}

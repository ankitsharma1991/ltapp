package com.accusterltapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.fragment.BaseFragment;
import com.accusterltapp.R;

public class ApprovedReportListFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentaddpatient, container, false);
        //getActivity().setTitle("AppointMent List");
        //sachin


        return view;
    }
}

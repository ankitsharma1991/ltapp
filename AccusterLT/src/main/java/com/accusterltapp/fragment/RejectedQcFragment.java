package com.accusterltapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accusterltapp.R;
import com.accusterltapp.model.QcData;

import java.util.ArrayList;

public class RejectedQcFragment extends Fragment {
    RecyclerView rv_qc_list;
    ArrayList<QcData> list;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.qcdatafragment, container, false);

        return v;
    }
}

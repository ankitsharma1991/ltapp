package com.accusterltapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accusterltapp.activity.UnsynReportView;
import com.accusterltapp.adapter.PatientTableViewAdapter;
import com.accusterltapp.model.RegisterPatient;
import com.accusterltapp.table.TablePatient;
import com.base.fragment.BaseFragment;
import com.base.listener.RecyclerViewListener;
import com.base.utility.ToastUtils;
import com.accusterltapp.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sqyuser on 4/1/18.
 */

public class UnSynPatientList extends BaseFragment implements RecyclerViewListener {

    private ArrayList<RegisterPatient> patientList;
    private TablePatient mPatient;
    private PatientTableViewAdapter patientTableDataAdapter;
    private RecyclerView patientTableView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_list, container, false);
        patientTableView = view.findViewById(R.id.rv_patient_view);
        patientTableView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //get realm instance
        patientList = new ArrayList<>();
        mPatient = new TablePatient(getActivity());
        if (!mPatient.isTableEmpty()) {
            patientList = mPatient.getallPatientAndReport(patientList);
        } else {
            ToastUtils.showShortToastMessage(getActivity(), "No Patient Found");
        }

        patientTableDataAdapter = new PatientTableViewAdapter(getActivity(), patientList, this);
        patientTableView.setAdapter(patientTableDataAdapter);

        return view;
    }


    @Override
    public void onItemClickListener(View view, int position, HashMap<String, String> hashMap) {
        if (hashMap.get("action").equalsIgnoreCase("show")) {
            Intent intent = new Intent(getActivity(), UnsynReportView.class);
            Bundle args = new Bundle();
            args.putString("camp_name", patientList.get(position).getCampName());
            args.putString("patient_name", patientList.get(position).getUserregistration_complete_name());
            args.putString("patient_id", patientList.get(position).getUserregistration_code());
            args.putString("label_id", patientList.get(position).getpLabelId());
            args.putString("process_date", patientList.get(position).getUserregistration_created_time());
            args.putString("time", patientList.get(position).getUserregistration_created_time());
            args.putParcelable("patientDetail", patientList.get(position));
            intent.putExtra("userData", args);
            startActivity(intent);
        }
    }
}

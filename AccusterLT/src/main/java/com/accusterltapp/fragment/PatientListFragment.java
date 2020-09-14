package com.accusterltapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Created by pbadmin on 12/12/17.
 * show patient list
 */

public class PatientListFragment extends BaseFragment implements RecyclerViewListener {
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
            patientList = mPatient.getallPatientByCamp(patientList,getArguments().getString("campCode"));
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
            PatientPreview patient_preview = new PatientPreview();
            Bundle args = new Bundle();
            args.putString("camp_name", patientList.get(position).getCampName());
            args.putString("patient_id", patientList.get(position).getUserregistration_code());
            args.putString("label_id", patientList.get(position).getpLabelId());
            args.putString("process_date", patientList.get(position).getUserregistration_created_time());
            args.putString("time", patientList.get(position).getUserregistration_created_time());
            args.putParcelable("patientDetail", patientList.get(position));
            patient_preview.setArguments(args);
            //Inflate the fragment
            setFragment(patient_preview, PatientPreview.class.getSimpleName());
        }
    }
}


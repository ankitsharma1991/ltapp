package com.accusterltapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accusterltapp.adapter.CampsAdapter;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.table.TableCamp;
import com.base.fragment.BaseFragment;
import com.base.listener.RecyclerViewListener;
import com.base.model.CampDetails;
import com.accusterltapp.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sqyuser on 4/1/18.
 */

public class UnSynCampList extends BaseFragment implements RecyclerViewListener {

    private RecyclerView recyclerView;
    private CampsAdapter adapter;
    private TableCamp tableCamp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_list_patient, container, false);
        tableCamp = new TableCamp(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("userId", AppPreference.getString(getActivity(), AppPreference.USER_ID));

        ArrayList<CampDetails> campList = new ArrayList<>();
        setupRecycler(tableCamp.getCampListToSyn(campList));

        return view;
    }

    private void setupRecycler(ArrayList<CampDetails> campList) {
        // create an empty adapter and ButtonAddPatient it to the recycler view
        if (getActivity() != null && isAdded()) {
            adapter = new CampsAdapter(getActivity(), this, campList);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClickListener(View view, int position, HashMap<String, String> hashMap) {

    }
}

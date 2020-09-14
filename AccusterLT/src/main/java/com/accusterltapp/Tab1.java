package com.accusterltapp;

import android.net.http.RequestQueue;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.accusterltapp.adapter.QcdataAdapter;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.model.QCDetail;
import com.accusterltapp.model.QCResponse;
import com.accusterltapp.model.QcData;
import com.accusterltapp.model.QcDataList;
import com.accusterltapp.service.ApiClinet;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.ImgService;
import com.accusterltapp.service.NetworkCallContext;
import com.accusterltapp.table.TableCamp;
import com.accusterltapp.table.TablePatient;
import com.accusterltapp.table.TableQcData;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.fragment.BaseFragment;
import com.base.listener.RecyclerViewListener;
import com.base.model.CampDetails;
import com.base.model.CampList;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import custom.CustomLablledSpinner;
import custom.CustomSpinnerAdapter;
import custom.CustomSpinnerItemListener;
import retrofit2.Call;
import retrofit2.Callback;

public class Tab1 extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, NetworkCallContext, RecyclerViewListener, SearchView.OnQueryTextListener, CustomSpinnerItemListener {
    ArrayAdapter<String> adapter;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<QcData> list;
    ArrayList<QcData >filter_list;
    ArrayList<QCDetail> qcDetailArrayList,filterlist;
    CustomLablledSpinner camp_selector;
    RecyclerView qcData_rv;
    QcDataList qcDataList;
    TableQcData qcdata;
    QcdataAdapter qcdataAdapter;
    ArrayList<CampList> camps_list;
    ArrayList<CampDetails> campDetails;
    TableCamp tableCamp;
    String campCode = "", campName = "";
    SwipeRefreshLayout swipeRefreshLayout;
    boolean userintrection =false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qcdata_tab, container, false);
        tableCamp = new TableCamp(getActivity());
        camp_selector = view.findViewById(R.id.camp_selector);
        camp_selector.hideHint(true);
        camp_selector.setSelection(0);
        camp_selector.setClickListener(this);

        camp_selector.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                userintrection=true;
                return false;
            }
        });
swipeRefreshLayout=view.findViewById(R.id.l_swipe);
swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
        list=new ArrayList<>();
        qcdata=new TableQcData(getActivity());
        qcdata.getQcdataList(list);
        qcDataList=new QcDataList();
        qcDataList.setList(list);
        qcDataList.setLtid(AppPreference.getString(getContext(), AppPreference.USER_ID));
        qcData_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        qcdataAdapter=new QcdataAdapter(getContext(),list,campDetails);
        qcData_rv.setAdapter(qcdataAdapter);

        swipeRefreshLayout.setRefreshing(false);
    }
});
        qcData_rv = view.findViewById(R.id.qcData_rv);
        campDetails = new ArrayList<>();
        camps_list = new ArrayList<CampList>();
        setupRecycler(tableCamp.getCampList(campDetails));
        list=new ArrayList<>();
        qcdata=new TableQcData(this.getActivity());
        qcdata.getQcdataList(list);
        qcDataList=new QcDataList();
        qcDataList.setList(list);
        qcDataList.setLtid(AppPreference.getString(this.getContext(), AppPreference.USER_ID));
        Gson g=new Gson();
        com.android.volley.RequestQueue queue= Volley.newRequestQueue(this.getContext());
        try {

            JSONObject ob = new JSONObject(g.toJson(qcDataList));
            JsonObjectRequest request = new JsonObjectRequest(ApiConstant.BASE_URL1+ApiConstant.ADD_QC_DATA, ob,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                           /// Log.e("happen qcdata", response.toString());

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String body;
                    Log.e("error", error.toString());
                }
            });
            queue.add(request);
        }
        catch (Exception e)
        {

        }
        Log.e("gson data",g.toJson(qcDataList));
        qcData_rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        qcdataAdapter=new QcdataAdapter(this.getContext(),list,campDetails);
        qcData_rv.setAdapter(qcdataAdapter);

//        Log.e("lt_id",lt_id);
        if(list==null||list.isEmpty()){

        }
            else{
                //retrofitJSONParsing();
            qcData_rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            qcdataAdapter = new QcdataAdapter(this.getContext(), list, campDetails);
            qcData_rv.setAdapter(qcdataAdapter);
            qcdataAdapter.notifyDataSetChanged();
            }

        return view;

    }

    private void setupRecycler(ArrayList<CampDetails> campList) {
        CustomSpinnerAdapter campListAdapter = new CustomSpinnerAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item, campList);
        campListAdapter.setShowDefaultText(false);
        camp_selector.setAdapter(campListAdapter);
      //  camp_selector.setSelection(AppPreference.getInt(getActivity(), AppPreference.CAMP_POSITION));

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void handleResponse(Object response, String type) {
        if (type.equalsIgnoreCase(ApiConstant.CAMP_LIST)) {
            CampList campList = (CampList) response;
            if (campList != null && campList.getStatus().equalsIgnoreCase(ApiConstant.SUCCESS)
                    && campList.getCamplist() != null && !campList.getCamplist().isEmpty()) {
                tableCamp.insertCampListFormServer(campList.getCamplist());
            }
            setupRecycler(tableCamp.getCampList(campDetails));
        }
    }

    @Override
    public void handleServerError(Object response, String type) {
        if (type.equalsIgnoreCase(ApiConstant.CAMP_LIST)) {
            setupRecycler(tableCamp.getCampList(campDetails));
        }
    }

    @Override
    public void onItemClickListener(View view, int position, HashMap<String, String> hashMap) {

    }

    @Override
    public void onItemSelection(Spinner spinnerView, int position) {
    if (userintrection)
    {
        filter_list=new ArrayList<>();

                if (position > 0) {
                    Log.e("Selected Position", position + " ");
                    campCode = campDetails.get(position).getCamp_code();
                    campName = campDetails.get(position).getCampName();

                    for (int i = 0; i < list.size(); i++) {
                        Log.e(" name"+campName,list.get(i).getCamp_name());
                        //AppPreference.setInt(getActivity(), AppPreference.CAMP_POSITION, position);
                        if (campName.equals(list.get(i).getCamp_name())) {

                            filter_list.add(new QcData(list.get(i).getTest_id(),list.get(i).getC1(),list.get(i).getC2(),list.get(i).getLab_id(),list.get(i).getCamp_name(),list.get(i).getQc_status(),list.get(i).getDate(),list.get(i).getTime(),list.get(i).getStatus(),list.get(i).getC3(),list.get(i).getL1(),list.get(i).getL2()));

                        }
                }
                    qcData_rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
                    qcdataAdapter = new QcdataAdapter(this.getContext(), filter_list, campDetails);
                    qcData_rv.setAdapter(qcdataAdapter);
                    qcdataAdapter.notifyDataSetChanged();
                    Log.e("data", String.valueOf(list.size()) + list + "");
            }
    }
            }




}

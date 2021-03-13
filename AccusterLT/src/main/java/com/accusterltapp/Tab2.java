package com.accusterltapp;

import android.content.Context;
import android.os.Bundle;
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

import com.accusterltapp.adapter.QCStatusAdapter;
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
import com.accusterltapp.table.TableQcData;
import com.base.fragment.BaseFragment;
import com.base.listener.RecyclerViewListener;
import com.base.model.CampDetails;
import com.base.model.CampList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import custom.CustomLablledSpinner;
import custom.CustomSpinnerAdapter;
import custom.CustomSpinnerItemListener;
import retrofit2.Call;
import retrofit2.Callback;

public class Tab2 extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, NetworkCallContext, RecyclerViewListener, SearchView.OnQueryTextListener, CustomSpinnerItemListener {
    Spinner campList_spinner;
    ArrayList<QCDetail> filteredList, filteredList1;
    Spinner status_spinner;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<QcData> list;
    RecyclerView qcData_rv;
    TableCamp tableCamp;
    ArrayList<CampList> camps_list;
    ArrayList<CampDetails> campDetails;
    String campCode = "", campName = "";
    QcDataList qcDataList;
    TableQcData qcdata;
    String[] qc_data_Status = {"All", "Approve", "Pending", "Reject"};
    ArrayAdapter statusAdapter;
    String lt_id;
    QCStatusAdapter qcdataAdapter;
    ArrayList<QCDetail> qclist;
    ArrayList<QCDetail> qcDetails;
    CustomSpinnerAdapter campListAdapter;
    ArrayList<QCDetail> qcDetailList;
    boolean spinner_camp = false, spinner_status = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qcstatus_tab, container, false);
        campList_spinner = view.findViewById(R.id.campList_spinner);
        swipeRefreshLayout = view.findViewById(R.id.i_swipe);

        campList_spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Real touch felt.");
                spinner_camp = true;
                return false;
            }
        });
        status_spinner = view.findViewById(R.id.status_spinner);
        status_spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spinner_status = true;
                return false;
            }
        });
        qcData_rv = view.findViewById(R.id.list_rv);
        statusAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, qc_data_Status);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status_spinner.setAdapter(statusAdapter);

        qclist = new ArrayList<>();
        qcDetails = new ArrayList<>();
        list = new ArrayList<>();
        qcdata = new TableQcData(this.getActivity());
        qcdata.getQcdataList(list);
        qcDataList = new QcDataList();
        qcDataList.setList(list);
        qcDataList.setLtid(AppPreference.getString(this.getContext(), AppPreference.USER_ID));
        lt_id = qcDataList.getLtid();
        retrofitJSONParsing();
        qcData_rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        qcdataAdapter = new QCStatusAdapter(getContext(), qclist, campDetails);
        qcData_rv.setAdapter(qcdataAdapter);
        qcdataAdapter.notifyDataSetChanged();
        campList_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("log ", "item select");
                try {
                    status_spinner.setSelection(0);
                    if (spinner_camp) {

                       if (i == 0)
                            filteredList = qcDetailList;
                       else if (i > 0) {
                            filteredList = new ArrayList<>();
                            campCode = campDetails.get(i).getCamp_code();
                            campName = campDetails.get(i).getCampName();
                            for (int k = 0; k < qcDetailList.size(); k++) {
                                if (qcDetailList.get(k).getCampName().equals(campName)) {

                                    filteredList.add(new QCDetail(
                                            qcDetailList.get(k).getTestId(),
                                            qcDetailList.get(k).getTestName(),
                                            qcDetailList.get(k).getQcId(),
                                            qcDetailList.get(k).getQcL1(),
                                            qcDetailList.get(k).getQcL2(),
                                            qcDetailList.get(k).getQcC1(),
                                            qcDetailList.get(k).getQcC2(),
                                            qcDetailList.get(k).getQcC3(),
                                            qcDetailList.get(k).getQcLabId(),
                                            qcDetailList.get(k).getQcType(),
                                            qcDetailList.get(k).getQcDateUpdate(),
                                            qcDetailList.get(k).getQcLtId(),
                                            qcDetailList.get(k).getQc_status(),
                                            qcDetailList.get(k).getCampName()));
                                }
                            }

                        }

                        //Log.e("data ", "da" + filteredList.size());
                        qcData_rv.setLayoutManager(new LinearLayoutManager(getContext()));
                        qcdataAdapter = new QCStatusAdapter(getContext(), filteredList, campDetails);
                        qcData_rv.setAdapter(qcdataAdapter);
                        qcdataAdapter.notifyDataSetChanged();


                    }
                } catch (Exception e) {
                e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner_status) {
                    ArrayList<QCDetail> qclist1 = null;
                    int no_to_check = 0;
                    filteredList1 = new ArrayList<>();

                    if (filteredList != null)
                        qclist1 = filteredList;
                    else
                        qclist1 = qcDetailList;
                    Log.e("select item ", " item " + i);


                    if (i == 0)
                        filteredList1 = qclist1;
                    if (i > 0) {

                        if (i == 1)
                            no_to_check = 0;
                        else if (i == 2)
                            no_to_check = 1;
                        else if (i == 3)
                            no_to_check = 2;

                        for (int k = 0; k < qclist1.size(); k++) {
                            Log.e("data ", qclist1.get(k).getQc_status() + " and " + no_to_check);
                            if (qclist1.get(k).getQc_status() == no_to_check) {

                                filteredList1.add(new QCDetail(
                                        qclist1.get(k).getTestId(),
                                        qclist1.get(k).getTestName(),
                                        qclist1.get(k).getQcId(),
                                        qclist1.get(k).getQcL1(),
                                        qclist1.get(k).getQcL2(),
                                        qclist1.get(k).getQcC1(),
                                        qclist1.get(k).getQcC2(),
                                        qclist1.get(k).getQcC3(),
                                        qclist1.get(k).getQcLabId(),
                                        qclist1.get(k).getQcType(),
                                        qclist1.get(k).getQcDateUpdate(),
                                        qclist1.get(k).getQcLtId(),
                                        qclist1.get(k).getQc_status(),
                                        qclist1.get(k).getCampName()));
                            }
                        }
//


                    }
                    Log.e("data ", "dat 23" + filteredList1.size());
                    qcData_rv.setLayoutManager(new LinearLayoutManager(getContext()));
                    qcdataAdapter = new QCStatusAdapter(getContext(), filteredList1, campDetails);
                    qcData_rv.setAdapter(qcdataAdapter);


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tableCamp = new TableCamp(getActivity());


        campDetails = new ArrayList<>();
        camps_list = new ArrayList<CampList>();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //tatusAdapter.clear();;
               // ArrayList<CampDetails> campList = tableCamp.getCampList(campDetails);
                status_spinner.setSelection(0);
                if (campListAdapter != null)
                    campList_spinner.setSelection(0);
                retrofitJSONParsing();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        setupRecycler(tableCamp.getCampListWithSelectCampTitle(campDetails));
        /// list = new ArrayList<>();
//        qcdata = new TableQcData(this.getActivity());
//        qcdata.getQcdataList(list);
//        qcDataList = new QcDataList();
//        qcDataList.setList(list);
        return view;
    }

    private void setupRecycler(ArrayList<CampDetails> campList) {

        campListAdapter = new CustomSpinnerAdapter(getContext(),
                    android.R.layout.simple_spinner_dropdown_item, campList);
        campListAdapter.setShowDefaultText(false);
        campList_spinner.setAdapter(campListAdapter);
        campList_spinner.setSelection(AppPreference.getInt(getActivity(), AppPreference.CAMP_POSITION));
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

            //setupRecycler(tableCamp.getCampList(campDetails));
        }

    }

    @Override
    public void handleServerError(Object response, String type) {
        if (type.equalsIgnoreCase(ApiConstant.CAMP_LIST)) {
            //setupRecycler(tableCamp.getCampList(campDetails));
        }
    }

    @Override
    public void onItemClickListener(View view, int position, HashMap<String, String> hashMap) {

    }

    @Override
    public void onItemSelection(Spinner spinnerView, int position) {
//        Log.e("log ","item select");
//        if (spinner_camp) {
//                if (spinnerView == campList_spinner.spinner) {
//                    if (position==0)
//                    filteredList = qcDetailList;
//                    if (position >0) {
//                        filteredList = new ArrayList<>();
//                        campCode = campDetails.get(position).getCamp_code();
//                        campName = campDetails.get(position).getCampName();
//                        for (int k = 0; k <qcDetailList.size(); k++) {
//                            if (qcDetailList.get(k).getCampName().equals(campName)) {
//
//                                filteredList.add(new QCDetail(
//                                        qcDetailList.get(k).getTestId(),
//                                        qcDetailList.get(k).getTestName(),
//                                        qcDetailList.get(k).getQcId(),
//                                        qcDetailList.get(k).getQcL1(),
//                                        qcDetailList.get(k).getQcL2(),
//                                        qcDetailList.get(k).getQcC1(),
//                                        qcDetailList.get(k).getQcC2(),
//                                        qcDetailList.get(k).getQcC3(),
//                                        qcDetailList.get(k).getQcLabId(),
//                                        qcDetailList.get(k).getQcType(),
//                                        qcDetailList.get(k).getQcDateUpdate(),
//                                        qcDetailList.get(k).getQcLtId(),
//                                        qcDetailList.get(k).getQc_status(),
//                                        qcDetailList.get(k).getCampName()));
//                            }
//                        }
//                        Log.e("data ","da"+filteredList.size());
//                                    qcData_rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//                                    qcdataAdapter = new QCStatusAdapter(this.getContext(), filteredList, campDetails);
//                                    qcData_rv.setAdapter(qcdataAdapter);
//
//
//
//
//
//
//                    }
//                }
//        }
    }

    public void retrofitJSONParsing() {
        //list = new ArrayList<>();
        Log.e("data ", lt_id + "");
        ImgService service = ApiClinet.getClinete().create(ImgService.class);
        Call<QCResponse> call = service.getQcByLT(lt_id);
        Log.e("lt_id", lt_id);
        call.enqueue(new Callback<QCResponse>() {
            @Override
            public void onResponse(Call<QCResponse> call, retrofit2.Response<QCResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        qcDetailList = new ArrayList<>();
                        qcDetailList.clear();

                        QCResponse qcResponse = response.body();
                        Log.e("object", qcResponse + " st");
                        Log.e("the list is", qcResponse.getQcData().size() + " size");
                        //list.addAll(response.body().getQcData());
                        qcDetailList.addAll(response.body().getQcData());
                        Log.e("qc list", response.body().getQcData() + " st");
                        Log.e("response ", new Gson().toJson(response.body()));
                        qcData_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        qcdataAdapter = new QCStatusAdapter(getContext(), qcDetailList, campDetails);
                        qcData_rv.setAdapter(qcdataAdapter);
                        qcdataAdapter.notifyDataSetChanged();
                        tableCamp.getCampListWithSelectCampTitle(campDetails).clear();
                        setupRecycler(tableCamp.getCampListWithSelectCampTitle(campDetails));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<QCResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
}


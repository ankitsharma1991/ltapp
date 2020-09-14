package com.accusterltapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accusterltapp.adapter.CampsAdapter;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.model.CampDetails1;
import com.accusterltapp.model.CampList1;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.NetworkCallContext;
import com.accusterltapp.service.NetworkUtil;
import com.accusterltapp.table.TableCamp;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.base.fragment.BaseFragment;
import com.base.listener.RecyclerViewListener;
import com.base.model.CampDetails;
import com.base.model.CampList;
import com.google.gson.Gson;
import com.accusterltapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/*
 * Created by appideas-user2 on 18/7/17.
 *
 */

public class CampsListFragment extends BaseFragment implements RecyclerViewListener, NetworkCallContext {
    private RecyclerView recyclerView;
    private CampsAdapter adapter;
    private TableCamp tableCamp;
    String ltid;
    ArrayList<CampDetails1> camp_to_inser=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_list_patient, container, false);
        tableCamp = new TableCamp(CampsListFragment.this.getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ltid= AppPreference.getString(getActivity(), AppPreference.USER_ID);
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("ltId", AppPreference.getString(getActivity(), AppPreference.USER_ID));
        if (NetworkUtil.checkInternetConnection(getActivity())) {
            com.android.volley.RequestQueue queue = Volley.newRequestQueue(this.getContext());
            StringRequest sr = new StringRequest(Request.Method.POST,ApiConstant.BASE_URL1+"getListApiCamp", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // mPostCommentResponse.requestCompleted();
                    //  System.out.print(response);
                    Log.e("response",response);
                    Gson gn=new Gson();
                    CampList1 campList=gn.fromJson(response,CampList1.class);

                    if (campList != null && campList.getStatus().equalsIgnoreCase(ApiConstant.SUCCESS)
                            && campList.getCampList() != null && !campList.getCampList().isEmpty()) {
                        ArrayList<CampDetails> campListModel = new ArrayList<>();
                        tableCamp.getCampList(campListModel);
                        ArrayList<CampDetails1> list1=campList.getCampList();
                        boolean falg=false;

                        for(int i=0;i<list1.size();i++)
                        {
                            falg=false;
                            for(int j=0;j<campListModel.size();j++)
                            {
                                if(list1.get(i).getCamp_code().equals(campListModel.get(j).getCamp_code()))
                                {
                                    falg=true;
                                    break;

                                }

                            }
                            if (!falg)
                            camp_to_inser.add(list1.get(i));
                        }
                       //tableCamp.isTableEmpty();
                      //  setupRecycler();
                        tableCamp.insertCampListFormServer1(camp_to_inser);
                    }
                    ArrayList<CampDetails> campListModel = new ArrayList<>();
                    tableCamp.isTableEmpty();
                    setupRecycler(tableCamp.getCampList(campListModel));
                    Log.e("size of list is ",campListModel.size()+"");


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error ",error.toString());
                    //toString////////mPostCommentResponse.requestEndedWithError(error);
                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    //    String ap=AppPreference.USER_ID;
                    params.put("ltId",ltid);

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return Collections.emptyMap();
                }
            };
            queue.add(sr);
        } else {
            Log.e("not internet","");
            ArrayList<CampDetails> campList = new ArrayList<>();
           setupRecycler(tableCamp.getCampList(campList));
        }
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
if(!Heleprec.avroverreport) {
    PatientListFragment patient_detail = new PatientListFragment();
    Bundle bundle = new Bundle();
    bundle.putString("campCode", hashMap.get("campCode"));
    patient_detail.setArguments(bundle);
    setFragment(patient_detail, PatientListFragment.class.getSimpleName());
}
else {

}
    }

    @Override
    public void handleResponse(Object response, String type) {
       // Log.e("response",respon+"");
        CampList campList = (CampList) response;


        if (campList != null && campList.getStatus().equalsIgnoreCase(ApiConstant.SUCCESS)
                && campList.getCamplist() != null && !campList.getCamplist().isEmpty()) {

            tableCamp.insertCampListFormServer(campList.getCamplist());
        }
        ArrayList<CampDetails> campListModel = new ArrayList<>();
        setupRecycler(tableCamp.getCampList(campListModel));
    }

    @Override
    public void handleServerError(Object response, String type) {
        Log.e("error",response.toString());
        ArrayList<CampDetails> campListModel = new ArrayList<>();
        setupRecycler(tableCamp.getCampList(campListModel));
    }
}




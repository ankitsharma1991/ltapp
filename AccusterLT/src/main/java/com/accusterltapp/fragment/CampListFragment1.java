package com.accusterltapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accusterltapp.adapter.CampListAdapter1;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.model.CampDetails1;
import com.accusterltapp.model.CampList1;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.NetworkCallContext;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CampListFragment1 extends BaseFragment implements RecyclerViewListener, NetworkCallContext {
    private RecyclerView recyclerView;
    private CampListAdapter1 adapter;
    ArrayList<CampDetails1> campList1;
    private TableCamp tableCamp;
    ProgressDialog progress;
    String ltid=null;
    android.widget.ProgressBar pb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_list_patient, container, false);
        tableCamp = new TableCamp(getActivity());
        pb=view.findViewById(R.id.pb);
        progress = new ProgressDialog(getContext());
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

     //   progress.show();
       // pb.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ltid= AppPreference.getString(getActivity(), AppPreference.USER_ID);
       // Log.d("response we",tr);
        // com.android.volley.RequestQueue rq= Volley.newRequestQueue(this.getContext());
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(this.getContext());
        StringRequest sr = new StringRequest(Request.Method.POST,ApiConstant.BASE_URL1+"getListApiCamp", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
               // Log.e("close ","clos the progress");
                Log.d("response",response);
                try {
                    JSONObject object =new JSONObject(response);
                    Gson gn=new Gson();
                    CampList1 clist=gn.fromJson(response,CampList1.class);
                    Log.d("arr",clist.toString());
                    setupRecycler(clist.getCampList());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error ",error.toString());
                // mPostCommentResponse.requestEndedWithError(error);
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
        if(Heleprec.list!=null)
        {
            setupRecycler(Heleprec.list);
        }
        else
        queue.add(sr);
//
        return view;
    }
    private void setupRecycler(ArrayList<CampDetails1> campList) {
        // create an empty adapter and ButtonAddPatient it to the recycler view
        if (getActivity() != null && isAdded()) {
            campList1=campList;
            adapter = new CampListAdapter1(getActivity(), this, campList);
            recyclerView.setAdapter(adapter);
        }
    }
    @Override
    public void onItemClickListener(View view, int position, HashMap<String, String> hashMap) {
if (Heleprec. avroverreport)
{
 //PdfViwerFragment patient_detail = new PdfViwerFragment();
//    Bundle bundle = new Bundle();
//    bundle.putString("campCode", campList1.get(position).getCamp_code());
//    Heleprec.current_camp_name=hashMap.get("campname");
//    patient_detail.setArguments(bundle);
//    Intent intent = new Intent(Intent.ACTION_VIEW);
//
//    intent.setDataAndType(Uri.parse( "http://docs.google.com/viewer?url=http://eaccuster.com/partner/maf/get_pdf.php?id=R77ZWHRFYJ6024" ), "text/html");
//
//    startActivity(intent);
    ApprovedReportFragment patient_detail = new ApprovedReportFragment();
    Bundle bundle = new Bundle();
    bundle.putString("campCode", campList1.get(position).getCamp_code());
    Heleprec.current_camp_name=hashMap.get("campname");
    patient_detail.setArguments(bundle);
    setFragment(patient_detail, ApprovedReportFragment.class.getSimpleName());
    //setFragment(patient_detail, PdfViwerFragment.class.getSimpleName());
}
else {
    PatientFragment patient_detail = new PatientFragment();
    Bundle bundle = new Bundle();
    bundle.putString("campCode", campList1.get(position).getId());
    patient_detail.setArguments(bundle);
    setFragment(patient_detail, PatientFragment.class.getSimpleName());
}
    }
    @Override
    public void handleResponse(Object response, String type) {
        CampList campList = (CampList) response;

        if (campList != null && campList.getStatus().equalsIgnoreCase(ApiConstant.SUCCESS)
                && campList.getCamplist() != null && !campList.getCamplist().isEmpty()) {

            tableCamp.insertCampListFormServer(campList.getCamplist());
        }
        ArrayList<CampDetails> campListModel = new ArrayList<>();
        //setupRecycler(tableCamp.getCampList(campListModel));
    }
    @Override
    public void handleServerError(Object response, String type) {
        ArrayList<CampDetails> campListModel = new ArrayList<>();
       // setupRecycler(tableCamp.getCampList(campListModel));
    }
}

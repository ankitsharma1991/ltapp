package com.accusterltapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accusterltapp.Tab1;
import com.accusterltapp.Tab2;
import com.accusterltapp.adapter.Pager;
import com.accusterltapp.adapter.QcdataAdapter;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.model.QcData;
import com.accusterltapp.model.QcDataList;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.NetworkCallContext;
import com.accusterltapp.table.TableQcData;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.fragment.BaseFragment;
import com.google.gson.Gson;
import com.accusterltapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class QcDataFragment extends BaseFragment implements NetworkCallContext, TabLayout.OnTabSelectedListener{
ArrayList<QcData> list;
 TabLayout tabLayout;
 ViewPager viewPager;
//RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.qcdatafragment,container,false);
        tabLayout = v.findViewById(R.id.tabLayout);
        viewPager = v.findViewById(R.id.pager);
//        tabLayout.addTab(tabLayout.newTab().setText("Data"));
//        tabLayout.addTab(tabLayout.newTab().setText("Status"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        Pager adapter = new Pager(getChildFragmentManager(), tabLayout.getTabCount());
        adapter.addFragment(new Tab1(),"Data");
        adapter.addFragment(new Tab2(),"Status");
        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(this);
//        recyclerView=v.findViewById(R.id.qclist);
//        list=new ArrayList<>();
//        final TableQcData qcdata=new TableQcData(this.getActivity());
//        qcdata.getQcdataList(list);
//        QcDataList qcDataList=new QcDataList();
//        qcDataList.setList(list);
//        qcDataList.setLtid(AppPreference.getString(this.getContext(), AppPreference.USER_ID));
//        Gson g=new Gson();
//        RequestQueue queue= Volley.newRequestQueue(this.getContext());
//        try {
//
//            JSONObject ob = new JSONObject(g.toJson(qcDataList));
//            JsonObjectRequest request = new JsonObjectRequest(ApiConstant.BASE_URL1+ApiConstant.ADD_QC_DATA, ob,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                           /// Log.e("happen qcdata", response.toString());
//
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    String body;
//                    Log.e("error", error.toString());
//                }
//            });
//            queue.add(request);
//        }
//        catch (Exception e)
//        {
//
//        }
//        Log.e("gson data",g.toJson(qcDataList));
////        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//        QcdataAdapter adapter=new QcdataAdapter(this.getContext(),list);
////        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void handleResponse(Object response, String type) {

    }

    @Override
    public void handleServerError(Object response, String type) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}

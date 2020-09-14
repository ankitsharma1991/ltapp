package com.accusterltapp.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.accusterltapp.adapter.PackageListAdapter;
import com.accusterltapp.app.LabTechnicianApplication;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.model.TestDetails;
import com.accusterltapp.model.TestListResponse;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.BaseNetworkRequest;
import com.accusterltapp.service.NetworkCallContext;
import com.accusterltapp.service.NetworkUtil;
import com.accusterltapp.table.TableGenericPacakge;
import com.accusterltapp.table.TablePackageList;
import com.base.fragment.BaseFragment;
import com.base.utility.ToastUtils;
import com.accusterltapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
public class PackageListFragment extends BaseFragment implements ExpandableListView.OnGroupExpandListener, NetworkCallContext, View.OnClickListener {
    private ExpandableListView expandableListView;
    private PackageListAdapter expandableListAdapter;
    private FloatingActionButton addpackage;
    private TestListResponse testListResponse;
    private ArrayList<TestDetails> mArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_package__list, container, false);
        addpackage = (FloatingActionButton) view.findViewById(R.id.addpackage);
        expandableListView = view.findViewById(R.id.expandableListView);
        expandableListView.setOnGroupExpandListener(this);
        addpackage.setOnClickListener(this);
        mArrayList = new ArrayList<>();
        TablePackageList packageList = new TablePackageList(getActivity());
        if (NetworkUtil.checkInternetConnection(getActivity())) {
            HashMap<String,String> map=new HashMap<>();
            Log.e("org id",AppPreference.getString(getContext(), AppPreference.ORGANISATION_ID));
            map.put("orgid",AppPreference.getString(getContext(), AppPreference.ORGANISATION_ID));
            BaseNetworkRequest<TestListResponse> mRequest = new BaseNetworkRequest<>(getActivity(), ApiConstant.PACKAGE_LIST,
                    this, map, true, TestListResponse.class);
            LabTechnicianApplication.getController(ApiConstant.BASE_URL).enqueueCall(mRequest);
        } else
            if (!packageList.isTableEmpty()) {
            packageList.getPackageList(mArrayList);
        }
//        else {
//            ToastUtils.showShortToastMessage(getActivity(), getString(R.string.server_error));
//        }
        setViewData();
        return view;
    }
    @Override
    public void onGroupExpand(int groupPosition) {
        int len = expandableListAdapter.getGroupCount();
        for (int i = 0; i < len; i++) {
            if (i != groupPosition) {
                expandableListView.collapseGroup(i);
            }
        }
    }
    @Override
    public void handleResponse(Object response, String type) {
        testListResponse = (TestListResponse) response;

        Log.i("response ",new Gson().toJson(testListResponse));
        if (testListResponse.getStatus().equalsIgnoreCase(ApiConstant.SUCCESS)) {
            if (testListResponse.getPackage_list() != null && !testListResponse.getPackage_list().isEmpty()) {
//                TablePackageList patientTest1 = new TablePackageList(getActivity());
//                patientTest1.deleteTableContent();
                TablePackageList patientTest = new TablePackageList(getActivity());
                patientTest.insertPackageList(testListResponse.getPackage_list());
            }
            TablePackageList packageList = new TablePackageList(getActivity());
            packageList.getPackageList(mArrayList);
            Log.e("the pacakge data",new Gson().toJson(mArrayList));
            setViewData();
        } else {
            ToastUtils.showShortToastMessage(getActivity(), getString(R.string.server_error));
        }
    }
    @Override
    public void handleServerError(Object response, String type) {
        ToastUtils.showShortToastMessage(getActivity(), getString(R.string.server_error));
    }

    @Override
    public void setViewData() {
        super.setViewData();
        expandableListAdapter = new PackageListAdapter(getContext(), mArrayList);
        expandableListView.setAdapter(expandableListAdapter);
    }
    @Override
    public void onClick(View view) {
        setFragment(new AddPackageFragemnt(), AddPackageFragemnt.class.getSimpleName());
    }
    @Override
    public void onResume() {
        super.onResume();
        TableGenericPacakge packageList = new TableGenericPacakge(getActivity());
        if (AppPreference.getBoolean(getActivity(), AppPreference.IS_PACKAGE_ADDED)) {
            packageList.getPackageList(mArrayList);
            expandableListAdapter.notifyDataSetChanged();
        }
    }
}

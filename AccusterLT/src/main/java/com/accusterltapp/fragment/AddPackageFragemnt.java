package com.accusterltapp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.accusterltapp.adapter.EditTestAdapter;
import com.accusterltapp.model.SubTestDetails;
import com.accusterltapp.model.TestDetails;
import com.accusterltapp.model.TestListResponse;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.NetworkCallContext;
import com.accusterltapp.table.TableGenericPacakge;
import com.accusterltapp.table.TablePackageList;
import com.base.fragment.BaseFragment;
import com.base.utility.ToastUtils;
import com.accusterltapp.R;

import java.util.ArrayList;
import java.util.Random;


public class AddPackageFragemnt extends BaseFragment implements ExpandableListView.OnGroupExpandListener, View.OnClickListener, NetworkCallContext {
    private ExpandableListView listView;
    private EditTestAdapter adapter;
    private TestListResponse testListResponse;
    private ArrayList<SubTestDetails> mListSelectedTest = new ArrayList<>();
    private ArrayList<TestDetails> mPackageDetails = new ArrayList<>();
    private EditText mEditTextPackageName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addpackages, container, false);
        listView = view.findViewById(R.id.listView);
        mEditTextPackageName = view.findViewById(R.id.package_name);
        listView.setOnGroupExpandListener(this);
        view.findViewById(R.id.bt_save).setOnClickListener(this);
        TableGenericPacakge packageList = new TableGenericPacakge(getActivity());
//        if (NetworkUtil.checkInternetConnection(getActivity())) {
//            BaseNetworkRequest<TestListResponse> mRequest = new BaseNetworkRequest<>(getActivity(), ApiConstant.GET_TEST_LIST,
//                    this, null, true, TestListResponse.class);
//            LabTechnicianApplication.getController(ApiConstant.BASE_URL).enqueueCall(mRequest);
//        } else
        if (!packageList.isTableEmpty()) {
            packageList.getPackageList(mPackageDetails);
            setViewData();
        } else {
            ToastUtils.showShortToastMessage(getActivity(), getString(R.string.server_error));
        }
        return view;
    }

    @Override
    public void setViewData() {
        super.setViewData();
        adapter = new EditTestAdapter(getActivity(), mPackageDetails);
        adapter.setTypeValue(this);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(adapter);
    }


    @Override
    public void onGroupExpand(int groupPosition) {
        int len = adapter.getGroupCount();
        for (int i = 0; i < len; i++) {
            if (i != groupPosition) {
                listView.collapseGroup(i);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (validate()) {
            TablePackageList patientTest = new TablePackageList(getActivity());
            patientTest.insertPacakge(mListSelectedTest, mEditTextPackageName.getText().toString(), getSaltString());
            ToastUtils.showShortToastMessage(getActivity(), "Package added successfully");
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }

    protected String getSaltString() {
        String SALTCHARS = "1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 4) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return "pac" + salt.toString();

    }

    private boolean validate() {
        if (mListSelectedTest == null || mListSelectedTest.isEmpty()) {
            ToastUtils.showShortToastMessage(getActivity(), "Select test");
            return false;
        } else if (TextUtils.isEmpty(mEditTextPackageName.getText())) {
            ToastUtils.showShortToastMessage(getActivity(), "Enter Package Name");
            return false;
        }
        return true;
    }

    @Override
    public void handleResponse(Object response, String type) {
        testListResponse = (TestListResponse) response;
        if (testListResponse.getStatus().equalsIgnoreCase(ApiConstant.SUCCESS)) {
            TableGenericPacakge packageList = new TableGenericPacakge(getActivity());
            packageList.insertPackageList(testListResponse.getTest_list());
            mPackageDetails = testListResponse.getTest_list();
            setViewData();
        } else {
            ToastUtils.showShortToastMessage(getActivity(), getString(R.string.server_error));
        }
    }

    @Override
    public void handleServerError(Object response, String type) {
        ToastUtils.showShortToastMessage(getActivity(), getString(R.string.server_error));
    }


    public void addTest(SubTestDetails details) {
        mListSelectedTest.add(details);
    }

    public void removeTest(SubTestDetails details) {
        mListSelectedTest.remove(details);
    }

}

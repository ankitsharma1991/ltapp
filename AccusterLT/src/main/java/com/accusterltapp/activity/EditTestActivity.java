package com.accusterltapp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.accusterltapp.adapter.EditTestAdapter;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.model.SubTestDetails;
import com.accusterltapp.model.TestDetails;
import com.accusterltapp.model.TestListResponse;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.NetworkCallContext;
import com.accusterltapp.table.TableGenericPacakge;
import com.accusterltapp.table.TablePatientTest;
import com.base.activity.BaseActivity;
import com.base.utility.StringUtils;
import com.base.utility.ToastUtils;
import com.accusterltapp.R;

import java.util.ArrayList;

public class EditTestActivity extends BaseActivity implements ExpandableListView
        .OnGroupExpandListener, NetworkCallContext, View.OnClickListener {

    private ExpandableListView listView;
    EditText et_search;
    private EditTestAdapter adapter;
    public TextView mTextViewPrice;
    private TestListResponse testListResponse;
    private ArrayList<SubTestDetails> mListSelectedTest = new ArrayList<>();
    private ArrayList<TestDetails> mPackageDetails = new ArrayList<>();
    ArrayList<TestDetails> filteredList = new ArrayList<>();
    String query;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_test);
        setActionBar(R.id.include_toolbar);
        setToolBarTittle("Add Test");
        mTextViewPrice = findViewById(R.id.txt_total);
        listView = findViewById(R.id.listView);
        et_search=findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                query =et_search.getText().toString().toLowerCase();
                ArrayList<TestDetails> filteredList = new ArrayList<>();
             for (int a=0;a<mPackageDetails.size();a++)
             {
                 boolean flag=false;
                 for(int b=0;b<mPackageDetails.get(a).getTest_list().size();b++)
                 {
                     String name=mPackageDetails.get(a).getTest_list().get(b).getTest_name().toLowerCase();
                     if (name.contains(query))
                         flag=true;
                 }
                 if (flag)
                 filteredList.add(mPackageDetails.get(a));
             }
                adapter = new EditTestAdapter(EditTestActivity.this, filteredList);
                listView.setAdapter(adapter);
                Log.e("show ","data");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        listView.setOnGroupExpandListener(this);
        findViewById(R.id.fab).setOnClickListener(this);
        TableGenericPacakge packageList = new TableGenericPacakge(this);
//        if (NetworkUtil.checkInternetConnection(this)) {
//            BaseNetworkRequest<TestListResponse> mRequest = new BaseNetworkRequest<>(this, ApiConstant.GET_TEST_LIST,
//                    this, null, true, TestListResponse.class);
//            LabTechnicianApplication.getController(ApiConstant.BASE_URL).enqueueCall(mRequest);
//        } else
        if (!packageList.isTableEmpty()) {
            packageList.getPackageList(mPackageDetails);
            setViewData();
        } else {
            ToastUtils.showShortToastMessage(this, getString(R.string.server_error));
        }
    }


    @Override
    public void setViewData() {
        super.setViewData();
   TablePatientTest patientTest = new TablePatientTest(EditTestActivity.this.getApplicationContext());
        ArrayList<SubTestDetails> ptestlist=new ArrayList<>();

        patientTest.getallPatientTest(ptestlist,getIntent().getStringExtra("plabelId"));
        ArrayList<String > id_list=new ArrayList<>();
        for (int i=0;i<ptestlist.size();i++)
        {
            Log.e("test name and is ",ptestlist.get(i).getTest_code());
            id_list.add(ptestlist.get(i).getTest_code());
        }

        Heleprec.testId_list=id_list;
        adapter = new EditTestAdapter(this, mPackageDetails);
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
    public void handleResponse(Object response, String type) {
        testListResponse = (TestListResponse) response;
        if (testListResponse.getStatus().equalsIgnoreCase(ApiConstant.SUCCESS)) {
            TableGenericPacakge packageList = new TableGenericPacakge(this);
            packageList.insertPackageList(testListResponse.getTest_list());
         //   mPackageDetails = testListResponse.getTest_list();
            setViewData();
        } else {
            ToastUtils.showShortToastMessage(this, getString(R.string.server_error));
        }
    }

    @Override
    public void handleServerError(Object response, String type) {
        ToastUtils.showShortToastMessage(this, getString(R.string.server_error));
    }

    public void setTotalAmount(double amount) {
        mTextViewPrice.setText(String.format("%s %s", getString(R.string.Rs), StringUtils.doubleToIndianFormat(amount)));
    }

    @Override
    public void onClick(View view) {
        if (mListSelectedTest != null && !mListSelectedTest.isEmpty()) {
            TablePatientTest patientTest = new TablePatientTest(this);
            patientTest.insertSinglePatient(mListSelectedTest, getIntent().getStringExtra("plabelId"),
                    getIntent().getStringExtra("campCode"), getIntent().getStringExtra("labelId"));
            AppPreference.setBoolean(this, AppPreference.TEST_TABLE_UPDATE, true);
        }
        finish();

    }

    public void addTest(SubTestDetails details) {
        mListSelectedTest.add(details);
    }

    public void removeTest(SubTestDetails details) {
        mListSelectedTest.remove(details);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
    }
}

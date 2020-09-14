package com.accusterltapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accusterltapp.R;
import com.accusterltapp.model.WidalTest;
import com.accusterltapp.model.WidalTestData;
import com.accusterltapp.model.WidalTestModel;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.table.TableWidalData;
import com.accusterltapp.table.TableWidalTest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddWidalTestResultActivity extends AppCompatActivity implements View.OnClickListener,Validator.ValidationListener {
    TextView tv_widal_test_name1,tv_widal_test_name2,tv_widal_test_name3,tv_widal_test_name4,tv_widal_head1,tv_widal_head2,tv_widal_head3,tv_widal_head4,tv_widal_head5,tv_action;
    @NotEmpty
    EditText et_r11,et_r12,et_r13,et_r14,et_r15,et_r21,et_r22,et_r23,et_r24,et_r25,et_r31,et_r32,et_r33,et_r34,et_r35,et_r41,et_r42,et_r43,et_r44,et_r45;
    LinearLayout ll_save;
    String patient_id;
    Validator validator;
    WidalTest widalTest=null;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_widal_test);
        patient_id=getIntent().getExtras().getString("patient_id");
        validator=new Validator(this);
        validator.setValidationListener(this);
       // getActionBar().setTitle("WidalTest");

        init();
    }
    public void init()
    {
        toolbar=findViewById(R.id.include_toolbar);
        toolbar.setTitle("WidalTest");
        tv_action=findViewById(R.id.tv_action);
        tv_widal_head1=findViewById(R.id.tv_t1);
        tv_widal_head2=findViewById(R.id.tv_t2);
        tv_widal_head3=findViewById(R.id.tv_t3);
        tv_widal_head4=findViewById(R.id.tv_t4);
        tv_widal_test_name1=findViewById(R.id.tv_l1);
        tv_widal_test_name2=findViewById(R.id.tv_l2);
        tv_widal_test_name3=findViewById(R.id.tv_l3);
        tv_widal_test_name4=findViewById(R.id.tv_l4);
       et_r11=findViewById(R.id.et_r11);
        et_r12=findViewById(R.id.et_r12);
        et_r13=findViewById(R.id.et_r13);
        et_r14=findViewById(R.id.et_r14);
        et_r21=findViewById(R.id.et_r21);
        et_r22=findViewById(R.id.et_r22);
        et_r23=findViewById(R.id.et_r23);
        et_r24=findViewById(R.id.et_r24);
        et_r31=findViewById(R.id.et_r31);
        et_r32=findViewById(R.id.et_r32);
        et_r33=findViewById(R.id.et_r33);
        et_r34=findViewById(R.id.et_r34);
        et_r41=findViewById(R.id.et_r41);
        et_r42=findViewById(R.id.et_r42);
        et_r43=findViewById(R.id.et_r43);
        et_r44=findViewById(R.id.et_r44);
        et_r15=findViewById(R.id.et_r15);
       et_r25=findViewById(R.id.et_r25);
        et_r35=findViewById(R.id.et_r35);
        et_r45=findViewById(R.id.et_r45);
        tv_widal_head5=findViewById(R.id.tv_t5);
        ll_save=findViewById(R.id.ll_save);
ll_save.setOnClickListener(this);
WidalTestData widalTestData=null;
TableWidalData tableWidalData=new TableWidalData(this);
widalTestData=tableWidalData.getWidalData();
if (widalTestData!=null)
{
    tv_widal_test_name1.setText(widalTestData.getWidal_test_left_1());
    tv_widal_test_name2.setText(widalTestData.getWidal_test_left_2());
    tv_widal_test_name3.setText(widalTestData.getWidal_test_left_3());
    tv_widal_test_name4.setText(widalTestData.getWidal_test_left_4());
    tv_widal_head1.setText(widalTestData.getWidal_test_header_1());
    tv_widal_head2.setText(widalTestData.getWidal_test_header_2());
    tv_widal_head3.setText(widalTestData.getWidal_test_header_3());
    tv_widal_head4.setText(widalTestData.getWidal_test_header_4());
    tv_widal_head5.setText(widalTestData.getWidal_test_header_5());
tv_widal_head5.setText(widalTestData.getWidal_test_header_5());
    TableWidalTest tableWidalTest=new TableWidalTest(this);
    ArrayList<WidalTest> list=new ArrayList<>();
    tableWidalTest.getwidaltestList(list);
    Gson gson=new Gson();;
    Log.e(" the json data",gson.toJson(list));
    widalTest=tableWidalTest.getWidaltest(patient_id);
    if (widalTest!=null)
    {
        tv_action.setText("Update");
        et_r11.setText(widalTest.getWidal_result11());
        et_r12.setText(widalTest.getWidal_result12());
        et_r13.setText(widalTest.getWidal_result13());
        et_r14.setText(widalTest.getWidal_result14());
        et_r21.setText(widalTest.getWidal_result21());
        et_r22.setText(widalTest.getWidal_result22());
        et_r23.setText(widalTest.getWidal_result23());
        et_r24.setText(widalTest.getWidal_result24());
        et_r31.setText(widalTest.getWidal_result31());
        et_r32.setText(widalTest.getWidal_result32());
        et_r33.setText(widalTest.getWidal_result33());
        et_r34.setText(widalTest.getWidal_result34());
        et_r41.setText(widalTest.getWidal_result41());
        et_r42.setText(widalTest.getWidal_result42());
        et_r43.setText(widalTest.getWidal_result43());
        et_r44.setText(widalTest.getWidal_result44());
        et_r15.setText(widalTest.getWidal_result15());
        et_r25.setText(widalTest.getWidal_result25());
        et_r35.setText(widalTest.getWidal_result35());
        et_r45.setText(widalTest.getWidal_result45());
    }
}else
    loadWidalData();
    }
    @Override
    public void onClick(View view) {
        validator.validate();
    }
    @Override
    public void onValidationSucceeded() {
            WidalTest widalTest = new WidalTest();
            TableWidalData tableWidalData = new TableWidalData(this);
            WidalTestData widalTestData = new WidalTestData();
            widalTestData = tableWidalData.getWidalData();
            widalTest.setPid(patient_id);
            String create_time;
            String update_time;
            Calendar cal=Calendar.getInstance();
            SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
            widalTest.setWidal_test_updated_time( format.format(cal.getTime()));
            if (this.widalTest==null)
                widalTest.setWidal_test_created_time(format.format(cal.getTime()));
            else widalTest.setWidal_test_created_time(this.widalTest.getWidal_test_created_time());
            widalTest.setWidal_result11(et_r11.getText().toString());
            widalTest.setWidal_test_interpretation(widalTestData.getWidal_test_interpretation());
            widalTest.setWidal_test_precaution(widalTestData.getWidal_test_precaution());
            widalTest.setWidal_result12(et_r12.getText().toString());
            widalTest.setWidal_result13(et_r13.getText().toString());
            widalTest.setWidal_result14(et_r14.getText().toString());
            widalTest.setWidal_result21(et_r21.getText().toString());
            widalTest.setWidal_result22(et_r22.getText().toString());
            widalTest.setWidal_result23(et_r23.getText().toString());
            widalTest.setWidal_result24(et_r24.getText().toString());
            widalTest.setWidal_result31(et_r31.getText().toString());
            widalTest.setWidal_result32(et_r32.getText().toString());
            widalTest.setWidal_result33(et_r33.getText().toString());
            widalTest.setWidal_result34(et_r34.getText().toString());
            widalTest.setWidal_result41(et_r41.getText().toString());
            widalTest.setWidal_result42(et_r42.getText().toString());
            widalTest.setWidal_result43(et_r43.getText().toString());
            widalTest.setWidal_result44(et_r44.getText().toString());
            widalTest.setWidal_result15(et_r15.getText().toString());
            widalTest.setWidal_result25(et_r25.getText().toString());
            widalTest.setWidal_result35(et_r35.getText().toString());
            widalTest.setWidal_result45(et_r45.getText().toString());
            widalTest.setWidal_test_head5(widalTestData.getWidal_test_header_5());
            widalTest.setWidal_test_head1(widalTestData.getWidal_test_header_1());
            widalTest.setWidal_test_head2(widalTestData.getWidal_test_header_2());
            widalTest.setWidal_test_head3(widalTestData.getWidal_test_header_3());
            widalTest.setWidal_test_head4(widalTestData.getWidal_test_header_4());
            widalTest.setWidal_test_name1(widalTestData.getWidal_test_left_1());
            widalTest.setWidal_test_name2(widalTestData.getWidal_test_left_2());
            widalTest.setWidal_test_name3(widalTestData.getWidal_test_left_3());
            widalTest.setWidal_test_name4(widalTestData.getWidal_test_left_4());
            TableWidalTest tableWidalTest = new TableWidalTest(this);
            tableWidalTest.insertPatientWidaltest(widalTest);
            Gson gson = new Gson();
            Log.e("the data", gson.toJson(widalTest));
            if(this.widalTest!=null)
                Toast.makeText(AddWidalTestResultActivity.this, "Updated", Toast.LENGTH_SHORT).show();
            else {
                tv_action.setText("Update");
                Toast.makeText(AddWidalTestResultActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
    }
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
    public void loadWidalData()
    {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, "http://qc.eaccuster.com/api/v2/getWidalTestData", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response ",response);
                Gson gson=new Gson();
                WidalTestModel widalTestModel=gson.fromJson(response,WidalTestModel.class);
                WidalTestData widalTestData=widalTestModel.getWidalData();
                TableWidalData data=new TableWidalData(getApplicationContext());
                data.insertWidalData(widalTestData);
                tv_widal_test_name1.setText(widalTestData.getWidal_test_left_1());
                tv_widal_test_name2.setText(widalTestData.getWidal_test_left_2());
                tv_widal_test_name3.setText(widalTestData.getWidal_test_left_3());
                tv_widal_test_name4.setText(widalTestData.getWidal_test_left_4());
                tv_widal_head1.setText(widalTestData.getWidal_test_header_1());
                tv_widal_head2.setText(widalTestData.getWidal_test_header_2());
                tv_widal_head3.setText(widalTestData.getWidal_test_header_3());
                tv_widal_head4.setText(widalTestData.getWidal_test_header_4());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error ",error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Collections.emptyMap();
            }
        };
        queue.add(sr);
    }
}

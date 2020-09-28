package com.accusterltapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.accusterltapp.activity.MainActivity;
import com.accusterltapp.adapter.PatientaddAdapter;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.model.ApoointMentDataToSend;
import com.accusterltapp.model.AppointMentData;
import com.accusterltapp.model.AppointMentDatalist;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.model.PatientIdList;
import com.accusterltapp.model.RegisterPatient;
import com.accusterltapp.model.SubTestDetails;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.table.TablePackageTestDetail;
import com.accusterltapp.table.TablePatient;
import com.accusterltapp.table.TablePatientTest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.base.fragment.BaseFragment;
import com.base.listener.RecyclerViewListener;
import com.base.utility.DateTimeUtils;
import com.base.utility.StringUtils;
import com.base.utility.ToastUtils;
import com.google.gson.Gson;
import com.accusterltapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PatientFragment  extends BaseFragment implements RecyclerViewListener {
    private ArrayList<RegisterPatient> patientList;
    private TablePatient mPatient;
    Context mcontext;
    PatientFragment object;
    private PatientaddAdapter patientTableDataAdapter;
    private RecyclerView patientTableView;
    FloatingActionButton fb;
    ProgressBar pb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmentaddpatient, container, false);
        getActivity().setTitle("AppointMent List");
        patientTableView = view.findViewById(R.id.rv_patient_view);
        pb=view.findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        patientTableView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mcontext=this.getContext();
        object=this;
        fb=view.findViewById(R.id.fab);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(mcontext,"clic event occur ",Toast.LENGTH_LONG).show();
                ArrayList<AppointMentData> apdata= Heleprec.appointList;
                ApoointMentDataToSend senddata=new ApoointMentDataToSend();
                senddata.setCamp_id(getArguments().getString("campCode"));
                mPatient = new TablePatient(getActivity());
                ArrayList<PatientIdList> idlist=new ArrayList<>();
                for(int j=0;j<apdata.size();j++)
                {
                    PatientIdList data=new PatientIdList();
                    data.setId(apdata.get(j).getId());
                    idlist.add(data);
                }
                senddata.setList(idlist);

                try {
                    Gson g = new Gson();

                    Log.e("gson data",g.toJson(senddata));
                    com.android.volley.RequestQueue queue= Volley.newRequestQueue(PatientFragment.this.getContext());
                    try {
                        JSONObject ob = new JSONObject(g.toJson(senddata));
                        JsonObjectRequest request = new JsonObjectRequest(ApiConstant.BASE_URL1+"setApproveAppointments", ob,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.e("oh oh response",response.toString());
                                        //TODO verificar o status code da resposta apenas deverá registar login caso seja 200
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String body;
                                Log.e(" oh error", error.toString());

                            }


                        });

                        //  request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        queue.add(request);
                    }
                    catch (Exception e)
                    {

                    }
                }
                catch (Exception e)
                {

                }
              String  patientID = StringUtils.getSaltString() + AppPreference.getString(getActivity(), AppPreference.USER_ID);
                for(int i=0;i<apdata.size();i++) {
                    RegisterPatient patient = new RegisterPatient();
                    try {
                        patient.setUserregistration_complete_name(apdata.get(i).getName());
                        patient.setUserregistration_code(apdata.get(i).getId());
                        patient.setUserregistration_mobile_number(apdata.get(i).getMobile());
                        patient.setUserregistration_age(apdata.get(i).getAge());
                        patient.setUserregistration_gender_id(apdata.get(i).getGender());

                        //patient.setUserregistration_address_line_1(address.getText().toString().trim());

                        patient.setUserregistration_email_address(apdata.get(i).getEmail());
                        patient.setDate(DateTimeUtils.getCurrentDate(DateTimeUtils.OUTPUT_HYPHEN_DATE_DD_MM_YYYY));
                        patient.setUserregistration_created_time(apdata.get(i).getAppointment_time());
                        patient.setCampName(apdata.get(i).getCamp_name());
                      //  patient.setUserregistration_org_id(OrgId);

                        patient.setUserregistration_camp_code(apdata.get(i).getCamp_code());
                        Log.d("camp code add ",apdata.get(i).getCamp_id());
                        patient.setUserregistration_org_id(AppPreference.ORGANISATION_ID);
                        patient.setUserregistration_address_line_1("sdfhghsd");
                      //  patient.setUserregistration_code(apdata.get(i).getId());
                       // patient.setpLabelId();

                        patient.setUserregistration_Lt_id(AppPreference.getString(getActivity(), AppPreference.USER_ID));
                        mPatient.insertSinglePatient(patient);

                        TablePatientTest patientTest = new TablePatientTest(getActivity());
                        TablePackageTestDetail packageList = new TablePackageTestDetail(getActivity());
                        ArrayList<SubTestDetails> subTestDetails = new ArrayList<>();
                        patientTest.insertSinglePatient(packageList.getAllSubTestList(subTestDetails, ""),apdata.get(i).getId(), apdata.get(i).getCamp_code(),
                                mPatient.getLabelId(apdata.get(i).getId()));


                      //  setPatientId();
                       //Refreshpage();
                       // patientID = StringUtils.getSaltString() + AppPreference.getString(getActivity(), AppPreference.USER_ID);
                     //   patientTableDataAdapter.notifyDataSetChanged();
                        Log.d("insert ","insertid data");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        ToastUtils.showShortToastMessage(getActivity(), getString(R.string.server_error));
                    }
                }
                Toast.makeText(mcontext,"AppointMent added ",Toast.LENGTH_LONG).show();
                MainActivity.navItemIndex = 1;
                ((MainActivity)getActivity()).setToolbarTitle();
                PatientRegistrationFragment patient_detail = new PatientRegistrationFragment();
                setFragment(patient_detail, PatientRegistrationFragment.class.getSimpleName());

            }
        });

        com.android.volley.RequestQueue queue = Volley.newRequestQueue(this.getContext());
        StringRequest sr = new StringRequest(Request.Method.POST,ApiConstant.BASE_URL1+"getAppointmentsApi", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pb.setVisibility(View.INVISIBLE);
                    Gson g = new Gson();
                    AppointMentDatalist d = g.fromJson(response, AppointMentDatalist.class);
                    patientTableDataAdapter = new PatientaddAdapter(mcontext, d.getAppointmentData(), (PatientFragment) object);
                    patientTableView.setAdapter(patientTableDataAdapter);

                    // mPostCommentResponse.requestCompleted();
                    //  System.out.print(response);
                    Log.d("response", response);
                }
                catch (Exception e)
                {

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
                params.put("camp_id",getArguments().getString("campCode"));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Collections.emptyMap();
            }
        };
        queue.add(sr);
        return view;
    }


    @Override
    public void onItemClickListener(View view, int position, HashMap<String, String> hashMap) {
        if (hashMap.get("action").equalsIgnoreCase("show")) {
            PatientPreview patient_preview = new PatientPreview();
            Bundle args = new Bundle();
            args.putString("camp_name", patientList.get(position).getCampName());
            args.putString("patient_name", patientList.get(position).getUserregistration_complete_name());
            args.putString("patient_id", patientList.get(position).getUserregistration_code());
            args.putString("label_id", patientList.get(position).getpLabelId());
            args.putString("process_date", patientList.get(position).getUserregistration_created_time());
            args.putString("time", patientList.get(position).getUserregistration_created_time());
            args.putParcelable("patientDetail", patientList.get(position));
            patient_preview.setArguments(args);
            //Inflate the fragment
            setFragment(patient_preview, PatientPreview.class.getSimpleName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}


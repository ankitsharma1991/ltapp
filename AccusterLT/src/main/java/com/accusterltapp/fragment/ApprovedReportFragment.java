package com.accusterltapp.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.accusterltapp.adapter.ApprovedReportListAdapter;
import com.accusterltapp.adapter.StatusAdapter;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.model.ApprovedReportData;
import com.accusterltapp.model.ApprovedReportDetailsList;
import com.accusterltapp.model.ApprovedReportList;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.NetworkCallContext;
import com.accusterltapp.table.TableCamp;
import com.accusterltapp.table.TablePatient;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.base.fragment.BaseFragment;
import com.base.listener.RecyclerViewListener;
import com.base.utility.PdfGenerator2;
import com.google.gson.Gson;
import com.accusterltapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
public class ApprovedReportFragment extends BaseFragment implements RecyclerViewListener, NetworkCallContext {
    private RecyclerView recyclerView;
    private ApprovedReportListAdapter adapter;
    private TableCamp tableCamp;
    Spinner filter_spinner;
    String lt_id;
    String camp_code;
    String reportStatus[] = {"All", "Approve", "Pending", "Reject"};
    ArrayAdapter  statusAdapter;
boolean  status=false;
    TablePatient tablePatient;
    ProgressDialog pd;
    android.widget.ProgressBar pb;
    ApprovedReportDetailsList list;
    ArrayList<ApprovedReportData> report_list,filter_list;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    SharedPreferences pref;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    Button fb,all,pdfDownload;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private SharedPreferences permissionStatus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        camp_code=getArguments().getString("campCode");
        Log.e("camp_code",camp_code);
        lt_id= AppPreference.getString(this.getContext(), AppPreference.USER_ID);
        View view = inflater.inflate(R.layout.approved_report_fragment, container, false);
        tableCamp = new TableCamp(getActivity());
        tablePatient = new TablePatient(getActivity());
        pb= view.findViewById(R.id.pb);
        permissionStatus = ApprovedReportFragment.this.getActivity().getSharedPreferences("permissionStatus", ApprovedReportFragment.this.getActivity().MODE_PRIVATE);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {
            String imageUrl = ApiConstant.imageurl + Heleprec.repostlistmap.get(Heleprec.current_camp_name).getCampLogo();
            Volley.newRequestQueue(ApprovedReportFragment.this.getActivity()).add(new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    Log.e("logo", "logo");
                    Heleprec.logo = bitmap;
                }
            },
                    1024, 1024, null, null));
        }
        catch (Exception e)
        {

        }
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(this.getContext());
        StringRequest sr = new StringRequest(Request.Method.POST,ApiConstant.BASE_URL1+"getAllReportApiWithDate", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.setVisibility(View.GONE);
                Log.d("response re",response);
                try {
                    JSONObject object =new JSONObject(response);
                    Gson gn=new Gson();
                    ApprovedReportList clist=gn.fromJson(response,ApprovedReportList.class);
                    Log.d("arr",clist.toString());
                    report_list=new ArrayList<>();
                    for(int i=0;i<clist.getTotalApprovedReport().size();i++)
                    {
                        Log.e("the select camp code",camp_code+" and "+clist.getTotalApprovedReport().get(i).getCamp_code());
                        if (camp_code.equals(clist.getTotalApprovedReport().get(i).getCamp_code()))
                        {
                            report_list.add(clist.getTotalApprovedReport().get(i));
                        }
                    }

                    setupRecycler(report_list);

                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                params.put("lt_id",lt_id);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Collections.emptyMap();
            }
        };
            queue.add(sr);
        filter_spinner=view.findViewById(R.id.filter);
        statusAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, reportStatus);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     filter_spinner.setAdapter(statusAdapter);
     filter_spinner.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View view, MotionEvent motionEvent) {
             status=true;
             return false;
         }
     });
     filter_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             if (status) {
                 filter_list = new ArrayList<>();
                 String selcet_tag = null;
                 if (i == 0)
                     filter_list = report_list;
                 else if (i == 1)
                     selcet_tag = "1";
                 else if (i == 2)
                     selcet_tag = "2";
                 else if (i == 3)
                     selcet_tag = "3";
                 if (selcet_tag!=null)
                 for (int j = 0; j < report_list.size(); j++) {
                     if (selcet_tag.equals(report_list.get(j).getReport_status())) {
                         filter_list.add(new ApprovedReportData(report_list.get(j).getId(), report_list.get(j).getUid(), report_list.get(j).getCamp(), report_list.get(j).getName(), report_list.get(j).getTest_manual_status(), report_list.get(j).getPatientCode(), report_list.get(j).getContact(), report_list.get(j).getAge(), report_list.get(j).getGender(), report_list.get(j).getEmail(), report_list.get(j).getTest(), report_list.get(j).getAmount(), report_list.get(j).getCreated(), report_list.get(j).getReport_status(),report_list.get(j).getCamp_code()));
                     }
                 }
                 setupRecycler(filter_list);
             }
         }
         @Override
         public void onNothingSelected(AdapterView<?> adapterView) {

         }
     });
        return view;
    }
    private void setupRecycler(ArrayList<ApprovedReportData> campList) {
        // create an empty adapter and ButtonAddPatient it to the recycler view

            adapter = new ApprovedReportListAdapter(getActivity(), this, campList);
            recyclerView.setAdapter(adapter);
    }
    @Override
    public void onItemClickListener(View view, int position, final HashMap<String, String> hashMap) {
        if (view.getId()==R.id.iv_info)
        {
            pd = new ProgressDialog(ApprovedReportFragment.this.getActivity());
            pd.setMessage("loading");
            pd.show();
            com.android.volley.RequestQueue queue = Volley.newRequestQueue(this.getContext());
            StringRequest sr = new StringRequest(Request.Method.POST, ApiConstant.BASE_URL1 + "getReportDetailsLtApi", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response REport= ", response);
                    pd.dismiss();
                    try {
                        JSONObject object = new JSONObject(response);
                        Gson gn = new Gson();
                      final   ApprovedReportDetailsList clist = gn.fromJson(response, ApprovedReportDetailsList.class);

                        AlertDialog.Builder dialod_reg=new AlertDialog.Builder(getContext());


                        View v=LayoutInflater.from(getContext()).inflate(R.layout.layout_status_dialog,null,false);
                        ImageView iv_view=v.findViewById(R.id.iv_view);
                        iv_view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (clist.getUserdetails().size() > 0) {
                                    printpdf(clist);

                                }
                            }
                        });
                       RecyclerView rv_status=v.findViewById(R.id.rv_list);
                       rv_status.setLayoutManager(new LinearLayoutManager(getContext()));
                       rv_status.setAdapter(new StatusAdapter(getContext(),clist.getTest_details()));
                        dialod_reg.setView(v);
                        dialod_reg.show();


                        //setupRecycler(clist.getTotalApprovedReport());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error ", error.toString());
                    // mPostCommentResponse.requestEndedWithError(error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    Log.e("pateint code ", hashMap.get("patientcode"));
                    params.put("report_patient_code", hashMap.get("patientcode"));

                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return Collections.emptyMap();
                }
            };
            queue.add(sr);
        }
        else {
           // pb.setVisibility(View.VISIBLE);
            pd = new ProgressDialog(ApprovedReportFragment.this.getActivity());
            pd.setMessage("loading");
            pd.show();
            com.android.volley.RequestQueue queue = Volley.newRequestQueue(this.getContext());
            StringRequest sr = new StringRequest(Request.Method.POST, ApiConstant.BASE_URL1 + "getReportDetailsLtApi", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response", response);
                    pd.dismiss();
                    try {
                        JSONObject object = new JSONObject(response);
                        Gson gn = new Gson();
                        ApprovedReportDetailsList clist = gn.fromJson(response, ApprovedReportDetailsList.class);
                        Log.d("arr", clist.toString());
                        if (clist.getUserdetails().size() > 0) {
                            printpdf(clist);

                        } else Log.e("size is ", clist.getUserdetails().size() + "");
                        //setupRecycler(clist.getTotalApprovedReport());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error ", error.toString());
                    // mPostCommentResponse.requestEndedWithError(error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    Log.e("pateint code ", hashMap.get("patientcode"));
                    params.put("report_patient_code", hashMap.get("patientcode"));

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
    @Override
    public void handleResponse(Object response, String type) {
        ApprovedReportList campList = (ApprovedReportList) response;

        Log.e("Response ",campList.getTotalApprovedReport().size()+"");
      //  ArrayList<CampDetails> campListModel = new ArrayList<>();
        setupRecycler(campList.getTotalApprovedReport());
    }
    @Override
    public void handleServerError(Object response, String type) {
    }
    public void printpdf(final ApprovedReportDetailsList list)
    {
Log.e("this called ","method");
        try {
            String imageUrl = ApiConstant.imageurl +list.getPathlogist().getSignature();
            Volley.newRequestQueue(ApprovedReportFragment.this.getActivity()).add(new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    // Do something with loaded bitmap...
                    Log.e("logo", "logo");
                    //pd.dismiss();
                    Heleprec.sinature = bitmap;
                    ApprovedReportFragment.this.list=list;
                    go(bitmap);


                }
            },
                    1024, 1024, null, null));
        }
        catch (Exception e)
        {
            Log.e("exception is ",e.getMessage());
        }
    }
    public void go( Bitmap bitmap)
    {
        boolean installed = appInstalledOrNot("com.adobe.reader");
        if (installed) {
            Log.e("install status is",permissionStatus.getBoolean(permissionsRequired[2], false)+"");
            int t = ContextCompat.checkSelfPermission(ApprovedReportFragment.this.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            int f1=ContextCompat.checkSelfPermission(ApprovedReportFragment.this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (t == PackageManager.PERMISSION_GRANTED&&f1==PackageManager.PERMISSION_GRANTED)
            {
               // pd.show();

                new ApprovedReportFragment.RetrieveFeedTask().execute(null, null);
            }
            else {
                setPermisio();
            }
        }
        else {
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Adobe reader application not installed.");
            alertDialog.setMessage("Adobe reader  is required to view report.");
//                    alertDialog.setMessage(sdf.format(date3) +" is after " + sdf.format(date2));
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.setButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            alertDialog.show();
        }
    }
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getContext().getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... strings) {
            PdfGenerator2 pdf=new PdfGenerator2(getActivity());
            pdf.generatePDF(list);
            pd.dismiss();
            //  pd.dismiss();
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pd!=null)
            pd.dismiss();
    }
    public void setPermisio()
    {
        try {
            //  permissionStatus = PatientPreview.this.getActivity().getSharedPreferences("permissionStatus", PatientPreview.this.getActivity().MODE_PRIVATE);
            if (ActivityCompat.checkSelfPermission(ApprovedReportFragment.this.getActivity().getApplicationContext(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(ApprovedReportFragment.this.getActivity().getApplicationContext(), permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(ApprovedReportFragment.this.getActivity().getApplicationContext(), permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(ApprovedReportFragment.this.getActivity().getApplicationContext(), permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(ApprovedReportFragment.this.getActivity().getApplicationContext(), permissionsRequired[4]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ApprovedReportFragment.this.getActivity(), permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(ApprovedReportFragment.this.getActivity(), permissionsRequired[1])
                        || ActivityCompat.shouldShowRequestPermissionRationale(ApprovedReportFragment.this.getActivity(), permissionsRequired[2])
                        || ActivityCompat.shouldShowRequestPermissionRationale(ApprovedReportFragment.this.getActivity(), permissionsRequired[3])
                        || ActivityCompat.shouldShowRequestPermissionRationale(ApprovedReportFragment.this.getActivity(), permissionsRequired[4])) {
                    //Show Information about why you need the permission
                    android.app.AlertDialog.Builder builder = new  android.app.AlertDialog.Builder(ApprovedReportFragment.this.getContext());
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Camera and Location permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(ApprovedReportFragment.this.getActivity(), permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                    //Previously Permission Request was cancelled with 'Dont Ask Again',
                    // Redirect to Settings after showing Information about why you need the permission
                    android.app.AlertDialog.Builder builder = new  android.app.AlertDialog.Builder(ApprovedReportFragment.this.getContext());
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Camera and Location permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            // sentToSettings = true;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", ApprovedReportFragment.this.getActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            Toast.makeText(ApprovedReportFragment.this.getActivity().getApplicationContext(), "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    //just request the permission
                    ActivityCompat.requestPermissions(ApprovedReportFragment.this.getActivity(), permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                }

                SharedPreferences.Editor editor = permissionStatus.edit();
                editor.putBoolean(permissionsRequired[0], true);
                editor.commit();
            }
        }
        catch (Exception e)
        {

        }
    }
}

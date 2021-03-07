package com.accusterltapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.accusterltapp.database.AppConstants;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.fragment.AboutUs;
import com.accusterltapp.fragment.CampListFragment1;
import com.accusterltapp.fragment.CampsListFragment;
import com.accusterltapp.fragment.CreateCampFragment;
import com.accusterltapp.fragment.HelpInfo;
import com.accusterltapp.fragment.HomeFragment;
import com.accusterltapp.fragment.PackageListFragment;
import com.accusterltapp.fragment.PatientPreview;
import com.accusterltapp.fragment.PatientRegistrationFragment;
import com.accusterltapp.fragment.QcDataFragment;
import com.accusterltapp.fragment.ReportSettingFragment;
import com.accusterltapp.login.LoginActivity;
import com.accusterltapp.model.CampDetails1;
import com.accusterltapp.model.CampList1;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.model.QcData;
import com.accusterltapp.model.QcDataList;
import com.accusterltapp.model.SubTestDetails;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.NetworkUtil;
import com.accusterltapp.table.TableCamp;
import com.accusterltapp.table.TablePackageList;
import com.accusterltapp.table.TablePatient;
import com.accusterltapp.table.TablePatientTest;
import com.accusterltapp.table.TableQcData;
import com.accusterltapp.table.TableReportSetting;
import com.accusterltapp.table.TableWidalData;
import com.accusterltapp.table.TableWidalTest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.base.activity.BaseActivity;
import com.base.listener.DialogCloseListener;
import com.base.model.CampDetails;
import com.base.utility.DateTimeUtils;
import com.base.utility.ImageCacheUtil;
import com.base.utility.ToastUtils;
import com.google.gson.Gson;
import com.accusterltapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, DialogCloseListener {
    public static int navItemIndex = 0;
    public NavigationView navigationView;
    private DrawerLayout drawer;
    private ImageView imgProfile;
    public TextView name, email;
    boolean isc1=false;
    String  testName;
    private int sletectedItem = 0;
    private static final int CAMERA_REQUEST = 1888;
    public static String reportFileName = "";
    boolean a=true;
    String versionName;
    private String ltid;
    String testNamec3=null;
    public QcData qcdata;
    String string_for_l1="##:,Glu_r,mg?dl,1.645,L1,LA124,:##!";
    String  string_for_l2="##:,Glu_r,mg?dl,1.645,L2,LA124,:##!";
    String string_for_c_data="##:,Glu-R,mg/dl,796.804,C1,:##796.804 ##:,Glu-R,mg/dl,764.489,C2,:##764.489 ##:,Glu-R,mg/dl,813.557,C3,:##813.557";
    private String[] activityTitles;
    private TableCamp tableCamp;
    private StringBuffer mBufferTestResponse = new StringBuffer();
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       TableWidalData tableWidalTest=new TableWidalData(this);
        tableCamp = new TableCamp(this);
        getUpdatedCampListFromServer();
        Log.e("the widal",tableWidalTest.mDbObject+"data");
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setToolBarTittle("Home");
        try {
            versionName = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0).versionName;
            Log.e("version name is",versionName);
            com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest sr = new StringRequest(Request.Method.POST, ApiConstant.BASE_URL1+"getAppInformationApi", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String version_name=null;
                    Log.e("responce fro version ",response);
                    try {
                        JSONObject g=new JSONObject(response);
                        version_name=g.getString("version_name");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage("New Verion of This app is Availble in play store so download it...");
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.accusterltapp"));
                                    startActivity(intent);
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    if(!versionName.equals(version_name))
                        alert11.show();
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
                    params.put("name","Lt App");
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return Collections.emptyMap();
                }
            };
            queue.add(sr);
            Log.e("request add","add");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("exception arrived","axception");
        }
        // table.insertQcData(qcdata);
        TableQcData data=new TableQcData(MainActivity.this);
        ArrayList<QcData> lis=new ArrayList<>();
        data.getQcdataList(lis);
        ArrayList<SubTestDetails> listtest=new ArrayList<SubTestDetails>();
        TablePatientTest t=new TablePatientTest(MainActivity.this.getBaseContext());
        QcDataList qcDataList=new QcDataList();
        qcDataList.setList(lis);
        qcDataList.setLtid(AppPreference.getString(this, AppPreference.USER_ID));
        Gson g=new Gson();
        Log.e("gson data",g.toJson(qcDataList));
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View mNavHeader = navigationView.getHeaderView(0);
//        imgProfile = mNavHeader.findViewById(R.id.img_profile);
        ((TextView) mNavHeader.findViewById(R.id.email)).setText(AppPreference.getString(this, AppPreference.E_MAIL));
        ((TextView) mNavHeader.findViewById(R.id.name)).setText(AppPreference.getString(this, AppPreference.LT_NAME));
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer,
                mToolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        selectNavMenu();
        setToolbarTitle();
        HomeFragment homeFragment = new HomeFragment();
        setFragment(homeFragment, HomeFragment.class.getSimpleName());
        TableReportSetting tbl_report_setting = new TableReportSetting(MainActivity.this);
        //s.insertSetting(list.get(i));
        final ArrayList<CampDetails1> camp_list = new ArrayList<>();
        tbl_report_setting.getQcdataList(camp_list);
        Log.e("no of settings", camp_list.size() + "");
        for (int i = 0; i < camp_list.size(); i++) {
            if (Integer.parseInt(camp_list.get(i).getReport_header()) == 1) {
                camp_list.get(i).setHeader(true);
            } else {
                camp_list.get(i).setHeader(false);
            }
            Heleprec.repostlistmap.put(camp_list.get(i).getName(), camp_list.get(i));
        }
        Heleprec.list=camp_list;
        if (camp_list.size()>0)
            Heleprec.update=true;
        if(!conectivity()) {
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
            alertDialog.setCancelable(true);
            alertDialog.setTitle("No Internet");
            alertDialog.setMessage("Please connect with internet");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.show();
        }
        else
        {
            ArrayList<CampDetails> campDetails = new ArrayList<>();
            com.android.volley.RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            StringRequest sr = new StringRequest(Request.Method.POST,ApiConstant.BASE_URL1+"getListApiCamp", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    TableReportSetting s1=new TableReportSetting(MainActivity.this);
                    //s.insertSetting(list.get(i));
                    s1.deleteTableContent();
                    ArrayList<CampDetails1> list=new ArrayList<>();
                    Log.d("response",response);
                    try {
                        JSONObject object =new JSONObject(response);
                        Gson gn=new Gson();
                        CampList1 clist=gn.fromJson(response,CampList1.class);
                        if (!clist.getStatus().equals("0")) {
                            //  Log.d("arr",clist.toString());
                            list = clist.getCampList();
                            for (int i = 0; i < list.size(); i++) {
                                TableReportSetting s = new TableReportSetting(MainActivity.this);
                                s.insertSetting(list.get(i));
                                if (Integer.parseInt(list.get(i).getReport_header()) == 1) {
                                    list.get(i).setHeader(true);
                                } else {
                                    list.get(i).setHeader(false);
                                }
                                Heleprec.repostlistmap.put(list.get(i).getName(), list.get(i));

                            }
                            Heleprec.list = list;
                            Heleprec.update = true;
                        }

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
                    Log.e("ltid is ",AppPreference.getString(MainActivity.this, AppPreference.USER_ID));
                    params.put("ltId", AppPreference.getString(MainActivity.this, AppPreference.USER_ID));

                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return Collections.emptyMap();
                }
            };
            queue.add(sr);
        }

        Splash.bluetoothIn = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                if (msg.what == Splash.handlerState) {
                    mBufferTestResponse.append((String) msg.obj);
                    int endOfLineIndex = mBufferTestResponse.indexOf(":##");
                    if (endOfLineIndex > 0) {
                        try {
                            String resultedString = mBufferTestResponse.toString();
                            String result = resultedString.replaceAll(".*##:,", "");
                            if(result.contains("C1")&&!result.contains("C2"))
                            {
                                qcdata=new QcData();
                                mBufferTestResponse.delete(0, mBufferTestResponse.length());
                                String testName, c1, LabId;
                                String parts[] = result.split(",");
                                if (parts[0].trim().equalsIgnoreCase("Ure(BUN)")) {
                                    testName = "Ure";
                                    //testUnit = parts[1].trim().split("\\(")[0];
                                    c1 = parts[2].trim().split("\\(")[0];
                                } else if (parts[0].trim().equalsIgnoreCase("CrK(MeGFR)")) {
                                    testName = "CrK";
                                    // testUnit = parts[1].trim().split("\\(")[0];
                                    c1 = parts[2].trim().split("\\(")[0];
                                } else if (parts[0].trim().contains("ACCUSTER")) {
                                    testName = parts[0].trim().split("ACCUSTER[\n\r]*")[1];
                                    //testUnit = parts[1].trim();
                                    c1 = parts[2].trim();
                                } else {
                                    testName = parts[0].trim();
                                    // testUnit = parts[1].trim();
                                    c1 = parts[2].trim();
                                }
                                LabId = parts[4].trim();
                                qcdata.setC1(c1);
                                isc1=true;

                                qcdata.setTest_id(testName);
                                Log.e("test name ",testName);
                                qcdata.setLab_id(LabId);
                                qcDatainfo("C1",c1);
                                mBufferTestResponse=new StringBuffer();

                            }
                            else {
                                if(result.contains("C2")&&!result.contains("C3"))
                                {
                                    Log.e("all data",result);
                                    Log.e("qcdata ","C2");
                                    mBufferTestResponse.delete(0, mBufferTestResponse.length());
                                    String testName, c2, LabId;
                                    String parts[] = result.split(",");
                                    if (parts[0].trim().equalsIgnoreCase("Ure(BUN)")) {
                                        testName = "Ure";
                                        //testUnit = parts[1].trim().split("\\(")[0];
                                        c2 = parts[2].trim().split("\\(")[0];
                                    } else if (parts[0].trim().equalsIgnoreCase("CrK(MeGFR)")) {
                                        testName = "CrK";
                                        // testUnit = parts[1].trim().split("\\(")[0];
                                        c2 = parts[2].trim().split("\\(")[0];
                                    } else if (parts[0].trim().contains("ACCUSTER")) {
                                        testName = parts[0].trim().split("ACCUSTER[\n\r]*")[1];
                                        //testUnit = parts[1].trim();
                                        c2 = parts[2].trim();
                                    } else {
                                        testName = parts[0].trim();
                                        // testUnit = parts[1].trim();
                                        c2 = parts[2].trim();
                                    }
                                    LabId = parts[4].trim();

                                    qcdata.setC2(c2);

                                    qcDatainfo("C2",c2);
                                    mBufferTestResponse=new StringBuffer();
                                    //qcdata.setLab_id(LabId);
                                }
                                else {
                                    if (result.contains("C3")&&!result.contains("L1"))
                                    {
                                        Log.e("qcdata ","C3");
                                        mBufferTestResponse.delete(0, mBufferTestResponse.length());
                                        String testName, c3, LabId;
                                        String parts[] = result.split(",");
                                        if (parts[0].trim().equalsIgnoreCase("Ure(BUN)")) {
                                            testName = "Ure";
                                            //testUnit = parts[1].trim().split("\\(")[0];
                                            c3 = parts[2].trim().split("\\(")[0];
                                        } else if (parts[0].trim().equalsIgnoreCase("CrK(MeGFR)")) {
                                            testName = "CrK";
                                            // testUnit = parts[1].trim().split("\\(")[0];
                                            c3 = parts[2].trim().split("\\(")[0];
                                        } else if (parts[0].trim().contains("ACCUSTER")) {
                                            testName = parts[0].trim().split("ACCUSTER[\n\r]*")[1];
                                            //testUnit = parts[1].trim();
                                            c3 = parts[2].trim();
                                        } else {
                                            testName = parts[0].trim();
                                            // testUnit = parts[1].trim();
                                            c3 = parts[2].trim();
                                        }
                                        LabId = parts[4].trim();
                                        qcdata.setC3(c3);
                                        testNamec3=testName;
                                        Calendar cal=Calendar.getInstance();
                                        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                                        format.format(cal.getTime());
                                        qcdata.setTest_id(testName);
                                        Log.e("test name ",testName);
                                        qcdata.setLab_id(LabId);
                                        qcdata.setTime(format.format(cal.getTime()));
                                        qcdata.setDate(DateTimeUtils.getCurrentDate(DateTimeUtils.OUTPUT_DATE_YYYYMMdd));
                                        TableQcData table=new TableQcData(MainActivity.this.getBaseContext());
                                        table.insertQcData(qcdata);
                                        Heleprec.isc3=true;
                                        qcDatainfo("C3",c3);
                                        mBufferTestResponse=new StringBuffer();
                                    }else {
                                        if(result.contains("L1")&&result.indexOf("L1")>result.indexOf("L2"))
                                        {

                                            // Log.e("qcdata ","L1");
                                            mBufferTestResponse.delete(0, mBufferTestResponse.length());
                                            String testName, l1, LabId;
                                            String parts[] = result.split(",");
                                            if (parts[0].trim().equalsIgnoreCase("Ure(BUN)")) {
                                                testName = "Ure";
                                                //testUnit = parts[1].trim().split("\\(")[0];
                                                l1 = parts[2].trim().split("\\(")[0];
                                            } else if (parts[0].trim().equalsIgnoreCase("CrK(MeGFR)")) {
                                                testName = "CrK";
                                                // testUnit = parts[1].trim().split("\\(")[0];
                                                l1 = parts[2].trim().split("\\(")[0];
                                            } else if (parts[0].trim().contains("ACCUSTER")) {
                                                testName = parts[0].trim().split("ACCUSTER[\n\r]*")[1];
                                                //testUnit = parts[1].trim();
                                                l1 = parts[2].trim();
                                            } else {
                                                testName = parts[0].trim();
                                                MainActivity.this.testName=testName;
                                                // testUnit = parts[1].trim();
                                                l1 = parts[2].trim();
                                            }
                                            LabId = parts[4].trim();
                                            if (testNamec3!=null)
                                            {
                                                if (!testNamec3.equals(testName)
                                                ) {
                                                    qcdata = new QcData();
                                                    Heleprec.isc3=false;
                                                    testNamec3=null;


                                                }
                                            }

                                            if(!Heleprec.isc3)
                                                qcdata = new QcData();
                                            if (!isc1) {
                                                qcdata = new QcData();
                                                Log.e("object create ","new ");
                                            }


                                            qcdata.setL1(l1);
                                            qcDatainfo("L1",l1);
                                            mBufferTestResponse=new StringBuffer();
                                        }
                                        else {
                                            if (result.contains("L1")&&result.indexOf("L1")<result.indexOf("L2"))
                                            {
                                                Log.e("current result is",result);
                                                // Log.e("qcdata ","L2");
                                                mBufferTestResponse.delete(0, mBufferTestResponse.length());
                                                String testName, l2, LabId;
                                                String parts[] = result.split(",");
                                                if (parts[0].trim().equalsIgnoreCase("Ure(BUN)")) {
                                                    testName = "Ure";
                                                    //testUnit = parts[1].trim().split("\\(")[0];
                                                    l2 = parts[2].trim().split("\\(")[0];
                                                } else if (parts[0].trim().equalsIgnoreCase("CrK(MeGFR)")) {
                                                    testName = "CrK";
                                                    // testUnit = parts[1].trim().split("\\(")[0];
                                                    l2 = parts[2].trim().split("\\(")[0];
                                                } else if (parts[0].trim().contains("ACCUSTER")) {
                                                    testName = parts[0].trim().split("ACCUSTER[\n\r]*")[1];
                                                    //testUnit = parts[1].trim();
                                                    l2 = parts[2].trim();
                                                } else {
                                                    testName = parts[0].trim();
                                                    // testUnit = parts[1].trim();
                                                    l2 = parts[2].trim();
                                                }
                                                LabId = parts[4].trim();

                                                qcdata.setL2(l2);
                                                Calendar cal=Calendar.getInstance();
                                                SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                                                format.format(cal.getTime());
                                                qcdata.setTest_id(testName);
                                                // Log.e("test name ",testName);
                                                qcdata.setLab_id(LabId);
                                                qcdata.setTime(format.format(cal.getTime()));
                                                qcDatainfo("L2",l2);
                                                qcdata.setDate(DateTimeUtils.getCurrentDate(DateTimeUtils.OUTPUT_DATE_YYYYMMdd));
                                                TableQcData table=new TableQcData(MainActivity.this.getBaseContext());
                                                // qcdata.setLtid(AppPreference.getString(MainActivity.this,AppPreference.USER_ID));
                                                // Log.e("all data",qcdata.getC1()+"  "+qcdata.getC2()+"  "+qcdata.getC3()+"  "+qcdata.getL1()+"  "+qcdata.getL2());
                                                table.insertQcDataofL(qcdata);
                                                qcDatainfo("L2",l2);
                                                Heleprec.isc3=false;
                                                isc1=false;
                                                //  mBufferTestResponse=new StringBuffer();

                                            }
                                            else {
                                                Log.e("qcdata "," not qcdata");

                                                mBufferTestResponse.delete(0, mBufferTestResponse.length());
                                                String testName, testUnit, testResult, LableId;
                                                String parts[] = result.split(",");
                                                if (parts[0].trim().equalsIgnoreCase("Ure(BUN)")) {
                                                    testName = "Ure";
                                                    testUnit = parts[1].trim().split("\\(")[0];
                                                    testResult = parts[2].trim().split("\\(")[0];
                                                } else if (parts[0].trim().equalsIgnoreCase("CrK(MeGFR)")) {
                                                    testName = "CrK";
                                                    testUnit = parts[1].trim().split("\\(")[0];
                                                    testResult = parts[2].trim().split("\\(")[0];
                                                } else if (parts[0].trim().contains("ACCUSTER")) {
                                                    testName = parts[0].trim().split("ACCUSTER[\n\r]*")[1];
                                                    testUnit = parts[1].trim();
                                                    testResult = parts[2].trim();
                                                } else {
                                                    testName = parts[0].trim();
                                                    testUnit = parts[1].trim();
                                                    testResult = parts[2].trim();
                                                }
                                                LableId = parts[3].trim();
                                                TablePatientTest tableTestResult = new TablePatientTest(MainActivity.this);

                                                tableTestResult.addTestResult(LableId, testName, testResult + " " + testUnit);

                                                Fragment fragmentLocal = getSupportFragmentManager().findFragmentByTag(PatientPreview.class.getSimpleName());
                                                if (fragmentLocal != null) {
                                                    ((PatientPreview) fragmentLocal).updateData(testName, testUnit, testResult, LableId);
                                                }
                                                //mBufferTestResponse=new StringBuffer();
                                            }
                                        }
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Splash.btSocket = null;
                            mBufferTestResponse.delete(0, mBufferTestResponse.length());
                        }
                    }
                }
                else {
                    Log.e("Result ",msg.what+"");
                }
            }
        };
        final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)

            {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                    // BluetoothDevice.ACTION_ACL_CONNECTED// LOG - Connected
                    Heleprec.con=true;

                } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                    Heleprec.con=false;

                    ToastUtils.showShortToastMessage(getApplicationContext(),"Bluetooth disconnected ");
                    // LOG - Disconnected
                }
            }
        };
        final Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Splash.btSocket == null ||!Splash.btSocket.isConnected()) {
                    ToastUtils.showShortToastMessage(getApplicationContext(),"Bluetooth disconnected ");
                    Heleprec.con=true;
                }
                if(!Heleprec.con)
                    ToastUtils.showShortToastMessage(getApplicationContext(),"Bluetooth disconnected ");
                getApplicationContext().registerReceiver(mReceiver,
                        new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED));

                getApplicationContext().registerReceiver(mReceiver,
                        new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED));
                h.postDelayed(this,5000);
            }
        },5000);
    }
    public void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    public void selectNavMenu() {
        try {
            navigationView.getMenu().getItem(navItemIndex).setChecked(true);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        sletectedItem = 0;
        drawer.closeDrawers();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() <= 1) {
            finish();
        } else {
            fragmentManager.popBackStackImmediate();
        }
        Fragment fragmentLocal = getSupportFragmentManager().findFragmentByTag(PatientRegistrationFragment.class.getSimpleName());
        if (fragmentLocal != null) {
            ((PatientRegistrationFragment) fragmentLocal).onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Check to see which item was being clicked and perform appropriate action
        drawer.closeDrawers();
        int id = item.getItemId();
        if (id != sletectedItem) {
            sletectedItem = id;
            if (item.getItemId() != R.id.nav_syn) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStackImmediate();
                }
            }

            switch (item.getItemId()) {
                //Replacing the main content with ContentFragment Which is our Inbox View;
                case R.id.nav_home:
                    navItemIndex = 0;
                    HomeFragment homeFragment = new HomeFragment();
                    setFragment(homeFragment, HomeFragment.class.getSimpleName());
                    break;
                case R.id.nav_create_camp:
                    navItemIndex = 3;
                    CreateCampFragment create__campFragment = new CreateCampFragment();
                    setFragment(create__campFragment, CreateCampFragment.class.getSimpleName());
                    break;
                case R.id.nav_register_patient:
                    navItemIndex = 1;
                    PatientRegistrationFragment patient_detail = new PatientRegistrationFragment();
                    setFragment(patient_detail, PatientRegistrationFragment.class.getSimpleName());

                    break;
                case R.id.qcdata:
                    navItemIndex=2;
                    QcDataFragment qcdata=new QcDataFragment();
                    setFragment(qcdata,QcDataFragment.class.getSimpleName());
                    break;
                case R.id.nav_package_list:
                    navItemIndex = 5;
                    PackageListFragment mPackageList = new PackageListFragment();
                    setFragment(mPackageList, PackageListFragment.class.getSimpleName());
                    break;
                case R.id.nav_user:
                    navItemIndex = 7;
                    Heleprec.avroverreport=false;
                    CampListFragment1 mAboutU = new CampListFragment1();
                    setFragment(mAboutU, CampListFragment1.class.getSimpleName());
                    break;
                case R.id.nav_about_us:
                    navItemIndex = 8;
                    AboutUs mAboutUs = new AboutUs();
                    setFragment(mAboutUs, AboutUs.class.getSimpleName());
                    break;

                case R.id.nav_help:
                    navItemIndex = 9;
                    HelpInfo mHelpInfo = new HelpInfo();
                    setFragment(mHelpInfo, HelpInfo.class.getSimpleName());
                    break;
                case R.id.nav_camp_list:
                    navItemIndex = 4;
                    CampsListFragment campsListFragment = new CampsListFragment();
                    setFragment(campsListFragment, CampsListFragment.class.getSimpleName());
                    break;
                case R.id.nav_syn:
                    navItemIndex = 6;
                    //  Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();
                    new SynDataToServer(this);
                    break;
                case R.id.Reportsetting:
                    navItemIndex=10;
                    ReportSettingFragment reportSettingFragment = new ReportSettingFragment();
                    setFragment(reportSettingFragment, ReportSettingFragment.class.getSimpleName());
                    break;
                case  R.id.Report:
                    navItemIndex=11;
                    CampListFragment1 mAboutU1 = new CampListFragment1();
                    setFragment(mAboutU1, CampListFragment1.class.getSimpleName());
                    Heleprec.avroverreport=true;
                    break;
                case R.id.nav_logout:
                    navItemIndex=12;
                    if (new TableCamp(this).getSynStatus() && new TablePatient(this).getallPatientSynStatus()
                            && new TablePatientTest(this).getPatientTestSynStatus()) {
                        AppPreference.clearData(this);
                        new TableCamp(this).deleteTableContent();
                        new TablePatient(this).deleteTableContent();
                        new TablePatientTest(this).deleteTableContent();
                        new TablePackageList(this).deleteTableContent();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(this, SynDataStatus.class));
                    }
                    break;
            }
            // set toolbar title
            setToolbarTitle();

        }
        return true;
    }

    @Override
    public void onDialogClose(HashMap<String, String> hashMap) {
        if (hashMap.get("show").equalsIgnoreCase("show")) {
            PatientRegistrationFragment patient_detail = new PatientRegistrationFragment();
            Bundle bundle = new Bundle();
            bundle.putString("campCode", hashMap.get("campCode"));
            patient_detail.setArguments(bundle);
            setFragment(patient_detail, PatientRegistrationFragment.class.getSimpleName());
        } else if (hashMap.get("create").equalsIgnoreCase("create")) {
            CreateCampFragment create__campFragment = new CreateCampFragment();
            setFragment(create__campFragment, CreateCampFragment.class.getSimpleName());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // just getting the ref of backstack fragment than reuse it
        Fragment fragmentLocal = getSupportFragmentManager().findFragmentByTag(PatientPreview.class.getSimpleName());
        if (fragmentLocal != null) {
            ((PatientPreview) fragmentLocal).onResultPictureDate(requestCode, resultCode, data);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int requestCodeCamera = 10100;
        int requestCodeReadStorage = 10111;
        int PERMISSIONS_REQUEST_CALL = 2000;
        if (requestCode == requestCodeCamera) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    BaseActivity.getPermissionStatus(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            requestCodeReadStorage);
                } else {
                    ImageCacheUtil.captureFromCamera(this, AppConstants.TEST_REPORT_IMAGE, reportFileName,
                            CAMERA_REQUEST);
                }
            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.

                ToastUtils.showShortToastMessage(this, "Please Allow App to access camera");
            }
        }

    }

    public  void getCdata(String c_string)
    {
        qcdata=new QcData();
        c_string= c_string.replace("##:,","");
        //  result.replace("##:","");
        getCdata(string_for_c_data);
        String g[]=c_string.split(",");

        qcdata.setTest_id(g[0]);
        qcdata.setC1(g[2]);
        qcdata.setC2(g[7]);
        qcdata.setC3(g[12]);
        getL1(string_for_l1);

    }
    public void getL1(String L1_string)
    {
        L1_string=L1_string.replace(".*##:","");
        String g[]=L1_string.split(",");
        qcdata.setL1(g[3].trim());
        Log.e("l1",qcdata.getL1());
        getL2(string_for_l2);

    }

    public void getL2(String l2_string)
    {
        l2_string=l2_string.replace(".*##:","");
        String g[]=l2_string.split(",");
        qcdata.setL2(g[3].trim());
        Log.e("l2",qcdata.getL2());
        TableQcData table=new TableQcData(MainActivity.this);
        qcdata.setStatus("0");
        //qcdata.setLtid(AppPreference.getString(this,AppPreference.USER_ID));
        table.insertQcData(qcdata);
        TableQcData data=new TableQcData(MainActivity.this);
        ArrayList<QcData> lis=new ArrayList<>();
        data.getQcdataList(lis);
        //  table.getQcdataList(lis);
        for(int i=0;i< lis.size();i++)
        {
            Log.e("sachin",lis.get(i).getC1()+"  "+lis.get(i).getC2()+"  "+lis.get(i).getC3()+" "+lis.get(i).getL1()+" "+lis.get(i).getL2()+"  "+lis.get(i).getTest_id());
        }
        qcdata=new QcData();
    }
    public  void qcDatainfo(String var,String value)

    {
        try
        {
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("  Qc Data  ");
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setMessage( "Parameter : " + var + "\n" + "" + "Result: " + value + " ");
            alertDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    generateNoteOnSD(mBufferTestResponse.toString());
                    alertDialog.dismiss();
                }
            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean conectivity() {

        ConnectivityManager connectivity = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("on resume call","onresume");
    }

    public  void getUpdatedCampListFromServer(){
        ltid= AppPreference.getString(this, AppPreference.USER_ID);
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("ltId", AppPreference.getString(this, AppPreference.USER_ID));
        if (NetworkUtil.checkInternetConnection(this)) {
            com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest sr = new StringRequest(Request.Method.POST, ApiConstant.BASE_URL1+"getListApiCamp", new Response.Listener<String>() {
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
                        //tableCamp.getCampList(campListModel);
                        ArrayList<CampDetails1> list1=campList.getCampList();
                        boolean falg=false;
                        //tableCamp.isTableEmpty();
                        //  setupRecycler();
                       // tableCamp.insertCampListFormServer1(list1);
                    }
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
        }
    }
}

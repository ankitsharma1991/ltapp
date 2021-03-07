package com.accusterltapp.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.accusterltapp.R;
import com.accusterltapp.app.LabTechnicianApplication;
import com.accusterltapp.model.CampSynResponse;
import com.accusterltapp.model.PatientSynResponse;
import com.accusterltapp.model.RegisterPatient;
import com.accusterltapp.model.RepidTestReslt;
import com.accusterltapp.model.ReportSync;
import com.accusterltapp.model.ReportSyncResponse;
import com.accusterltapp.model.ReposrtSyncModel;
import com.accusterltapp.model.TesTDetailsToSyn;
import com.accusterltapp.model.TestDetails;
import com.accusterltapp.model.UserLogin;
import com.accusterltapp.model.WidalDataSynModel;
import com.accusterltapp.model.WidalResultModel;
import com.accusterltapp.model.WidalTest;
import com.accusterltapp.service.ApiClinet;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.BaseNetworkRequest;
import com.accusterltapp.service.ImgService;
import com.accusterltapp.service.NetworkCallContext;
import com.accusterltapp.service.NetworkUtil;
import com.accusterltapp.service.ProgressBar;
import com.accusterltapp.table.TableCamp;
import com.accusterltapp.table.TablePackageList;
import com.accusterltapp.table.TablePatient;
import com.accusterltapp.table.TablePatientTest;
import com.accusterltapp.table.TableWidalTest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.model.CampDetails;
import com.base.model.SynModel;
import com.base.utility.ImageCacheUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.accusterltapp.service.ProgressBar.animation;
import static com.accusterltapp.service.ProgressBar.mProgressDialog;

public class SynDataToServer implements NetworkCallContext {
    private Context mContext;
    ProgressDialog progress;
    File file;
    int numberOfResType = 0;
    boolean progressMethodCall;
    List<String> numberOfPendingTeble;
    int jumpTime = 0;

    public SynDataToServer(Context context) {

        mContext = context;
        if (!NetworkUtil.checkInternetConnection(context))
        {
            Toast.makeText(context,"Please check your internet connection.",Toast.LENGTH_SHORT).show();
            return;
        }

        numberOfPendingTeble = new ArrayList<>();
        numberOfPendingTeble.clear();
        getPendingDataFromMultipleTable();
        synCampList();
        // synWidalTest();
//      synImagesToServer();
    }

    public void synWidalTest() {
        TableWidalTest tableWidalTest = new TableWidalTest(mContext);
        ArrayList<WidalTest> list = new ArrayList<>();
        tableWidalTest.getwidaltestList(list);
        WidalDataSynModel widalDataSynModel = new WidalDataSynModel();
        widalDataSynModel.setTestlist(list);
        Gson gson = new Gson();
        Log.e(" the json data", gson.toJson(widalDataSynModel));
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(mContext);
        JSONObject job = null;
        String ob = gson.toJson(widalDataSynModel);
        try {
            JSONObject jsono = new JSONObject(ob);
            job = jsono;

            Log.e("data ", job.toString());
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://qc.eaccuster.com/api/v2/addWidalTestReportApi", job,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            WidalResultModel widalResultModel;
                            Gson gson = new Gson();
                            Log.e("d update", response.toString() + "gd");
                            widalResultModel = gson.fromJson(response.toString(), WidalResultModel.class);
                            TableWidalTest tableWidalTest = new TableWidalTest(mContext);
                            if (widalResultModel.getStatus() == 1) {
                                tableWidalTest.updateSynStatus(widalResultModel.getPatients());
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String body;
                    Log.e("error", error.toString());
                }


            });

            queue.add(request);
        } catch (Exception e) {
            Log.e("exception ", e.getMessage() + " mes");
        }
    }

    public void synCampList() {
        TableCamp tableCamp = new TableCamp(mContext);
        ArrayList<CampDetails> campDetails = new ArrayList<>();
        tableCamp.getCampListToSyn(campDetails);
        SynModel campSynModel = new SynModel();
        Log.e("length camp ", campDetails.size() + "");
        if (!campDetails.isEmpty()) {
            download(null);
            campSynModel.setCamp_list(campDetails);
            Log.e("camp json", new Gson().toJson(campSynModel));
            HashMap<String, String> map = new HashMap<>();
            map.put("camp_sync_list", new Gson().toJson(campSynModel));
            BaseNetworkRequest<CampSynResponse> mRequest = new BaseNetworkRequest<>(mContext, ApiConstant.CAMP_SYN,
                    this, map, false, CampSynResponse.class);
            LabTechnicianApplication.getController(ApiConstant.BASE_URL).enqueueCall(mRequest);
            Log.v("list_data", new Gson().toJson(campSynModel));
        } else {
          //  download(null);
            synPatientList();
        }
    }

    public void synPatientList() {
        TablePatient tableCamp = new TablePatient(mContext);
        ArrayList<RegisterPatient> campDetails = new ArrayList<>();
        tableCamp.getallPatientToSync(campDetails);
        Gson g = new Gson();
        try {
            Log.e("patient data", new JSONObject(g.toJson(campDetails)).toString());
        } catch (Exception e) {

        }
        SynModel campSynModel = new SynModel();
        Log.e("length ", campDetails.size() + "");
        if (!campDetails.isEmpty()) {
            download(null);
            HashMap<String, String> map = new HashMap<>();
            campSynModel.setPatient_list(campDetails);
            Log.e("patient data", new Gson().toJson(campSynModel));
            map.put("patient_sync_list", new Gson().toJson(campSynModel));
            BaseNetworkRequest<PatientSynResponse> mRequest = new BaseNetworkRequest<>(mContext, ApiConstant.PATIENT_SYN,
                    this, map, false, PatientSynResponse.class);
            LabTechnicianApplication.getController(ApiConstant.BASE_URL).enqueueCall(mRequest);
            Log.e("happen ", "or not ");
        } else {
            download(null);
            synReportList();
        }
    }

    public void synReportList() {
        TablePatientTest tableCamp = new TablePatientTest(mContext);
        ArrayList<TesTDetailsToSyn> campDetails = new ArrayList<>();
        tableCamp.getallPatientTestToSyn(campDetails);
        SynModel campSynModel = new SynModel();
        if (!campDetails.isEmpty()) {
            final HashMap<String, String> map = new HashMap<>();
            campSynModel.setReport_list(campDetails);
            ArrayList<ReposrtSyncModel> list = new ArrayList<>();
            for (int i = 0; i < campSynModel.getReport_list().size(); i++) {
                ReposrtSyncModel reportsettingModel = new ReposrtSyncModel();
                reportsettingModel.setReport_camp_code(campSynModel.getReport_list().get(i).getReport_camp_code());
                reportsettingModel.setReport_created_time(campSynModel.getReport_list().get(i).getReport_created_time());
                reportsettingModel.setReport_id(campSynModel.getReport_list().get(i).getReport_id());
                reportsettingModel.setReport_lt_id(campSynModel.getReport_list().get(i).getReport_lt_id());
                reportsettingModel.setReport_patient_code(campSynModel.getReport_list().get(i).getReport_patient_code());
                Gson gson = new Gson();
                //  Log.e("data",subTestDetails.get(i).getReport_ref_img_name());
                ArrayList<String> imageName = gson.fromJson(campSynModel.getReport_list().get(i).getReport_ref_img_name(), ArrayList.class);
                reportsettingModel.setReport_ref_img_name(imageName);
                reportsettingModel.setReport_result(campSynModel.getReport_list().get(i).getReport_result());
                reportsettingModel.setReport_test_id(campSynModel.getReport_list().get(i).getReport_test_id());
                list.add(reportsettingModel);
            }
            final ReportSync reportSync = new ReportSync();
            reportSync.setReport_list(list);
            Log.e("report json r", new Gson().toJson(reportSync));
            ArrayList<TesTDetailsToSyn> subTestDetails = new ArrayList<>();
            for (int i = 0; i < campSynModel.getReport_list().size(); i++) {
                Log.e("name is ", campSynModel.getReport_list().get(i).getReport_ref_img_name());
                if (campSynModel.getReport_list().get(i).getReport_ref_img_name().equals("null")) {
                    Log.e("data ", "add");
                } else {
                    Log.e("data ", "add2");
                    subTestDetails.add(campSynModel.getReport_list().get(i));
                }
            }
            Log.e("size is ", subTestDetails.size() + "d");
            if (subTestDetails.size() > 0)
                synImagesToServer(subTestDetails);
            map.put("report_sync_list", new Gson().toJson(reportSync));
            BaseNetworkRequest<ReportSyncResponse> mRequest = new BaseNetworkRequest<>(mContext, ApiConstant.REPORT_SYN,
                    this, map, false, ReportSyncResponse.class);
            LabTechnicianApplication.getController(ApiConstant.BASE_URL).enqueueCall(mRequest);
//            Log.e("api hit","not");
        } else {
            //synImagesToServer();
        }
    }

    public void synPackageList() {
        TablePackageList tableCamp = new TablePackageList((Activity) mContext);
        ArrayList<TestDetails> campDetails = new ArrayList<>();
        tableCamp.getPackageListToSyn(campDetails);
        if (!campDetails.isEmpty()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("package_list", new Gson().toJson(campDetails));
            BaseNetworkRequest<UserLogin> mRequest = new BaseNetworkRequest<>((Activity) mContext, ApiConstant.PACKAGE_SYNC,
                    this, map, false, UserLogin.class);
            LabTechnicianApplication.getController(ApiConstant.BASE_URL).enqueueCall(mRequest);
        }
    }

    @Override
    public void handleResponse(Object response, String type) {
        Log.e("responce API ", type);

        numberOfResType++;
        Log.e("responce VALUES ", numberOfPendingTeble.size() + " No of Res TYpe=" + numberOfResType);
        //progress.dismiss();

        if (numberOfPendingTeble.size() == 1 && numberOfResType == 1) {

            progressMethodCall = false;
            mProgressDialog.setProgress(100);
            animation.setIntValues(100);
            animation.cancel();
            mProgressDialog.setMessage("Data Synced \n 100%");
            Log.e("responce API Per", "" + mProgressDialog.getProgress());
            if (mProgressDialog.getProgress() == 100) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Log.i("JO", "run");
                        ProgressBar.dismissDialog();
                    }
                }, 5000);
            }

        } else if (numberOfPendingTeble.size() == 2 && numberOfResType == 2) {
            progressMethodCall = false;
            mProgressDialog.setProgress(100);
            animation.setIntValues(100);
            animation.cancel();
            mProgressDialog.setMessage("Data Synced \n 100%");
            Log.e("responce API Per", "" + mProgressDialog.getProgress());
            if (mProgressDialog.getProgress() == 100) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Log.i("JO", "run");
                        ProgressBar.dismissDialog();
                    }
                }, 5000);
            }

        }
        else if (numberOfPendingTeble.size() == 3 && numberOfResType == 3) {
            progressMethodCall = false;
            mProgressDialog.setProgress(100);
            animation.setIntValues(100);
            animation.cancel();
            mProgressDialog.setMessage("Data Synced \n 100%");
            Log.e("responce API Per", "" + mProgressDialog.getProgress());
            if (mProgressDialog.getProgress() == 100) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Log.i("JO", "run");
                        ProgressBar.dismissDialog();
                    }
                }, 5000);
            }

        }
        else if (numberOfPendingTeble.size() == 4 && numberOfResType == 4) {
            progressMethodCall = false;
            mProgressDialog.setProgress(100);
            animation.setIntValues(100);
            animation.cancel();
            mProgressDialog.setMessage("Data Synced \n 100%");
            Log.e("responce API Per", "" + mProgressDialog.getProgress());
            if (mProgressDialog.getProgress() == 100) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Log.i("JO", "run");
                        ProgressBar.dismissDialog();
                    }
                }, 5000);
            }

        }
        if (type.equalsIgnoreCase(ApiConstant.CAMP_SYN)) {
            CampSynResponse campSynResponse = (CampSynResponse) response;
            if (campSynResponse.getStatus().equalsIgnoreCase(ApiConstant.SUCCESS)) {
                Log.v("list_data", new Gson().toJson(campSynResponse));
                TableCamp tableCamp = new TableCamp(mContext);
                if (campSynResponse.getSync_camp() != null && campSynResponse.getSync_camp().length > 0) {
                    tableCamp.updateSynStatus(campSynResponse.getSync_camp());
                }
                if (campSynResponse.getNonSync_camp() != null && campSynResponse.getNonSync_camp().length > 0) {
                    tableCamp.updateSynStatus(campSynResponse.getSync_camp());
                }
                synPatientList();
            }
        } else if (type.equalsIgnoreCase(ApiConstant.PATIENT_SYN)) {
            PatientSynResponse campSynResponse = (PatientSynResponse) response;
            if (campSynResponse.getStatus().equalsIgnoreCase(ApiConstant.SUCCESS)) {
                TablePatient tablePatient = new TablePatient(mContext);
                Log.e("patient sync", "patient" + campSynResponse.getSync_patient() + " and " + campSynResponse.getSync_patient().length);
                if (campSynResponse.getSync_patient() != null && campSynResponse.getSync_patient().length > 0) {

                    tablePatient.updateSynStatus(campSynResponse.getSync_patient());
                    Log.e("this one ", "patient");
                }
                if (campSynResponse.getNoneSync_patient() != null && campSynResponse.getNoneSync_patient().length > 0) {
                    tablePatient.updateSynStatus(campSynResponse.getNoneSync_patient());
                }
                synReportList();
            }
        } else if (type.equalsIgnoreCase(ApiConstant.REPORT_SYN)) {
            ReportSyncResponse campSynResponse = (ReportSyncResponse) response;
            Log.e("res", new Gson().toJson(campSynResponse));
            if (campSynResponse.getStatus().equalsIgnoreCase(ApiConstant.SUCCESS)) {
                TablePatientTest tablePatient = new TablePatientTest(mContext);
//                ArrayList<SubTestDetails> subTestDetails=new ArrayList<>();
//                for (int i=0;i<campSynResponse.getSync_report().length;i++)
//                    tablePatient.getallPatientTest(subTestDetails,campSynResponse.getSync_report()[i]);
//                synImagesToServer(subTestDetails);
                if (campSynResponse.getSync_report() != null && campSynResponse.getSync_report().length > 0) {
                    tablePatient.updateSynStatus(campSynResponse.getSync_report());
                }

                if (campSynResponse.getNoneSync_report() != null && campSynResponse.getNoneSync_report().length > 0) {
                    tablePatient.updateSynStatus(campSynResponse.getNoneSync_report());
                }
                //  synImagesToServer();
            }
        }
    }

    @Override
    public void handleServerError(Object response, String type) {
        if (type.equalsIgnoreCase(ApiConstant.CAMP_SYN)) {
            Log.v("list_data", "error");
            progressMethodCall = false;
            progress.dismiss();
        }
    }

    public void synImagesToServer(ArrayList<TesTDetailsToSyn> subTestDetails) {
        try {
           /* progress = new ProgressDialog(mContext);
            progress.setCancelable(false);
            progress.setMessage("Data saving ...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();*/
            Log.e(" ddata", subTestDetails.toString() + " and " + subTestDetails.size());
            final HashMap<String, File> map = new HashMap<>();
            ArrayList<String> arrayList = ImageCacheUtil.getFromSdcard();
            ArrayList<MultipartBody.Part> imageList = new ArrayList<>();
            ImgService service = ApiClinet.getClinet().create(ImgService.class);
            // map.put(file.getName(), file);
            for (int i = 0; i < subTestDetails.size(); i++) {

                Gson gson = new Gson();
                Log.e("data", subTestDetails.get(i).getReport_ref_img_name());
                ArrayList<String> imageName = gson.fromJson(subTestDetails.get(i).getReport_ref_img_name(), ArrayList.class);
                if (imageName != null)
                    for (int j = 0; j < imageName.size(); j++) {

                        file = new File("/storage/emulated/0/rapid/" + imageName.get(j).toString());
                        Bitmap bitmap;
                        try {
                            bitmap = BitmapFactory.decodeFile(file.getPath());
                            Log.e("name", bitmap.toString());
                        } catch (OutOfMemoryError error) {
                        }
                        final RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpg"), new Compressor(mContext).compressToFile(file));
                        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
                        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                        try {
                            Log.e("data ", " d" + body.body().contentLength() + " and  na" + name);
                        } catch (Exception e) {

                        }
                        Call<RepidTestReslt> userCall = service.postImage1(body, name);

                        // retrofit2.Call<okhttp3.ResponseBody> req = service.postImage1(body, name);
                        Log.e("file", " anem" + file.getName());
                        userCall.enqueue(new Callback<RepidTestReslt>() {
                            @Override
                            public void onResponse(Call<RepidTestReslt> call, Response<RepidTestReslt> response) {
                                // Do Something
                                //  progress.dismiss();
                                try {
                                    Log.e("work", response.body().getImageName() + response.body().getStatus());
                                    //  String name=response.body().getImageName().replace(".jpg",);
                                    // boolean b=map.get(response.body().getImageName()).delete();
                                    //  Toast.makeText(mContext,"file is "+b,Toast.LENGTH_LONG).show();
                                } catch (Exception e) {

                                }
//                        Log.e("happen sachin",response.body().getImageName());
//                        Toast.makeText(mContext,response.body().getImageName(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<RepidTestReslt> call, Throwable t) {
                                t.printStackTrace();
                                // progress.dismiss();
                                Log.e("fail", t.getLocalizedMessage());
                            }
                        });
                    }
            }
        } catch (Exception e) {
        }
    }//new RetrieveFeedTask().execute();


    public void download(View view) {
        //  final Handler handler = new Handler();


        progress = new ProgressDialog(mContext);
        progress.setMessage("Data Syncing");

        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
      /*  progress.setIndeterminate(true);
        progress.setMax(100);
        progress.setProgress(0);*/

        // BELOW LINE COMMENTED BY JS
        Log.d("already showing value=", "" + progress.isShowing());
        if (!progressMethodCall) {
            Log.d("animation END", "TESt2233");

            //  progress.dismiss();
            /// progress.show();
            ProgressBar.showDialog(mContext, "Data Syncing", true, true);

            progressMethodCall = true;

        } else {

        }

 /*       CircleProgressBar circleProgressBar = new CircleProgressBar(this);
        ((ViewGroup) findViewById(R.id.rlContainer)).addView(circleProgressBar);
        circleProgressBar.setProgress(65);
        circleProgressBar.setWidth(200);
        circleProgressBar.setWidthProgressBackground(25);
        circleProgressBar.setWidthProgressBarLine(25);
        circleProgressBar.setText("Loading...");
        circleProgressBar.setTextSize(30);
        circleProgressBar.setBackgroundColor(Color.LTGRAY);
        circleProgressBar.setProgressColor(Color.RED);
*/


    }

    private void getPendingDataFromMultipleTable() {

       // numberOfPendingTeble.clear();
        Log.d("LIST SEIZE=", "" + numberOfPendingTeble.size());
        //numberOfPendingTeble.clear();
        //11
        TableCamp tableCamp = new TableCamp(mContext);
        ArrayList<CampDetails> campDetails = new ArrayList<>();
        tableCamp.getCampListToSyn(campDetails);
        if (!campDetails.isEmpty()) {
            numberOfPendingTeble.add("1");
            Log.d("added=", "111 called");
        }
        //22

        TablePatient tableCamp2 = new TablePatient((Activity) mContext);
        ArrayList<RegisterPatient> registerPatients = new ArrayList<>();
        tableCamp2.getallPatientToSync(registerPatients);
        if (!registerPatients.isEmpty()) {
            numberOfPendingTeble.add("2");
            Log.d("added=", "222 called");
        }
        //33
        TablePatientTest tableCamp3 = new TablePatientTest((Activity) mContext);
        ArrayList<TesTDetailsToSyn> tesTDetailsToSyns = new ArrayList<>();
        tableCamp3.getallPatientTestToSyn(tesTDetailsToSyns);
        if (!tesTDetailsToSyns.isEmpty()) {
            numberOfPendingTeble.add("3");
            Log.d("added=", "333 called");
        }
        //44
        TablePackageList tableCamp4 = new TablePackageList((Activity) mContext);
        ArrayList<TestDetails> testDetails = new ArrayList<>();
        tableCamp4.getPackageListToSyn(testDetails);
        if (!testDetails.isEmpty()) {
            //numberOfPendingTeble.add("4");
            Log.d("added=", "4444 called");
        }
        Log.d("LIST SEIZE NEW=", "" + numberOfPendingTeble.size());
    }
}
//    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {
//
//
//        protected Void doInBackground(String... urls) {
//            ArrayList<String> arrayList = ImageCacheUtil.getFromSdcard();
//            ArrayList<MultipartBody.Part> imageList = new ArrayList<>();
//            if (!arrayList.isEmpty()) {
//                for (final String imagePath : arrayList) {
//                    ImgService service = ApiClinet.getClinet().create(ImgService.class);
//                    final File file=new File(imagePath);
//                    final RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
//                    RequestBody imgname = RequestBody.create(MediaType.parse("text/plain"), file.getName());
//                    Call<RepidTestReslt> userCall = service.postImage1(body,imgname);
//                     userCall.enqueue(new Callback<RepidTestReslt>() {
//                         @Override
//                         public void onResponse(Call<RepidTestReslt> call, Response<RepidTestReslt> response) {
//                             Log.e("happen sachin"+file.delete(),response.body().getImageName());
//
//
//
//
//                         }
//
//                         @Override
//                         public void onFailure(Call<RepidTestReslt> call, Throwable t) {
//                             Log.e("fail ","");
//
//                         }
//                     });
//                    FTPClient client = new FTPClient();
//FTPClient fileclinte=new FTPClient();
//
//                    SimpleFTP ftp = new SimpleFTP();
//
//
//
//                    // Connect to an FTP server on port 21.
//
//                    try {
//
//                        //ftp.connect("111.118.215.98", 21, "accuster@rentpe.com", "!Q2w3e4r5t");
//
//                        // Set binary mode.
//                       // ftp.bin();
//
//                        // Change to a new working directory on the FTP server.
//
//
//                        //ftp.cwd("path");
//
//                        // Upload some files.
//                        //ftp.stor(new File("your-file-path"));
//
//                        // Quit from the FTP server.
//                        //ftp.disconnect();
//                        Log.e("file name",imagePath);
//                       // fileclinte.connect("111.118.215.98",21);
//                        if (1>2) {
//
//                           fileclinte.login("accuster@rentpe.com","!Q2w3e4r5t");
//                            fileclinte.setFileType(FTP.BINARY_FILE_TYPE);
//                            fileclinte.enterLocalPassiveMode();
//                            File myImageFile=new File(imagePath);
//                            BufferedInputStream buffIn = new BufferedInputStream(
//                                    new FileInputStream(myImageFile));
//                            fileclinte.enterLocalPassiveMode();
//                            ProgressInputStream progressInput = new ProgressInputStream(
//                                    buffIn);
//                            boolean result = fileclinte.storeFile("sachin"+".png",
//                                    progressInput);
//                            //fileclinte.storeFile(imagePath);
//                           // fileclinte.stor(imagePath);
//
//
//                            //boolean b=fileclinte.storeFile("sachi.jpg",new FileInputStream(new File(imagePath)));
//Log.e("the value of boolean",result+"");
//
//                        }
//
//
//
//                        //fileclinte.
//                     //   client.connect("111.118.215.98", 21);
////                        client.login("accuster@rentpe.com", "!Q2w3e4r5t");
////                        client.setType(FTPClient.TYPE_BINARY);
//////                        client.changeDirectory("/upload/");
////
////
////                        client.upload(new File(imagePath), new FTPDataTransferListener() {
////                            @Override
////                            public void started() {
////Log.e("startid","");
////                            }
////
////                            @Override
////                            public void transferred(int i) {
////                                Log.e("transferred",i+"");
////
////
////                            }
////
////                            @Override
////                            public void completed() {
////Log.e("file transfer comp","");
////  boolean b=new File(imagePath).delete();
////  if (b)
////  {
////      Log.e("file deleted ","");
////  }
////  else Log.e("file not deleted","");
////                            }
////
////                            @Override
////                            public void aborted() {
////                                Log.e("aborted","not transfer");
////
////                            }
////
////                            @Override
////                            public void failed() {
////                                Log.e("failed","not transfer");
////
////                            }
////                        });
//                       // client.
//                        Log.e("images","work");
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Log.e("problem 1","1");
//
//
//                        try {
//                            //client.disconnect(true);
//                          //  fileclinte.disconnect();
//                        } catch (Exception e2) {
//Log.e("prolem 2","");
//                            e2.printStackTrace();
//                        }
//                    }
//                }
//            }
//            return null;
//        }
//
//        protected void onPostExecute() {
//        }

//}


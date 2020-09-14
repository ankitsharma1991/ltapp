package com.accusterltapp.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.accusterltapp.R;
import com.accusterltapp.activity.AddWidalTestResultActivity;
import com.accusterltapp.activity.ImageEditer;
import com.accusterltapp.activity.MainActivity;
import com.accusterltapp.adapter.PatientTestListAdapter;
import com.accusterltapp.adapter.SliderAddapterc;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.model.ImageName;
import com.accusterltapp.model.RegisterPatient;
import com.accusterltapp.model.SubTestDetails;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.ImageLodingService;
import com.accusterltapp.table.TablePatientTest;
import com.base.fragment.BaseFragment;
import com.base.listener.RecyclerViewListener;
import com.base.utility.DateTimeUtils;
import com.base.utility.PdfGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import ss.com.bannerslider.Slider;


public class PatientPreview extends BaseFragment implements RecyclerViewListener, View.OnClickListener {
    private custom.AvenirRomanTextView camp_name, patient_id, label_id, P_process_date, P_time;
    private RecyclerView recyclerView_test_list;

    File destination;
    String imgPath;
    Bitmap bitmap;
    Uri outputFileUri;
    Uri imageUri;
    Slider slider;
    public final static int MY_PERMISSIONS_REQUEST = 10;
    int count = 0;
    int image_count = 1;
    Button tv_add, tv_done,tv_Edit;
    ArrayList<String> image_info;
    Dialog dialog;
    private ArrayList<SubTestDetails> mSubTestDetails = new ArrayList<>();
    private PatientTestListAdapter adapter;
    private TablePatientTest patientTest;
    private final int requestCodeCamera = 10100;
    private static final int CAMERA_REQUEST = 1888;
    private String testName = "", testResult = "", reportFileName = "";
    private RegisterPatient mRegisterPatient;
    boolean flagselectall = false;
    ProgressDialog pd;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    SharedPreferences pref;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    Button fb, all, pdfDownload, tv_widal;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private SharedPreferences permissionStatus;
    String file_name;

    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_preview, container, false);
        camp_name = view.findViewById(R.id.camp_name);
        patient_id = view.findViewById(R.id.patient_id);
        all = (Button) view.findViewById(R.id.all);
        tv_widal = view.findViewById(R.id.tv_widal);

        tv_widal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddWidalTestResultActivity.class);

                intent.putExtra("patient_id", getArguments().getString("patient_id"));
                startActivity(intent);
            }
        });


        permissionStatus = PatientPreview.this.getActivity().getSharedPreferences("permissionStatus", PatientPreview.this.getActivity().MODE_PRIVATE);

        try {
//            String imageUrl = ApiConstant.imageurl + Heleprec.repostlistmap.get(Heleprec.current_camp_name).getCampLogo();
//            Volley.newRequestQueue(PatientPreview.this.getActivity()).add(new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
//                @Override
//                public void onResponse(Bitmap bitmap) {
//                    // Do something with loaded bitmap...
//                    Log.e("logo", "logo");
//                    Heleprec.logo = bitmap;
//                }
//            },
//                    1024, 1024, null, null));
            Glide.with(getContext())
                    .load(ApiConstant.imageurl + Heleprec.repostlistmap.get(Heleprec.current_camp_name).getCampLogo())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>(1024, 1024) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            //  image.setImageBitmap(resource); // Possibly runOnUiThread()
                            Log.e("logo", "logo");
                            Heleprec.logo = resource;
                        }
                    });
        } catch (Exception e) {

        }
        label_id = view.findViewById(R.id.label_id);
        pdfDownload = view.findViewById(R.id.pdfDownload);
        pdfDownload.setOnClickListener(this);
        P_process_date = view.findViewById(R.id.P_process_date);
        P_time = view.findViewById(R.id.P_time);
        fb = view.findViewById(R.id.fab);


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TablePatientTest(getActivity()).deletTest(Heleprec.testlist);
                //SetData();
                //   mRegisterPatient = getArguments().getParcelable("patientDetail");

                patientTest = new TablePatientTest(getActivity());
                ArrayList<SubTestDetails> ptestlist = new ArrayList<>();

                patientTest.getallPatientTest(ptestlist, getArguments().getString("patient_id"));

                adapter = new PatientTestListAdapter(PatientPreview.this.getContext(), ptestlist, PatientPreview.this);
                recyclerView_test_list.setAdapter(adapter);
                Log.e("delet three ", "del");
                AppPreference.setBoolean(getActivity(), AppPreference.TEST_TABLE_UPDATE, true);
            }
        });
        recyclerView_test_list = view.findViewById(R.id.recyclerView_test_list);
        recyclerView_test_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        SetData();
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patientTest = new TablePatientTest(getActivity());
                mSubTestDetails = new ArrayList<>();

                patientTest.getallPatientTest(mSubTestDetails, getArguments().getString("patient_id"));
                if (!flagselectall) {
                    for (int i = 0; i < mSubTestDetails.size(); i++) {

                        mSubTestDetails.get(i).setSelected(true);
                    }
                    Heleprec.testlist = mSubTestDetails;
                    adapter = new PatientTestListAdapter(PatientPreview.this.getContext(), mSubTestDetails, PatientPreview.this);
                    recyclerView_test_list.setAdapter(adapter);
                    AppPreference.setBoolean(getActivity(), AppPreference.TEST_TABLE_UPDATE, true);
                    Log.e("delet one ", "del");

                    flagselectall = true;
                } else {
                    Log.e("delet two ", "del");

                    for (int i = 0; i < mSubTestDetails.size(); i++) {

                        mSubTestDetails.get(i).setSelected(false);
                    }
                    Heleprec.testlist.clear();
                    adapter = new PatientTestListAdapter(PatientPreview.this.getContext(), mSubTestDetails, PatientPreview.this);
                    recyclerView_test_list.setAdapter(adapter);
                    AppPreference.setBoolean(getActivity(), AppPreference.TEST_TABLE_UPDATE, true);

                    flagselectall = false;
                }


                // new TablePatientTest(getActivity()).deletTest(Heleprec.testlist);

                //SetData();
                //mRegisterPatient = getArguments().getParcelable("patientDetail");

            }
        });


        return view;
    }

    public void SetData() {
        camp_name.setText(getArguments().getString("camp_name"));
        patient_id.setText(getArguments().getString("patient_id"));
        label_id.setText(getArguments().getString("label_id"));
        P_process_date.setText(getArguments().getString("process_date"));
        P_time.setText(DateTimeUtils.changeDateFormat(getArguments().getString("time"),
                DateTimeUtils.INPUT_DATE_YYYY_MM_DD_TS, DateTimeUtils.TIME_FORMAT));

        mRegisterPatient = getArguments().getParcelable("patientDetail");

        patientTest = new TablePatientTest(getActivity());

        patientTest.getallPatientTest(mSubTestDetails, getArguments().getString("patient_id"));

        adapter = new PatientTestListAdapter(getActivity(), mSubTestDetails, this);
        recyclerView_test_list.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onItemClickListener(View view, int position, HashMap<String, String> hashMap) {

        testName = hashMap.get("TestName");
        testResult = hashMap.get("result");
        reportFileName = hashMap.get("TestName").toLowerCase() + getArguments().getString("patient_id").toLowerCase() + String.valueOf(System.currentTimeMillis());
//        Intent intent = new Intent();

//        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        getActivity().startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
        ((MainActivity) getActivity()).reportFileName = reportFileName;

        Image image;
        final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setTitle("Select Option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    dialog.dismiss();
                    try {
                        if (checkPermission()) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            count = 0;
                            file_name = reportFileName + String.valueOf(System.currentTimeMillis()) + "cam";
                            Log.e("file _name", file_name);
                            outputFileUri = Uri.fromFile(getFile(file_name));

                            imgPath = destination.getAbsolutePath();
                            image_info = new ArrayList<>();
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            startActivityForResult(intent, 1);
                        }

                    } catch (Exception e) {
                        Log.e("exception", e.getLocalizedMessage() + "mes");
                    }
                    // startActivityForResult(Intent.createChooser(intent, , 1);

                } else if (options[item].equals("Choose From Gallery")) {
                    dialog.dismiss();
                    if (checkPermission()) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
                    }


                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        String data = hashMap.get("image_permission");
        Log.e("data", data);
        if (hashMap.get("image_permission").equals("0")) {
            TablePatientTest patientTest = new TablePatientTest(getActivity());
            patientTest.addTestResultByName(getArguments().getString("patient_id"), testName,
                    testResult, null);
            Log.e("image name", reportFileName);
            mSubTestDetails.clear();

//            ImageView b=dialog.findViewById(R.id.yes);
//            b.setOnClickListener(new View.OnClickListener() { addTestResult
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//                }
//            });
            patientTest.getallPatientTest(mSubTestDetails, getArguments().getString("patient_id"));
            adapter.notifyDataSetChanged();
            testName = "";
            testResult = "";
            reportFileName = "";
        } else
            builder.show();


//        new ImagePicker.Builder(getActivity())
//                .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
//                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
//                .directory(ImagePicker.Directory.DEFAULT)
//                .extension(ImagePicker.Extension.JPG)
//                .scale(600, 600)
//                .allowMultipleImages(true)
//                .enableDebuggingMode(true)
//                .build();


//        File imageFile = ImageCacheUtil.getProfileFile(AppConstants.TEST_REPORT_IMAGE, "samo");
//        if (imageFile.exists()) {
//            imageFile.delete();
//        }
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            BaseActivity.getPermissionStatus(getActivity(), Manifest.permission.CAMERA,
//                    requestCodeCamera);
//        } else {
//            ImageCacheUtil.captureFromCamera(getActivity(), AppConstants.TEST_REPORT_IMAGE,
//                    reportFileName, CAMERA_REQUEST);
//        }
    }

    public void onResultPictureDate(int requestCode, int resultCode, Intent data) {
        Log.e("request code ", requestCode + " code");
        if (requestCode != 2) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //  StoreImage(getContext(),imageUri,destination);
                    image_info.add(file_name + ".jpg");
                    if (count == 0) {
                      /*  Intent intent=new Intent(getContext(), ImageEditer.class);
                        intent.putExtra("uri",image_info.get(0));
                        startActivity(intent);*/

                        dialog = new Dialog(getContext(), android.R.style.Theme_NoTitleBar_Fullscreen);
                        dialog.setContentView(R.layout.rapid_image_camera_dialog);
                        slider = dialog.findViewById(R.id.banner_slider1);
                        Slider.init(new ImageLodingService(getContext()));
                        slider.setAdapter(new SliderAddapterc(getContext(), image_info));
                        dialog.show();
                        tv_add = dialog.findViewById(R.id.tv_add);
                        tv_done = dialog.findViewById(R.id.tv_done);
                        tv_Edit=dialog.findViewById(R.id.tv_Edit);
                        tv_add.setOnClickListener(this);
                        tv_done.setOnClickListener(this);
                        tv_Edit.setOnClickListener(this);

                    } else {
                        Slider.init(new ImageLodingService(getContext()));
                        slider.setAdapter(new SliderAddapterc(getContext(), image_info));
                    }
                    // Log.e("the bitmp", bitmap.toString() + " da" + imgPath);
                } catch (Exception e) {
                    Log.e("exception e", e.getLocalizedMessage() + " mes");

                }
            } else {
                Log.e("path not found", "p");
                imgPath = null;
                bitmap = null;

            }
        } else {
            try {
                if (resultCode == Activity.RESULT_OK) {
                    ArrayList<String> imageNames = new ArrayList<>();
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        Log.e("conte data ", count + " no");//evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                        for (int i = 0; i < count; i++) {
                            imageUri = data.getClipData().getItemAt(i).getUri();
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                            // desination = new File(getRealPathFromURI(imageUri).toString());
                            destination = new File(imageUri.getPath());
                            //Log.e("name",destination.getAbsolutePath()+" and name"+destination.getName()+" and "+bitmap );
                            imgPath = createDirectoryAndSaveFile(bitmap, reportFileName + i).getAbsolutePath();
                            imageNames.add(destination.getName());
                            Log.e("gvefwjhk", "kjbh,jhfsfd" + imageUri + " and " + bitmap + "name " + destination.getName());
                        }
                        String image_details = new Gson().toJson(imageNames);
                        Log.e("data ", image_details + " d");
                        onCaptureImage(image_details);
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                    } else if (data.getData() != null) {
                        Log.e("data null", "data");
                        String imagePath = data.getData().getPath();
                        imageUri = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                        // destination = new File(getRealPathFromURI(imageUri).toString());
                        destination = new File(imageUri.getPath());
                        //Log.e("name",destination.getAbsolutePath()+" and name"+destination.getName()+" and "+bitmap );
                        imgPath = createDirectoryAndSaveFile(bitmap, reportFileName).getAbsolutePath();

                        imageNames.add(destination.getName());
                        String image_details = new Gson().toJson(imageNames);
                        Log.e("data ", image_details + " d");
                        onCaptureImage(image_details);
                        //do something with the image (save it to some directory or whatever you need to do with it here)

                    }
                }
            } catch (Exception e) {
                Log.e("exception e", "e1");
            }
        }

    }

    public void updateData(String testName, String testUnit, String testResult, String LableId) {
        try {
            final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Apply Successfully");
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setMessage("Token Number : " + LableId + "\n" + "TestName : " + testName + "\n" + "" + "TestResult: " + testResult + " " + testUnit);
            alertDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                                    generateNoteOnSD(mBufferTestResponse.toString());
                    alertDialog.dismiss();
                    try {
                        mSubTestDetails.clear();
                        patientTest.getallPatientTest(mSubTestDetails, getArguments().getString("patient_id"));
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCaptureImage(String imagePath) {
        Log.e("imagePath", imagePath);
        if (!imagePath.equalsIgnoreCase("")) {

          /* Intent intent=new Intent(getContext(),ImageEditer.class);
            intent.putExtra("uri",imagePath);
            startActivity(intent);*/
           TablePatientTest patientTest = new TablePatientTest(getActivity());
            patientTest.addTestResultByName(getArguments().getString("patient_id"), testName, testResult, imagePath);
            Log.e("image name", reportFileName);
            mSubTestDetails.clear();


            patientTest.getallPatientTest(mSubTestDetails, getArguments().getString("patient_id"));
            adapter.notifyDataSetChanged();
            testName = "";
            testResult = "";
            reportFileName = "";
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_add) {
            dialog.dismiss();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            outputFileUri = Uri.fromFile(getFile(reportFileName + image_count));
            image_count++;
            imgPath = destination.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            startActivityForResult(intent, 1);

        } else if (v.getId() == R.id.tv_done) {
            dialog.dismiss();
            String image_details = new Gson().toJson(image_info);
            Log.e("imageinfo", image_details);
            onCaptureImage(image_details);
            image_info = null;


        } else if(v.getId()==R.id.tv_Edit)
        {
            try {

                UCrop.of(Uri.parse(image_info.get(0)), Uri.parse(imgPath))
                        .withAspectRatio(16, 9)
                        .withMaxResultSize(400, 600)
                        .start(PatientPreview.this.getContext(),PatientPreview.this);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        else {
            pd = new ProgressDialog(PatientPreview.this.getActivity());
            pd.setMessage("loading");
            pd.setCancelable(false);

            boolean installed = appInstalledOrNot("com.adobe.reader");
            if (installed) {
                Log.e("install status is", permissionStatus.getBoolean(permissionsRequired[2], false) + "");
                int t = ContextCompat.checkSelfPermission(PatientPreview.this.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                int f1 = ContextCompat.checkSelfPermission(PatientPreview.this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (t == PackageManager.PERMISSION_GRANTED && f1 == PackageManager.PERMISSION_GRANTED) {
                    pd.show();

                    new RetrieveFeedTask().execute(null, null);
                } else {
                    setPermisio();
                }
            } else {
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
            PdfGenerator pdfGenerator = new PdfGenerator(getActivity());

            pdfGenerator.generatePDF(mRegisterPatient);
            pd.dismiss();
            //  pd.dismiss();
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pd != null)
            pd.dismiss();
    }

    public void setPermisio() {
        try {
            //  permissionStatus = PatientPreview.this.getActivity().getSharedPreferences("permissionStatus", PatientPreview.this.getActivity().MODE_PRIVATE);
            if (ActivityCompat.checkSelfPermission(PatientPreview.this.getActivity().getApplicationContext(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(PatientPreview.this.getActivity().getApplicationContext(), permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(PatientPreview.this.getActivity().getApplicationContext(), permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(PatientPreview.this.getActivity().getApplicationContext(), permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(PatientPreview.this.getActivity().getApplicationContext(), permissionsRequired[4]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(PatientPreview.this.getActivity(), permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(PatientPreview.this.getActivity(), permissionsRequired[1])
                        || ActivityCompat.shouldShowRequestPermissionRationale(PatientPreview.this.getActivity(), permissionsRequired[2])
                        || ActivityCompat.shouldShowRequestPermissionRationale(PatientPreview.this.getActivity(), permissionsRequired[3])
                        || ActivityCompat.shouldShowRequestPermissionRationale(PatientPreview.this.getActivity(), permissionsRequired[4])) {
                    //Show Information about why you need the permission
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PatientPreview.this.getContext());
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Camera and Location permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(PatientPreview.this.getActivity(), permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
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
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PatientPreview.this.getContext());
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Camera and Location permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            // sentToSettings = true;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", PatientPreview.this.getActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            Toast.makeText(PatientPreview.this.getActivity().getApplicationContext(), "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
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
                    ActivityCompat.requestPermissions(PatientPreview.this.getActivity(), permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                }

                ///  UtilityMethod.showShortToastMessage(getContext(), "Permissions Required");

                SharedPreferences.Editor editor = permissionStatus.edit();
                editor.putBoolean(permissionsRequired[0], true);
                editor.commit();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Log.e("on result ", "data");
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<ImageName> imageNames = new ArrayList<>();
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    Log.e("conte data ", count + " no");//evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    imageUri = data.getClipData().getItemAt(0).getUri();
                    for (int i = 0; i < count; i++) {
                        imageUri = data.getClipData().getItemAt(i).getUri();
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        Log.e("Activity", "Pick from Gallery::>>> ");
                        destination = new File(getRealPathFromURI(imageUri).toString());
                        imgPath = createDirectoryAndSaveFile(bitmap, destination.getName()).getAbsolutePath();
                        ImageName imageName = new ImageName();
                        imageName.setName(destination.getName().toString());
                        //  imageName.setPath(imgPath.toString());
                        imageNames.add(imageName);

                    }

                    Intent intent=new Intent(PatientPreview.this.getActivity(), ImageEditer.class);
                    intent.putExtra("",imageUri);
                    startActivity(intent);
                    String image_details = new Gson().toJson(imageNames);
                    onCaptureImage(image_details);


                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            } else if (data.getData() != null) {
                Log.e("data null", "data");
                String imagePath = data.getData().getPath();
                //do something with the image (save it to some directory or whatever you need to do with it here)

            }
        } catch (Exception e) {

        }
    }

    private File createDirectoryAndSaveFile(Bitmap imgSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/" + "rapid");

        if (!direct.exists()) {
            File imageDirectory = new File(Environment.getExternalStorageDirectory() + "/" + "rapid" + "/");
            imageDirectory.mkdirs();
        }
        destination = new File(new File(Environment.getExternalStorageDirectory() + "/" + "rapid" + "/"), fileName + ".jpg");
        if (destination.exists()) {
            destination.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(destination);
            imgSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("destinaion", destination.getAbsolutePath() + " de");
        return destination;
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public File getFile(String file_name) {
        this.file_name = file_name;
        File direct = new File(Environment.getExternalStorageDirectory() + "/" + "rapid");

        if (!direct.exists()) {
            File imageDirectory = new File(Environment.getExternalStorageDirectory() + "/" + "rapid" + "/");
            imageDirectory.mkdirs();
        }
        destination = new File(new File(Environment.getExternalStorageDirectory() + "/" + "rapid" + "/"), file_name + ".jpg");
        if (destination.exists()) {
            destination.delete();
        }
        return destination;
    }

    public static void StoreImage(Context mContext, Uri imgUri, File imageDir) {
        Bitmap bm = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;

            bm = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(imgUri), null, options);
            FileOutputStream out = new FileOutputStream(imageDir);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            bm.recycle();
        } catch (Exception e) {
            Log.e("exception ", e.getLocalizedMessage() + "ed");
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e(" code ", requestCode + "h");

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //  getLocationname();

                } else {

                    // numberGhumao.setString("current_location",new Gson().toJson(current_location));
                    //code for deny
                }
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getActivity(), Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale((Activity) getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale((Activity) getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(getContext());
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Camera and Storage  permissions are necessary !!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{
                                    Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);

                            // requestPermissions((Activity)getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    });
                    android.app.AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    requestPermissions(new String[]{
                            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);


                    // ActivityCompat.requestPermissions((Activity)getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

}


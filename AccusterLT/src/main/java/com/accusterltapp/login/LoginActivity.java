package com.accusterltapp.login;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.accusterltapp.activity.DeviceListActivity;
import com.accusterltapp.activity.MainActivity;
import com.accusterltapp.app.LabTechnicianApplication;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.model.ReportsettingModel;
import com.accusterltapp.model.UserLogin;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.BaseNetworkRequest;
import com.accusterltapp.service.NetworkCallContext;
import com.accusterltapp.table.TableCamp;
import com.accusterltapp.table.TableGenericPacakge;
import com.accusterltapp.table.TablePackageList;
import com.base.model.CampDetails;
import com.base.utility.Const;
import com.base.utility.ToastUtils;
import com.google.gson.Gson;
import com.accusterltapp.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by appideas-user19 on 4/7/17.
 */

public class LoginActivity extends AppCompatActivity implements NetworkCallContext {

    private Button login;
    private EditText et_email, et_password;
    private String emailPattern = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    private String email;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    SharedPreferences pref;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private SharedPreferences permissionStatus;
//    private TextView forgot_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            permissionStatus = this.getSharedPreferences("permissionStatus", this.MODE_PRIVATE);
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[4]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[1])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[2])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[3])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[4])) {
                    //Show Information about why you need the permission
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Camera and Location permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(LoginActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
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
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Camera and Location permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            // sentToSettings = true;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", LoginActivity.this.getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            Toast.makeText(getApplicationContext(), "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
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
                    ActivityCompat.requestPermissions(this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                }

                ///  UtilityMethod.showShortToastMessage(getContext(), "Permissions Required");

                SharedPreferences.Editor editor = permissionStatus.edit();
                editor.putBoolean(permissionsRequired[0], true);
                editor.commit();
            }
        }
        catch (Exception e)
        {


        }
        login = findViewById(R.id.bt_sign);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString().trim();
                Submited();
            }
        });

        if (AppPreference.getBoolean(this, AppPreference.IS_LOGIN)) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    private void Submited() {

        if (et_email.getText().toString().trim().length() == 0) {
            ToastUtils.showShortToastMessage(getApplicationContext(), "Username is not entered");
        }
        if (et_password.getText().toString().trim().length() == 0) {
            ToastUtils.showShortToastMessage(getApplicationContext(), "Password is not entered");
        } else {
            if (email.matches(emailPattern)) {
                login();
            } else {
                ToastUtils.showShortToastMessage(getApplicationContext(), "Invalid email address");
            }

        }

    }

    private void login() {

        HashMap<String, String> map = new HashMap<>();
        map.put(Const.Params.EMAIL, et_email.getText().toString());
        map.put(Const.Params.PASSWORD, et_password.getText().toString());
        map.put("roleId", "1");
        BaseNetworkRequest<UserLogin> mRequest = new BaseNetworkRequest<>(this, ApiConstant.USER_LOGIN,
                this, map, true, UserLogin.class);
        LabTechnicianApplication.getController(ApiConstant.BASE_URL).enqueueCall(mRequest);
    }

    @Override
    public void handleResponse(Object response, String type) {
        UserLogin mLogin = (UserLogin) response;
        if (mLogin.getStatus().equalsIgnoreCase(ApiConstant.SUCCESS)) {
            AppPreference.setString(this, AppPreference.USER_ID, mLogin.getUser_detial().getUserregistration_id());
            AppPreference.setString(this, AppPreference.ORGANISATION_ID, mLogin.getUser_detial().getOrganization_id());
            AppPreference.setString(this, AppPreference.ORGANISATION_NAME, mLogin.getUser_detial().getOrganization_name());
            AppPreference.setString(this, AppPreference.PATHOLOGIST_LIST, new Gson().toJson(mLogin.getPathlogist_list()));
            AppPreference.setString(this, AppPreference.LT_NAME,
                    mLogin.getUser_detial().getUserregistration_complete_name());
            AppPreference.setString(this, AppPreference.E_MAIL,
                    mLogin.getUser_detial().getUserregistration_email_address());

            AppPreference.setBoolean(this, AppPreference.IS_LOGIN, true);

            TableGenericPacakge packageList = new TableGenericPacakge(this);
            packageList.insertPackageList(mLogin.getTest_list());
           // String d=mLogin.getTest_list().get(0).getTest_list().get(0).getTest_interpretation();
           // Log.d("etr ",d);

            TablePackageList patientTest = new TablePackageList(this);
            patientTest.insertPackageList(mLogin.getPackage_list());

            new TableCamp(this).insertCampListFormServer(mLogin.getCamp_list());
            ArrayList<CampDetails> list = new ArrayList<>();

            new TableCamp(this).getCampList(list);
            ArrayList<ReportsettingModel> list2=new ArrayList<>();
//
//            for (int i = 0; i < list.size(); i++) {
//                try {
//                    ReportsettingModel rs = new ReportsettingModel();
//                    rs.setAddress(Heleprec.Adrresscamp);
//                    rs.setCamp_name(list.get(i).getCamp_name());
//                    rs.setLogo_enable("yes");
//                    rs.setHeader(true);
//                    rs.setFooter(true);
//                    ReportSetting rst = new ReportSetting(this);
//                   // rst.insertSingleSetting(rs);
//                    list2.add(rs);
//                  //  Heleprec.list.add(rs);
//                   // Heleprec.repostlistmap.put(list.get(i).getCamp_name(),rs);
//
//                } catch (Exception e) {
//                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//            FileOutputStream outStream = null;
//            try {
//                File f = new File(Environment.getExternalStorageDirectory(), "/data.pdf");
//                outStream = new FileOutputStream(f);
//                ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);
//
//
//                objectOutStream.writeObject(list2);
//                objectOutStream.close();
//            } catch (FileNotFoundException e1) {
//                Log.e("error1",e1.getMessage());
//            } catch (IOException e1) {
//                Log.e("error2",e1.getMessage());
//            }
//
//



            Intent i = new Intent(getApplicationContext(), DeviceListActivity.class);
            startActivity(i);
            finish();
        } else {
            ToastUtils.showShortToastMessage(this, getString(R.string.server_error));
        }
    }

    @Override
    public void handleServerError(Object response, String type) {
        ToastUtils.showShortToastMessage(this, getString(R.string.server_error));
    }
}

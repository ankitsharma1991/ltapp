package com.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import com.accusterltapp.R;
import com.accusterltapp.activity.Splash;
import com.accusterltapp.service.ProgressBar;
import com.base.utility.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;


public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        // set color to action bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Resource
                        .getColor(this, R.color.colorPrimary));
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public void generateNoteOnSD(String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "ResultData");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.append("\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
    }

    /* Initialize all component here */

    public void setViewData() {
    }

    //    set data in view after all process like service run

    //    setFragment in container and add to stack
    public void setFragment(Fragment fragment, String fragmentNameTag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
        fragmentTransaction.replace(R.id.frame, fragment, fragmentNameTag);
        fragmentTransaction.addToBackStack(fragmentNameTag);
        fragmentTransaction.commit();
    }


    public void showProgressDialog(String message) {
        ProgressBar.showDialog(this, message, true, false);
    }

    public void hideProgressDialog() {
        ProgressBar.dismissDialog();
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public void setActionBar(int toolBarId) {

        Toolbar toolbar = findViewById(toolBarId);
        setSupportActionBar(toolbar);
        //set home menu button
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    public void setActionBarWithoutIcon(int toolBarId) {

        Toolbar toolbar = findViewById(toolBarId);
        setSupportActionBar(toolbar);
        //set home menu button
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }
    }

    public void setToolBarTittle(String tittle) {
        if (tittle != null && !tittle.isEmpty()) {
            setTitle(tittle);
        }
    }

    public void showDialogFragment(AppCompatDialogFragment dialogFragment, String tag) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(tag);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment.show(ft, tag);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void getPermissionStatus(Activity context, String request, int requestCode) {
        if (context.checkSelfPermission(request)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(context, new String[]{request},
                    requestCode);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void showVirturalKeyboard() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (m != null) {
                    m.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        }, 100);
    }


    public void hideVirturalKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getWindow().getCurrentFocus() != null) {
           imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void backupDatabase() throws IOException {
        try {
            //Open your local db as the input stream
            String inFileName = "/data/data/com.accusterltapp/databases/LtData";
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);


            String outFileName = Environment.getExternalStorageDirectory() + "/ltdb";
            //Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);
            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            //Close the streams
            output.flush();
            output.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Splash.btSocket == null || !Splash.btSocket.isConnected()) {
           // ToastUtils.showShortToastMessage(this,"Bluetooth disconnected");
        }
    }
}

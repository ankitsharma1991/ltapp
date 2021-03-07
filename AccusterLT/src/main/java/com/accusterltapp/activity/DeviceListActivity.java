package com.accusterltapp.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.accusterltapp.database.AppPreference;
import com.accusterltapp.fragment.CampsListFragment;
import com.accusterltapp.model.CampDetails1;
import com.accusterltapp.model.CampList1;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.R;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.NetworkUtil;
import com.accusterltapp.table.TableCamp;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.base.model.CampDetails;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.accusterltapp.activity.Splash.device;

public class DeviceListActivity extends AppCompatActivity {
    // Debugging for LOGCAT
    private static final String TAG = "DeviceListActivity";
    private static final boolean D = true;
    // declare button for launching website and textview for connection status
    Button btn_dl_skip;
    TextView textView1;

    // EXTRA string to send on to mainactivity
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Member fields
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private static SharedPreferences mSharedPreferences;

    ListView pairedListView;
    boolean con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        btn_dl_skip = findViewById(R.id.btn_dl_skip);
        btn_dl_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Splash.btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

    }


    @Override
    public void onResume() {
        super.onResume();
        //***************
        //checkBTState();

        textView1 = findViewById(R.id.connecting);
        textView1.setTextSize(40);
        textView1.setText(" ");

        // Initialize array adapter for paired devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        // Find and set up the ListView for paired devices
        pairedListView = findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices and append to 'pairedDevices'
        if (mBtAdapter != null) {
            Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);//make title viewable
                for (BluetoothDevice device : pairedDevices) {
                    mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            } else {
                String noDevices = getResources().getText(R.string.none_paired).toString();
                mPairedDevicesArrayAdapter.add(noDevices);
            }

        }


        // Add previosuly paired devices to the array

    }

    // Handle default back press
    @Override
    public void onBackPressed() {

        finish();
    }
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return device.createRfcommSocketToServiceRecord(Splash.BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    // Set up on-click listener for the list (nicked this - unsure)
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // textView1.setText("Connecting...");
            // progressBar.setVisibility(View.VISIBLE);
            // Get the device MAC camp_address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            AppPreference.setString(DeviceListActivity.this, AppPreference.PREF_BLUETOOTH_ADDRESS, address);
            new ProgressTask().execute();
        }
    };
    private void checkBTState() {
        // Check device has Bluetooth and that it is turned on
        mBtAdapter = BluetoothAdapter.getDefaultAdapter(); // CHECK THIS OUT THAT IT WORKS!!!
        if (mBtAdapter == null) {
            Toast.makeText(getBaseContext(), "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            if (mBtAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void connectionFuc() {
        //con = false;
        Splash.mac_address = AppPreference.getString(DeviceListActivity.this,
                AppPreference.PREF_BLUETOOTH_ADDRESS);
        Log.d("mac_address", Splash.mac_address);
        System.out.println("arjundeviceaddress" + Splash.mac_address);
        //create device and set the MAC camp_address
        // if (device!=null){
        device = Splash.btAdapter.getRemoteDevice(Splash.mac_address);
        if (Splash.btSocket != null && Splash.btSocket.isConnected()) {
            try {
                Splash.btSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//    if (SplashScreenActivity.btSocket == null || !SplashScreenActivity.btSocket.isConnected()) {
        try {
            Log.d("lob", "lob");
            Splash.btSocket = createBluetoothSocket(device);

        } catch (IOException e) {
            Log.d("lob4", "onResume1: " + e.getMessage());

            Toast.makeText(getApplicationContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try {
            Splash.btSocket.connect();
            con = true;
            Heleprec.con=true;
            Intent i = new Intent(DeviceListActivity.this, MainActivity.class);
            startActivity(i);
        } catch (IOException e) {
        }
        Splash.mConnectedThread = new Splash.ConnectedThread(Splash.btSocket);
        Splash.mConnectedThread.start();
        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        Splash.mConnectedThread.write("x");
    }
    public void dialogFun() {
        final android.support.v7.app.AlertDialog.Builder adb = new android.support.v7.app.AlertDialog.Builder(this);
        adb.setTitle("Bluetooth connection failed");
        adb.setMessage("Are you sure want to retry?");
        adb.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                new ProgressTask().execute();


            }
        });
        adb.setNegativeButton("Skip and Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(DeviceListActivity.this, MainActivity.class);
                finish();
                startActivity(i);
            }
        });
        adb.show();
    }
    public class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;
        protected void onPreExecute() {
            dialog = new ProgressDialog(DeviceListActivity.this);
            this.dialog.setMessage("Connecting...");
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setCancelable(false);
            this.dialog.show();
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (con) {
                Heleprec.con=true;
                Toast.makeText(DeviceListActivity.this, "Bluetooth device successfully connected.", Toast.LENGTH_LONG).show();
            } else {
                dialogFun();

            }
        }
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        protected Boolean doInBackground(final String... args) {
            try {
                connectionFuc();
                return true;
            } catch (Exception e) {
                Log.e("tag", "error", e);
                return false;
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_LONG).show();
                //bluetoothEnabled();
            } else if (resultCode == RESULT_CANCELED) {
                //Toast.makeText(this, "User canceled", Toast.LENGTH_LONG).show();
                // finish();
            }
        }
    }



}
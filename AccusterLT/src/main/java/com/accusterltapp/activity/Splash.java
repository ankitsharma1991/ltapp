package com.accusterltapp.activity;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.accusterltapp.login.LoginActivity;
import com.accusterltapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by appideas-user4 on 12/6/17.
 */

public class Splash extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;

    public static Handler bluetoothIn;
    // String for MAC address
    public static String mac_address;
    public static int handlerState = 0;                         //used to identify handler message
    public static BluetoothAdapter btAdapter = null;
    //private BluetoothSocket btSocket = null;
    public static BluetoothDevice device = null;
    public static InputStream mmInStream = null;
    public static OutputStream mmOutStream = null;
    public static ConnectedThread mConnectedThread;
    // SPP UUID service - this should work for most devices
    static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static BluetoothSocket btSocket = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Intent i = new Intent(Splash.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

        finish();
    }

    //create new class for connect thread
    static class ConnectedThread extends Thread {


        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
//            InputStream tmpIn = null;
//            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                mmInStream = socket.getInputStream();
                mmOutStream = socket.getOutputStream();
            } catch (IOException e) {
            }
        }


        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        byte[] msgBuffer;

        //write method
        public void write(String input) {
            msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //Toast.makeText(SplashScreenActivity.this,"error:-"+e.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("lob1", "write: " + e.getMessage());
            }
        }
    }
//    private void resetConnection() {
//        if (mmInStream != null) {
//            try {
//                mmInStream.close();
//            } catch (Exception e) {
//            }
//            mmInStream = null;
//        }
//
//        if (mmOutStream != null) {
//            try {
//                mmOutStream.close();
//            } catch (Exception e) {
//            }
//            mmOutStream = null;
//        }
//
//        if (SplashScreenActivity.btSocket != null) {
//            try {
//                SplashScreenActivity.btSocket.close();
//            } catch (Exception e) {
//            }
//            SplashScreenActivity.btSocket = null;
//        }
//
//    }
    private boolean connect() {
        if (device == null)
            device = btAdapter.getRemoteDevice(mac_address);
        // Make an RFCOMM binding.
        try {
            Splash.btSocket = device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        } catch (Exception e1) {
            Log.d("connect failed", e1.getMessage() + "");
            return false;
        }
        Log.d(" Trying to connect.", "connected");
        try {
            Splash.btSocket.connect();
        } catch (Exception e) {
            Log.d("exception throwing ", e.getMessage() + "");
            return false;
        }
        Log.d(" connect.", "connected");

        try {
            mmOutStream = Splash.btSocket.getOutputStream();
            mmInStream = Splash.btSocket.getInputStream();
        } catch (Exception e) {
            // msg ("connect(): Error attaching i/o streams to socket. msg=" + e.getMessage());
            Log.d("error in streams", e.getMessage() + "");
            return false;
        }
        return true;
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

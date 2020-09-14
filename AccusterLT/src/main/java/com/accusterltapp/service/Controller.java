package com.accusterltapp.service;


import android.util.Base64;

import com.base.utility.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
/**
 * main conmtroller paisaBazaar
 * Created by policy on 1/3/16.
 */
public class Controller {
    NetworkInterface mNetworkInterface;
    private Retrofit mRetrofit = null;
    private static final String USER_NAME = "matrixGurgaon";
    private static final String PASSWORD = "123456";
    private static Controller sMController = null;
    public static Controller buildNetworkInterface(String baseUrl) {
        if (sMController == null) {
            sMController = new Controller();
        }
        if (sMController.mNetworkInterface == null) {
            OkHttpClient okHttpClient = getClient();
            sMController.mRetrofit = new Retrofit.Builder().baseUrl(baseUrl)
                    .client(okHttpClient).build();
            sMController.mNetworkInterface = sMController.mRetrofit.create(NetworkInterface.class);
        }
        return sMController;
    }
    public void enqueueCall(BaseNetworkRequest baseNetworkRequest) {
        Call responseCall;
        if (baseNetworkRequest != null && baseNetworkRequest.context != null
                && NetworkUtil.checkInternetConnection(baseNetworkRequest.context)) {
            ServiceResponse mNetworkResponse = new ServiceResponse(baseNetworkRequest.context, baseNetworkRequest);
            responseCall = new ServiceSelector().selectMethod(this, baseNetworkRequest);
            if (responseCall != null) {
                if (baseNetworkRequest.isShowProgressBar) {
                    ProgressBar.showDialog(baseNetworkRequest.context, "Please Wait...", false);
                }
                responseCall.enqueue(mNetworkResponse);
            }

        } else {
            ToastUtils.showShortToastMessage(baseNetworkRequest.context, "Please Check Internet Connection");
        }
    }
    public static OkHttpClient getClient() {
        OkHttpClient.Builder okHttpClient = SelfSigningClientBuilder.createClient();
        okHttpClient.networkInterceptors().add(new CustomRequestInterceptor(getDefaultHeader()));
        int READ_TIMEOUT = 2 * 60;
        okHttpClient.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        int CONNECT_TIMEOUT = 6 * 60;
        okHttpClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.retryOnConnectionFailure(true);
        return okHttpClient.build();
    }
    public static HashMap<String, String> getDefaultHeader() {
        String headerParm = USER_NAME + ":" + PASSWORD;
        byte[] data = null;
        try {
            data = headerParm.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.NO_WRAP);
        HashMap<String, String> map = new HashMap<>();
        map.put("auth-param", base64);
        map.put("Content-Type", "application/x-www-form-urlencoded");
        return map;
    }
}

package com.accusterltapp.service;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;

/**
 * used to set all necessary parameter for Api call Created by policy on 3/2/2016.
 */
public class BaseNetworkRequest<T> {
    NetworkCallContext networkCallContext;
    String requestType;
    HashMap<String, String> bodyParams;
    public boolean isShowProgressBar;
    Class<T> responseType;
    public Context context;
    ArrayList<MultipartBody.Part> mParts;

    public BaseNetworkRequest(Context context, String requestType, NetworkCallContext networkCallContext,
                              HashMap<String, String> bodyParams, boolean isShowProgressBar,
                              Class<T> responseType) {
        this.context = context;
        this.networkCallContext = networkCallContext;
        this.bodyParams = bodyParams;
        this.isShowProgressBar = isShowProgressBar;
        this.responseType = responseType;
        this.requestType = requestType;
    }


    public BaseNetworkRequest(NetworkCallContext networkCallContext, String requestType, HashMap<String,
            String> bodyParams, boolean isShowProgressBar, Class<T> responseType, Context context,
                              ArrayList<MultipartBody.Part> parts) {
        this.networkCallContext = networkCallContext;
        this.requestType = requestType;
        this.bodyParams = bodyParams;
        this.isShowProgressBar = isShowProgressBar;
        this.responseType = responseType;
        this.context = context;
        mParts = parts;
    }
}

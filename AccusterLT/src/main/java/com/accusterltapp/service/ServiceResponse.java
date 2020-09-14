package com.accusterltapp.service;


import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.accusterltapp.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * base network response base network response to handle all common response Created by policy on
 * 29/2/16.
 */
public class ServiceResponse implements Callback<ResponseBody> {
    private final BaseNetworkRequest baseNetworkRequest;
    private final Context context;
    public ServiceResponse(Context context, BaseNetworkRequest baseNetworkRequest) {
        this.baseNetworkRequest = baseNetworkRequest;
        this.context = context;
    }


    @Override
    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> responseBody) {
        if (baseNetworkRequest.isShowProgressBar) {
            ProgressBar.dismissDialog();
        }
        if (responseBody.isSuccessful()) {
            try {
                Object response = responseBody.body().string();
                String json = response.toString();


                if (!TextUtils.isEmpty(json)) {
                    //set raw response
                    if (baseNetworkRequest.responseType == null) {
                        baseNetworkRequest.networkCallContext.handleResponse(response, baseNetworkRequest.requestType);
                    } else {
                        baseNetworkRequest.networkCallContext.handleResponse(new Gson().fromJson(json, baseNetworkRequest.responseType), baseNetworkRequest.requestType);
                    }
                } else {
                    baseNetworkRequest.networkCallContext.handleServerError(context.getString(R.string.server_error), baseNetworkRequest.requestType);
                }

            } catch (Exception e) {
                baseNetworkRequest.networkCallContext.handleServerError(context.getString(R.string
                        .server_error), baseNetworkRequest.requestType);
                e.printStackTrace();
            }
        } else {
            baseNetworkRequest.networkCallContext.handleServerError(context.getString(R.string.server_error), baseNetworkRequest.requestType);
        }
    }

    @Override
    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
        baseNetworkRequest.networkCallContext.handleServerError(context.getString(R.string.server_error), baseNetworkRequest.requestType);
    }
}

package com.accusterltapp.service;

import com.accusterltapp.model.RepidTestReslt;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * used to define method
 * Created by policy on 29/2/16.
 */

interface NetworkInterface {
    @POST(ApiConstant.CAMP_LIST)
    Call<ResponseBody> campList();

    @POST(ApiConstant.PATIENT_LIST)
    Call<ResponseBody> userList();

    @POST(ApiConstant.REPORT_LIST)
    Call<ResponseBody> reportList();

    @FormUrlEncoded
    @POST(ApiConstant.PATIENT_LIST_BYCAMP)
    Call<ResponseBody> userListCamp(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST(ApiConstant.REPORT_LIST_BYUSER)
    Call<ResponseBody> reportListUser(@FieldMap HashMap<String, String> hashMap);


    @FormUrlEncoded
    @POST(ApiConstant.USER_LOGIN)
    Call<ResponseBody> userLogin(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST(ApiConstant.REPORT_UPDATE_BY_USER_ID)
    Call<ResponseBody> updateReport(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST(ApiConstant.ADD_PATIENT)
    Call<ResponseBody> addPatient(@FieldMap HashMap<String, String> bodyParams);

    @FormUrlEncoded
    @POST(ApiConstant.CREATE_CAMP)
    Call<ResponseBody> createCamp(@FieldMap HashMap<String, String> bodyParams);

    @POST(ApiConstant.GET_TEST_LIST)
    Call<ResponseBody> getAllTest();
    @FormUrlEncoded
    @POST(ApiConstant.PACKAGE_LIST)
    Call<ResponseBody> getAllPackage(@FieldMap HashMap<String, String> bodyParams);

    @FormUrlEncoded
    @POST(ApiConstant.CAMP_SYN)
    Call<ResponseBody> synCamp(@FieldMap HashMap<String, String> bodyParams);

    @FormUrlEncoded
    @POST(ApiConstant.PATIENT_SYN)
    Call<ResponseBody> patientSyn(@FieldMap HashMap<String, String> bodyParams);

    @FormUrlEncoded
    @POST(ApiConstant.REPORT_SYN)
    Call<ResponseBody> reportSyn(@FieldMap HashMap<String, String> bodyParams);

    @FormUrlEncoded
    @POST(ApiConstant.PACKAGE_SYNC)
    Call<ResponseBody> packageSyn(@FieldMap HashMap<String, String> bodyParams);

    @Multipart
    @POST(ApiConstant.IMAGE_SYNC)
    Call<ResponseBody> uploadAllFiles(@Part ArrayList<MultipartBody.Part> bodyParams);

    @Multipart
    @POST("rapidTestImage")
    Call<RepidTestReslt> postImage1(@Part MultipartBody.Part image, @Part("file") RequestBody name);
}

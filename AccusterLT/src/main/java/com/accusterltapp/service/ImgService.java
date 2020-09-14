package com.accusterltapp.service;

import com.accusterltapp.model.QCResponse;
import com.accusterltapp.model.RepidTestReslt;
import com.accusterltapp.model.ReportSettingUpdate;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImgService {
    @Multipart
    @POST("editCampDetailApi")
    Call<ReportSettingUpdate> postImage(@Part("camp_id") RequestBody campid, @Part("address") RequestBody name,
                                        @Part("header") RequestBody header, @Part MultipartBody.Part image,
                                        @Part("file") RequestBody iname);

        @Multipart
        @POST("rapidTestImage")
        Call<RepidTestReslt> postImage1(@Part MultipartBody.Part image, @Part("file") RequestBody name);

    @FormUrlEncoded
    @POST("ltQcData")
    Call<QCResponse> getQcByLT(@Field("lt_id") String ltId);

}

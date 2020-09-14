package com.accusterltapp.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class CustomRequestInterceptor implements Interceptor {
    public HashMap<String, String> headerMap;
    Request request;

    public CustomRequestInterceptor(HashMap<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (headerMap != null && !headerMap.isEmpty()) {
            Set<String> keySet = headerMap.keySet();
            for (String key : keySet) {
                request = chain.request().newBuilder().addHeader(key, headerMap.get(key)).build();
            }

        }
        Response response = chain.proceed(request);
        return response;

    }
}

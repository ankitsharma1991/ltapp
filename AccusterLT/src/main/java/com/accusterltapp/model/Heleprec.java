package com.accusterltapp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.accusterltapp.database.AppPreference;
import com.accusterltapp.service.ApiConstant;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.base.model.CampDetails;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Heleprec {
    public static boolean avroverreport=false;
    static  boolean sl=false;
   public static  Bitmap sinature=null;
   public  static  boolean isc3=false;
   public static  String qc_time=null;
     public static ArrayList<AppointMentData> appointList=new ArrayList<>();

    public static boolean isSl() {
        return sl;
    }

    public static void setSl(boolean sl) {
        Heleprec.sl = sl;
    }
    public static ArrayList<SubTestDetails> testlist=new ArrayList<>();
     public static  boolean con=true;
     public static HashMap<String,CampDetails1>  repostlistmap=new HashMap<>();
     public static ArrayList<CampDetails1> list;
     public static  String current_camp_name=null;
     public static Bitmap logo=null;
     public static boolean update=false;
     public static ArrayList<String> testId_list;
     public static ArrayList<Integer> headerchange=new ArrayList<>();
     public static void getCamList(final Context context)

     {
       //  adapter.notifyDataSetChanged();
Heleprec.list.clear();

         ArrayList<CampDetails> campDetails = new ArrayList<>();
         com.android.volley.RequestQueue queue = Volley.newRequestQueue(context);
         StringRequest sr = new StringRequest(Request.Method.POST, ApiConstant.BASE_URL1+"getListApiCamp", new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 // mPostCommentResponse.requestCompleted();
                 //  System.out.print(response);
                 //   pb.setVisibility(View.INVISIBLE);
                 Log.d("response",response);
                 try {
                     JSONObject object =new JSONObject(response);
                     Gson gn=new Gson();
                     CampList1 clist=gn.fromJson(response,CampList1.class);
                     Log.d("arr",clist.toString());
                     list=clist.getCampList();
                     for(int i=0;i<list.size();i++)
                     {
                         if(Integer.parseInt(list.get(i).getReport_header())==1)
                         {
                             list.get(i).setHeader(true);
                         }
                         else {
                             list.get(i).setHeader(false);
                         }
                         Heleprec.repostlistmap.put(list.get(i).getName(),list.get(i));

                     }
                   //  Heleprec.list.addAll(list);
                    // adapter.notifyDataSetChanged();
                    // context.newList(list);
                     // ReportsettingAdapter adapter=new ReportsettingAdapter(ReportSettingFragment.this.getActivity(),list);
                     // recyclerView.setAdapter(adapter);
                     // setupRecycler(clist.getCampList());

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }


             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Log.d("error ",error.toString());
                 // mPostCommentResponse.requestEndedWithError(error);
             }
         }){
             @Override
             protected Map<String,String> getParams(){
                 Map<String,String> params = new HashMap<String, String>();
                 //    String ap=AppPreference.USER_ID;
                 params.put("ltId", AppPreference.getString(context, AppPreference.USER_ID));

                 return params;
             }

             @Override
             public Map<String, String> getHeaders() throws AuthFailureError {
                 return Collections.emptyMap();
             }
         };
         queue.add(sr);



     }

   //  public  static String Adrresscamp="Accuster Technologies Pvt. Ltd."+"Plot No 41 Sec"+"IMT Manesar, Gurgaon (HR)";
}

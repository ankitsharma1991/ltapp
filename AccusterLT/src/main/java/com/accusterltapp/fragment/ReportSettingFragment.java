package com.accusterltapp.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.accusterltapp.adapter.ReportsettingAdapter;
import com.accusterltapp.model.CampDetails1;
import com.accusterltapp.model.CampHeaderList;
import com.accusterltapp.model.CampHeaderModel;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.service.ApiConstant;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.accusterltapp.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class ReportSettingFragment extends android.support.v4.app.Fragment implements RecyclerView.RecyclerListener {
    private RecyclerView recyclerView;
    ArrayList<CampDetails1> list;
    ProgressBar pb;
    ProgressDialog progress;
    ReportsettingAdapter adapter;
    boolean rdat=false;
    AlertDialog alertDialog;
    long currentVisiblePosition = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.report_setting_list, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
      //  v.setFocusableInTouchMode(true);
       // v.requestFocus();

        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                ;
               // Log.i(tag, "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.e("listener work", "onKey Back listener is working!!!");
                   // getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setCancelable(true);
                    if(Heleprec.headerchange.size()>0)
                    {
                        rdat=false;
                    }
                    else rdat=true;
                    // Setting Dialog Title
                    alertDialog.setTitle("Save Changes....");

                    // Setting Dialog Message
                    alertDialog.setMessage("Please Save the Changes");

                    // Setting alert dialog AdvertiesImage
                    alertDialog.setButton(1, "Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    //alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
                    alertDialog.setButton(2,"cancel",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // ReportSettingFragment.this.
                            Heleprec.headerchange.clear();
                          //  rdat=false;
                            dialog.dismiss();
                        }
                    });

                    // Setting OK Button
                    alertDialog.setButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            savedata();
                           // rdat=false;
                           // ReportSettingFragment.this.
                           // dialog.dismiss();
                        }
                    });


                    // Showing Alert Message
                   // if(Heleprec.headerchange.size()>0)
                       // savedata();
                    alertDialog.show();

                        Log.e("size ", Heleprec.headerchange.size()+"");
                    return rdat;
                }
                return true;
            }
        });
      //  pb=
        progress = new ProgressDialog(getContext());
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progress.show();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(Heleprec.update&& Heleprec.list!=null) {
            list = Heleprec.list;
             adapter = new ReportsettingAdapter(ReportSettingFragment.this.getActivity(), Heleprec.list);
            recyclerView.setAdapter(adapter);
            progress.dismiss();
        }
        else {
            progress.dismiss();
        }
       return v;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

            inflater.inflate(R.menu.save, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

savedata();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void newList(ArrayList<CampDetails1> list)
    {
        Heleprec.list.clear();
        Heleprec.list.addAll(list);
        adapter.notifyDataSetChanged();
        progress.dismiss();
    }
    public void savedata()
    {
        final CampHeaderList object= new CampHeaderList();
        object.headerlist=new ArrayList<>();
        ArrayList<CampHeaderModel> campheader=new ArrayList<>();

        ArrayList<String> list1=new ArrayList<>();
        for(int i = 0; i< Heleprec.headerchange.size(); i++)
        {

            CampHeaderModel ob=new CampHeaderModel();
            ob.setCamp_id(Heleprec.list.get(Heleprec.headerchange.get(i)).getId());
            ob.setHeader(Heleprec.list.get(Heleprec.headerchange.get(i)).getReport_header());

            //list.add(Heleprec.list.get(i).getId());
            //list.get(i).setReport_header(Heleprec.);
            campheader.add(ob);
        }
        object.setHeaderlist(campheader);
        final    Gson g=new Gson();
        g.toJson(object);
        Log.e("gson data",g.toJson(object));
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(ReportSettingFragment.this.getContext());

        JSONObject job=null;
        String ob=g.toJson(object);
        try {
            JSONObject jsono = new JSONObject(ob);
            job=jsono;
        }
        catch (Exception e)
        {

        }
        // queue.add(sr);
        JsonObjectRequest request = new JsonObjectRequest(ApiConstant.BASE_URL1+"changeHeaderApi", job,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("happen",response.toString());
                        Heleprec.update=true;
                        progress.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body;
                Log.e("error",error.toString());
            }


        });
        queue.add(request);
        progress = new ProgressDialog(getContext());
        progress.setCancelable(true);
        progress.setMessage("Saving...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progress.show();
    }
    private Parcelable state;
    @Override
    public void onPause() {
        super.onPause();
        // this variable should be static in class
        currentVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }
    @Override
    public void onResume() {
        super.onResume();
        if(Heleprec.update&&adapter!=null){
            list = Heleprec.list;
            adapter = new ReportsettingAdapter(ReportSettingFragment.this.getActivity(), Heleprec.list);
            recyclerView.setAdapter(adapter);
        }
        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition((int) currentVisiblePosition);
        currentVisiblePosition = 0;
        Log.e("resume ","call");
    }

}

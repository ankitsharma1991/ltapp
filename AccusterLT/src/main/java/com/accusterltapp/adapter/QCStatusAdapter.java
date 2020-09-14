package com.accusterltapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.accusterltapp.R;
import com.accusterltapp.model.QCDetail;
import com.accusterltapp.model.QcData;
import com.base.model.CampDetails;

import java.util.ArrayList;

public class QCStatusAdapter extends  RecyclerView.Adapter<QCStatusAdapter.ViewHolderc>
{
    Context mcontext;
    ArrayList<QCDetail> list;
    QcData qcData;
    CampDetails campDetails;
    ArrayList<CampDetails> campList;
    String qc_data_Status[] = {"Action", "Approve", "Pending", "Reject"};
    ArrayAdapter statusAdapter;
    String action="";

    public QCStatusAdapter(Context mcontext, ArrayList<QCDetail> list, ArrayList<CampDetails> campList)
    {
        this.list=list;
        this.mcontext=mcontext;
        this.campList = campList;
    }

    public QCStatusAdapter() {
    }

    @Override
    public QCStatusAdapter.ViewHolderc onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.qcdata_adapter1,parent,false);
        QCStatusAdapter.ViewHolderc ob=new QCStatusAdapter.ViewHolderc(v);
        return ob;
    }

    @Override
    public void onBindViewHolder(QCStatusAdapter.ViewHolderc holder, final int position) {
        qcData = new QcData();
        campDetails = new CampDetails();
       // CampDetails campDetails = campList.get(position);
       holder.tv_camp_name.setText(list.get(position).getCampName());
        holder.c1.setText(list.get(position).getQcC1());
        holder.c2.setText(list.get(position).getQcC2());
        holder.c3.setText(list.get(position).getQcC3());
        holder.l1.setText(list.get(position).getQcL1());
        holder.l2.setText(list.get(position).getQcL2());
        holder.lab_id.setText(list.get(position).getQcLabId());
        holder.test_name.setText(list.get(position).getTestName());
        holder.test_name.setText(list.get(position).getTestName());
        holder.date.setText(list.get(position).getQcDateUpdate());
        //holder.tv_camp_name.setText(campDetails.getCamp_name());
        try {
            String time[] = list.get(position).getTime().split(" ");
            holder.time.setText(time[1]);
        }
        catch (Exception e)
        {

        }

        if (list.get(position).getQc_status()==1)
        {
            holder.imageView.setImageResource(R.drawable.letter_a);
        }
        else if (list.get(position).getQc_status()==2)
        {
            holder.imageView.setImageResource(R.drawable.r);
        }
        else if (list.get(position).getQc_status()==0)
        {
            holder.imageView.setImageResource(R.drawable.letter_p);
        }
        statusAdapter = new ArrayAdapter(mcontext,android.R.layout.simple_spinner_item, qc_data_Status);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(statusAdapter);
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).toString().equals("Action")) {
                    action = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
       // QCStatusAdapter.this.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolderc extends RecyclerView.ViewHolder
    {
        TextView test_name,c1,c2,c3,l1,l2,lab_id,date,time,tv_camp_name;
        Spinner spinner;
        Button submit;
        ImageView imageView;
        public ViewHolderc(View v)
        {
            super(v);
            lab_id=v.findViewById(R.id.tv_device);
            test_name=v.findViewById(R.id.tv_testname);
            c1=v.findViewById(R.id.tv_c1);
            c2=v.findViewById(R.id.tv_c2);
            c3=v.findViewById(R.id.tv_c3);
            l1=v.findViewById(R.id.tv_l1);
            l2=v.findViewById(R.id.tv_l2);
            date=v.findViewById(R.id.date);
            tv_camp_name=v.findViewById(R.id.tv_campname);
            time=v.findViewById(R.id.tv_time);
            spinner = v.findViewById(R.id.status_spinner);
            submit = v.findViewById(R.id.button);
            imageView = v.findViewById(R.id.imageView);
        }
    }
}

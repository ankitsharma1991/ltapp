package com.accusterltapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.accusterltapp.R;
import com.accusterltapp.model.ApprovedReportTestDetails;

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.Holder>{
    Context mcontext;
    ArrayList<ApprovedReportTestDetails> list;
   public StatusAdapter(Context mcontext, ArrayList<ApprovedReportTestDetails> list)
    {
        this.mcontext=mcontext;
        this.list=list;

    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_status_dialog,parent,false);
        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tv_name.setText(list.get(position).getTest_name());
        if (list.get(position).getReport_status().equals("1"))
        {
            holder.tv_status.setText("Approved");
        }
        else if (list.get(position).getReport_status().equals("2"))
        {
            holder.tv_status.setText("Pending");
        }
        else holder.tv_status.setText("Reject");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends  RecyclerView.ViewHolder
    {
        TextView tv_name,tv_status;
        Holder(View v)
    {
    super(v);
    tv_name=v.findViewById(R.id.tv_name);
    tv_status=v.findViewById(R.id.tv_status);
    }

    }

}

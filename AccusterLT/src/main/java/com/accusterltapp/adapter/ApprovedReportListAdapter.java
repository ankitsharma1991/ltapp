package com.accusterltapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.accusterltapp.model.ApprovedReportData;
import com.base.listener.RecyclerViewListener;
import com.accusterltapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ApprovedReportListAdapter extends RecyclerView.Adapter<ApprovedReportListAdapter.CardViewHolder> {

    private Context context;
    private RecyclerViewListener mListener;
    private ArrayList<ApprovedReportData> mCampList;

    public ApprovedReportListAdapter(Context context, RecyclerViewListener listener, ArrayList<ApprovedReportData> campList) {
        this.context = context;
        mListener = listener;
        mCampList=campList;
    }
    // create new views (invoked by the layout manager)
    @Override
    public ApprovedReportListAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approved_report_adapter, parent, false);
        return new ApprovedReportListAdapter.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ApprovedReportListAdapter.CardViewHolder holder, int position) {
        final ApprovedReportData details=mCampList.get(position);
        holder.tv_pa_labelNo.setText(String.valueOf(details.getId()));
        holder.tv_campname.setText(details.getName());
        holder.age.setText(details.getAge());
        if (details.getReport_status().equals("1"))
        {
            holder.status.setImageResource(R.drawable.letter_a);
        }else if (details.getReport_status().equals("2"))
        {
            holder.status.setImageResource(R.drawable.letter_p);
        }
        else if (details.getReport_status().equals("3"))
        {
            holder.status.setImageResource(R.drawable.r);
        }
        holder.amount.setText(details.getAmount());
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> hashMap= new HashMap<>();
                hashMap.put("patientcode",details.getPatientCode());
                mListener.onItemClickListener(v, holder.getAdapterPosition(),hashMap);
            }
        });
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> hashMap= new HashMap<>();
                hashMap.put("patientcode",details.getPatientCode());
                mListener.onItemClickListener(view, holder.getAdapterPosition(),hashMap);
            }
        });


    }


    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {
        return mCampList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {


        public TextView tv_pa_labelNo;
        public TextView tv_campname;
        public ImageView img_pa_preview;
        public TextView age;
        public  TextView amount;
        public  ImageView status,info;

        public CardViewHolder(View itemView) {

            super(itemView);
            age=itemView.findViewById(R.id.age);
            amount=itemView.findViewById(R.id.amount);
            info=itemView.findViewById(R.id.iv_info);

            tv_pa_labelNo = itemView.findViewById(R.id.tv_pa_labelNo);
            tv_campname = itemView.findViewById(R.id.tv_campname);
//            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
           // img_pa_preview = itemView.findViewById(R.id.img_pa_preview);
            status=itemView.findViewById(R.id.iv_status);

        }
    }
    }
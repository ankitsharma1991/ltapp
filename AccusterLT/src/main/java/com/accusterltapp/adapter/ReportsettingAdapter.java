package com.accusterltapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.accusterltapp.activity.LogoChangeActivity;
import com.accusterltapp.model.CampDetails1;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.R;

import java.util.ArrayList;

public class ReportsettingAdapter extends RecyclerView.Adapter<ReportsettingAdapter.CardViewHolder> {

    private Context context;
   // private RecyclerViewListener mListener;
    private ArrayList<CampDetails1> mCampList;

    public ReportsettingAdapter(Context context,  ArrayList<CampDetails1> campList) {
        this.context = context;
       // mListener = listener;
        mCampList=campList;
    }

    // create new views (invoked by the layout manager)
    @Override
    public ReportsettingAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_setting_adapter, parent, false);
        return new ReportsettingAdapter.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReportsettingAdapter.CardViewHolder holder, final int position) {
        final CampDetails1 details=mCampList.get(position);


       // holder.tv_pa_labelNo.setText(String.valueOf(details.getCampID()));
        holder.sno.setText(position+1+"");
        holder.tv_campname.setText(details.getName());
        holder.header.setOnCheckedChangeListener(null);
        if (details.isHeader())
            holder.header.setChecked(true);
        else
            holder.header.setChecked(false);
    holder.header.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            try {
                details.setHeader(b);
                Heleprec.list.get(position).setHeader(b);
                if (b)
                    Heleprec.list.get(position).setReport_header("1");
                else
                    Heleprec.list.get(position).setReport_header("0");
                Heleprec.headerchange.add(position);
            }catch (Exception e)
            {

            }


         // //  Heleprec.repostlistmap.put(details.getName(),details);
        }
    });
        holder.footer.setOnCheckedChangeListener(null);
        if (details.isFooter())
            holder.footer.setChecked(true);
        else
            holder.footer.setChecked(false);
        holder.footer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    details.setFooter(b);
                    Heleprec.list.get(position).setFooter(b);
                    if (details.getReport_header()==null)
                        details.setHeader(true);
                    Heleprec.repostlistmap.put(details.getName(), details);
                }
                catch (Exception e)
                {

                }
            }
        });
        holder.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(context.getApplicationContext(), LogoChangeActivity.class);
                in.putExtra("url",details.getCampLogo());
                in.putExtra("camp_id",details.getId());
                in.putExtra("address",details.getCamp_address());
                in.putExtra("header",details.getReport_header());
                context.startActivity(in);
            }
        });
    }
    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {
        return mCampList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {


        public TextView sno;
        public TextView tv_campname,address;
        public Switch header,footer;
        public Button logo;
        public CardViewHolder(View itemView) {

            super(itemView);

            sno= itemView.findViewById(R.id.tv_pId);
            tv_campname = itemView.findViewById(R.id.camp_name);
//            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            header= itemView.findViewById(R.id.header);
            footer=itemView.findViewById(R.id.footer);
            logo=itemView.findViewById(R.id.logo);
           // address=itemView.findViewById(R.id.address);

        }
    }
}


package com.accusterltapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.accusterltapp.model.CampDetails1;
import com.base.listener.RecyclerViewListener;
import com.accusterltapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CampListAdapter1  extends RecyclerView.Adapter<CampListAdapter1.CardViewHolder> {

    private Context context;
    private RecyclerViewListener mListener;
    private ArrayList<CampDetails1> mCampList;

    public CampListAdapter1(Context context, RecyclerViewListener listener, ArrayList<CampDetails1> campList) {
        this.context = context;
        mListener = listener;
        mCampList=campList;
    }

    // create new views (invoked by the layout manager)
    @Override
    public CampListAdapter1.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.campadapter, parent, false);
        return new CampListAdapter1.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CampListAdapter1.CardViewHolder holder, int position) {
        final CampDetails1 details=mCampList.get(position);
        holder.tv_pa_labelNo.setText(String.valueOf(position+1));
        holder.tv_campname.setText(details.getName());
        holder.start_date.setText(details.getStartdate());
        holder.end_date.setText(details.getEnddate());

        holder.img_pa_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> hashMap= new HashMap<>();
                hashMap.put("campCode",details.getId());
                hashMap.put("campname",details.getName());
                mListener.onItemClickListener(v, holder.getAdapterPosition(),hashMap);
            }
        });


    }


    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {
        Log.d("size ",mCampList.size()+"");
        return mCampList.size();

    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {


        public TextView tv_pa_labelNo;
        public TextView tv_campname;
        public ImageView img_pa_preview;
        public TextView start_date,end_date;

        public CardViewHolder(View itemView) {

            super(itemView);
            start_date =itemView.findViewById(R.id.total_test);
            end_date=itemView.findViewById(R.id.total_patient);
            tv_pa_labelNo = itemView.findViewById(R.id.tv_pa_labelNo);
            tv_campname = itemView.findViewById(R.id.tv_campname);
//            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            img_pa_preview = itemView.findViewById(R.id.img_pa_preview);

        }
    }
}
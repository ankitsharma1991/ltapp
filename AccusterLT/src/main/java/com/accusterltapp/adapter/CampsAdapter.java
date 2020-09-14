package com.accusterltapp.adapter;

/**
 * Created by appideas-user2 on 17/7/17.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.listener.RecyclerViewListener;
import com.base.model.CampDetails;
import com.accusterltapp.R;

import java.util.ArrayList;
import java.util.HashMap;


public class CampsAdapter extends RecyclerView.Adapter<CampsAdapter.CardViewHolder> {

    private Context context;
    private RecyclerViewListener mListener;
    private ArrayList<CampDetails> mCampList;

    public CampsAdapter(Context context, RecyclerViewListener listener, ArrayList<CampDetails> campList) {
        this.context = context;
        mListener = listener;
        mCampList=campList;
    }

    // create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.campadapter, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        final CampDetails details=mCampList.get(position);
        holder.tv_pa_labelNo.setText(position+1+"");
        holder.tv_campname.setText(details.getCampName());
        holder.start_date.setText(details.getStartDate());
        holder.end_date.setText(details.getEndDate());

        holder.img_pa_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> hashMap= new HashMap<>();
                hashMap.put("campCode",details.getCamp_code());
                mListener.onItemClickListener(v, holder.getAdapterPosition(),hashMap);
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

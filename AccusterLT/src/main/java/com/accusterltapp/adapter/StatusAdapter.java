package com.accusterltapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accusterltapp.R;
import com.accusterltapp.model.ApprovedReportTestDetails;

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.Holder>{
    Context mcontext;
    ArrayList<ApprovedReportTestDetails> list;
    public static ArrayList<String> testId = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, ArrayList<String> testId);
    }
    public StatusAdapter(Context mcontext, ArrayList<ApprovedReportTestDetails> list,OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener = onItemClickListener;
        this.mcontext=mcontext;
        this.list=list;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_status_dialog,parent,false);
        final Holder holder=new Holder(view);


            holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (holder.tv_status.getText() == "Reject") {
                        if (testId.contains(list.get(holder.getAdapterPosition()).getTest_id())) {
                            testId.remove(list.get(holder.getAdapterPosition()).getTest_id());
                            holder.chkbox.setChecked(false);
                        } else {
                            holder.chkbox.setChecked(true);
                            testId.add(list.get(holder.getAdapterPosition()).getTest_id());
                        }
                        mOnItemClickListener.onItemClick(v, holder.getAdapterPosition(), testId);
                    }
                }
            });

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tv_name.setText(list.get(position).getTest_name());
        if (list.get(position).getReport_status().equals("1"))
        {
            holder.tv_status.setText("Approved");
            holder.chkbox.setVisibility(View.INVISIBLE);
        }
        else if (list.get(position).getReport_status().equals("2"))
        {
            holder.tv_status.setText("Pending");
            holder.chkbox.setVisibility(View.INVISIBLE);
        }
        else{
            holder.tv_status.setText("Reject");
            holder.chkbox.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends  RecyclerView.ViewHolder
    {
        TextView tv_name,tv_status;
        CheckBox chkbox;
        RelativeLayout relativeLayoutMain;
        Holder(View v)
    {
    super(v);
    tv_name=v.findViewById(R.id.tv_name);
    tv_status=v.findViewById(R.id.tv_status);
        chkbox=v.findViewById(R.id.chkbox);
        relativeLayoutMain=v.findViewById(R.id.relativeLayoutMain);
    }

    }

}

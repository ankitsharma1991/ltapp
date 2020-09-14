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

import com.accusterltapp.model.QcData;
import com.accusterltapp.R;
import com.base.model.CampDetails;
import com.base.model.CampList;

import java.util.ArrayList;

public class QcdataAdapter extends RecyclerView.Adapter<QcdataAdapter.ViewHolderc> {
    Context mcontext;
    ArrayList<QcData> list;
    ArrayList<CampDetails> campList;
    String qc_data_Status[] = {"Action", "Approve", "Pending", "Reject"};
    ArrayAdapter statusAdapter;
    String action="";
    public QcdataAdapter(Context mcontext, ArrayList<QcData> list, ArrayList<CampDetails> campList)
    {
       this.list=list;
       this.mcontext=mcontext;
       this.campList = campList;
    }
    @Override
    public ViewHolderc onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.qcdata_adapter,parent,false);
        ViewHolderc ob=new ViewHolderc(v);
        return ob;
    }

    @Override
    public void onBindViewHolder(final ViewHolderc holder, final int position) {
        QcData qcdata=list.get(position);
holder.lab_id.setText((CharSequence) qcdata.getLab_id());
holder.test_name.setText(qcdata.getTest_id());
holder.c1.setText(qcdata.getC1());
holder.c2.setText(qcdata.getC2());
holder.c3.setText(qcdata.getC3());
holder.l1.setText(qcdata.getL1());
holder.l2.setText(qcdata.getL2());
Log.e("the date is ",qcdata.getDate()+" jihkf");
holder.date.setText(qcdata.getDate().substring(0,4)+"/"+qcdata.getDate().substring(4,6)+"/"+qcdata.getDate().substring(6,8));
holder.time.setText(qcdata.getTime());
for (int i=0;i<campList.size();i++)
{
    Log.e("da "+campList.get(i).getCampID()," and "+campList.get(i).getCamp_code());
    if (list.get(position).getLab_id().equals(campList.get(i).getCampID()))
    {

    }
}
//holder.tv_camp_name.setText(campDetails.getCamp_name());
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

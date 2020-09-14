package com.accusterltapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accusterltapp.model.RegisterPatient;
import com.accusterltapp.table.TablePatientTest;
import com.base.listener.RecyclerViewListener;
import com.base.utility.StringUtils;
import com.accusterltapp.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pbadmin on 3/11/17.
 *
 */

public class





PatientTableViewAdapter extends RecyclerView.Adapter<PatientTableViewAdapter.CardViewHolder> {

    private ArrayList<RegisterPatient> patientArrayList;
    private RecyclerViewListener mListener;
    private TablePatientTest mPatient;


    public PatientTableViewAdapter(Context context, ArrayList<RegisterPatient> itemList, RecyclerViewListener listener) {
        patientArrayList = itemList;
        mListener = listener;
        mPatient = new TablePatientTest(context);

    }

    @Override
    public PatientTableViewAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patientadapter, parent, false);
        return new PatientTableViewAdapter.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        final RegisterPatient patient = patientArrayList.get(position);
        holder.pId.setText(patient.getUserregistration_code());
        holder.p_name.setText(patient.getUserregistration_complete_name());
        holder.pLid.setText(patientArrayList.size()-position+" ");
        String gender="-";
       Log.e("pateintid",patient.getUserregistration_gender_id());
        if (patient.getUserregistration_gender_id().equalsIgnoreCase("0")){
            gender="Male";
        }else if (patient.getUserregistration_gender_id().equalsIgnoreCase("1")){
            gender="Female";
     }
        holder.pSex.setText(gender);

        holder.textViewMobileNo.setText(mPatient.getTestCode(patient.getUserregistration_code()));
        holder.p_totalcost.setText("\u20B9 "+ StringUtils.indianFormat(mPatient.getTotalTestCost(patient.getUserregistration_code())));

       try {
   //holder.pStatus.setText(mPatient.getReportStatus(patient.getUserregistration_Id_no()) ? "Success" : "Pending");
       }
       catch (Exception e)
       {
           holder.pStatus.setText("Pending");
Log.e("exception ",e.getMessage()+" hi");
       }

        holder.imageViewPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("action", "show");
                mListener.onItemClickListener(view, holder.getAdapterPosition(), hashMap);
            }
        });
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("action", "set");
                mListener.onItemClickListener(view, holder.getAdapterPosition(), hashMap);
            }
        });
    }


    @Override
    public int getItemCount() {
        return patientArrayList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView pId, pLid, pStatus, p_totalcost, p_name, textViewMobileNo, pSex;
        public ImageView imageViewPreview;
        private LinearLayout mLayout;

        public CardViewHolder(View itemView) {
            super(itemView);
            pId = itemView.findViewById(R.id.pId);
            p_name = itemView.findViewById(R.id.p_name);
            pLid = itemView.findViewById(R.id.labelid);
            pSex = itemView.findViewById(R.id.pSex);
            textViewMobileNo = itemView.findViewById(R.id.mobileNumber);
            p_totalcost = itemView.findViewById(R.id.p_totalcost);
            pStatus = itemView.findViewById(R.id.status);
            imageViewPreview = itemView.findViewById(R.id.img_pa_preview);
            mLayout = itemView.findViewById(R.id.lv_parent);


        }
    }

    public void animateTo(ArrayList<RegisterPatient> newModels) {
        this.patientArrayList.clear();
        notifyDataSetChanged();
        applyAndAnimateAdditions(newModels);

    }

    private void applyAndAnimateAdditions(ArrayList<RegisterPatient> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final RegisterPatient model = newModels.get(i);
            if (!patientArrayList.contains(model)) {
                addItem(i, model);
            }
        }
    }


    public void addItem(int position, RegisterPatient model) {
        patientArrayList.add(position, model);
        notifyItemInserted(position);
    }
}

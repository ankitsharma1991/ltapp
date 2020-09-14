package com.accusterltapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accusterltapp.model.AppointMentData;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.model.RegisterPatient;
import com.accusterltapp.table.TablePatientTest;
import com.base.listener.RecyclerViewListener;
import com.accusterltapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientaddAdapter extends RecyclerView.Adapter<PatientaddAdapter.CardViewHolder> {

    private ArrayList<AppointMentData> patientArrayList;
    private RecyclerViewListener mListener;
    private TablePatientTest mPatient;


    public PatientaddAdapter(Context context, ArrayList<AppointMentData> itemList, RecyclerViewListener listener) {
        patientArrayList = itemList;
        mListener = listener;
        mPatient = new TablePatientTest(context);

    }

    @Override
    public PatientaddAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patientaddadapter, parent, false);
        return new PatientaddAdapter.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PatientaddAdapter.CardViewHolder holder, final int position) {
        final AppointMentData patient = patientArrayList.get(position);
        holder.pId.setText(position+1+"");
        holder.p_name.setText(patient.getName());
       // holder.pLid.setText(patient.getId());
        String gender="-";
//        if (patient.getUserregistration_gender_id().equalsIgnoreCase("1")){
//            gender="Male";
//        }else if (patient.getUserregistration_gender_id().equalsIgnoreCase("2")){
//            gender="Female";
//
        if(patient.getStatus().equals('1'))
        {
            holder.pStatus.setText("Success");
            patient.setSelected(true);
        }
        else {
            holder.pStatus.setText("Pending");
            patient.setSelected(false);

        }

       // holder.pSex.setText(gender);
        holder.cb.setOnCheckedChangeListener(null);
        if (patient.isSelected()) {
            holder.cb.setChecked(true);
        }
        else {
            holder.cb.setChecked(false);
        }

        holder.textViewMobileNo.setText(patient.getMobile());

      //  holder.p_totalcost.setText("\u20B9 "+ StringUtils.indianFormat(mPatient.getTotalTestCost(patient.getUserregistration_code())));
       //holder.pStatus.setText(mPatient.getReportStatus(patient.getUserregistration_code())?"Success":"Pending");

//        holder.imageViewPreview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put("action", "show");
//                mListener.onItemClickListener(view, holder.getAdapterPosition(), hashMap);
//            }
//        });
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("action", "set");
                mListener.onItemClickListener(view, holder.getAdapterPosition(), hashMap);
            }
        });

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                patient.setSelected(b);
                if(b)
                {
                   patientArrayList.get(position).setStatus("1");
                    patient.setStatus("1");
                    holder.pStatus.setText("Success");
                    Heleprec. appointList.add(patientArrayList.get(position));

                }
                else {
                    patientArrayList.get(position).setStatus("0");
                    patient.setStatus("1");
                    holder.pStatus.setText("Pending");
                    Heleprec.appointList.remove(patientArrayList.get(position));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return patientArrayList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView pId, pLid, pStatus, p_totalcost, p_name, textViewMobileNo, pSex;
        public CheckBox cb;
        private LinearLayout mLayout;

        public CardViewHolder(View itemView) {
            super(itemView);
           pId = itemView.findViewById(R.id.pId);
            p_name = itemView.findViewById(R.id.p_name);
           // pLid = itemView.findViewById(R.id.labelid);
            pSex = itemView.findViewById(R.id.pSex);
            textViewMobileNo = itemView.findViewById(R.id.mobileNumber);
            p_totalcost = itemView.findViewById(R.id.p_totalcost);
            pStatus = itemView.findViewById(R.id.status);
            cb = itemView.findViewById(R.id.img_pa_preview);
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
               // addItem(i, model);
            }
        }
    }


//    public void addItem(int position, RegisterPatient model) {
//        patientArrayList.add(position, model);
//        notifyItemInserted(position);
//    }
}

package com.accusterltapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.accusterltapp.model.Patient;
import com.accusterltapp.model.RegisterPatient;
import com.accusterltapp.R;

import java.util.ArrayList;


/**
 * Created by appideas-user2 on 14/7/17.
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.CardViewHolder> {

    private ArrayList<Patient> itemList;
    private Context context;

    public PatientAdapter(Context context) {
        this.context = context;
    }


    public PatientAdapter(Context context, ArrayList<Patient> itemList) {
        this.context = context;
        this.itemList = itemList;

    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patientadapter, parent, false);
        context = parent.getContext();
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        final RegisterPatient patient = null;
        // cast the generic view holder to our specific one
        final CardViewHolder holders = holder;
        holders.p_token.setText(patient.getpLabelId());
        holders.p_name.setText(patient.getUserregistration_complete_name());
        holders.pSex.setText(patient.getUserregistration_mobile_number());
        holders.p_totalcost.setText(patient.getUserregistration_org_id());
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView p_token, pSex, pCode, p_totalcost, p_name;

        public CardViewHolder(View itemView) {
            super(itemView);
            p_token = itemView.findViewById(R.id.pId);
            p_name = itemView.findViewById(R.id.p_name);
            pSex = itemView.findViewById(R.id.pSex);
            pCode = itemView.findViewById(R.id.testCode);
            p_totalcost = itemView.findViewById(R.id.p_totalcost);

        }
    }
}
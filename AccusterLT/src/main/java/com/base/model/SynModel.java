package com.base.model;

import com.accusterltapp.model.RegisterPatient;
import com.accusterltapp.model.TesTDetailsToSyn;

import java.util.ArrayList;
import com.accusterltapp.model.ReposrtSyncModel;

/**
 * Created by LoB Android on 14/12/17.
 */

public class SynModel {
    private ArrayList<CampDetails> camp_list;
    private ArrayList<RegisterPatient> patient_list;
    private ArrayList<TesTDetailsToSyn> report_list;
    private ArrayList<ReposrtSyncModel> report_list1;

    public ArrayList<RegisterPatient> getPatient_list() {
        return patient_list;
    }

    public ArrayList<TesTDetailsToSyn> getReport_list() {
        return report_list;
    }

    public ArrayList<CampDetails> getCamp_list() {
        return camp_list;
    }

    public void setCamp_list(ArrayList<CampDetails> camp_list) {
        this.camp_list = camp_list;
    }

    public void setPatient_list(ArrayList<RegisterPatient> patient_list) {
        this.patient_list = patient_list;
    }

    public void setReport_list(ArrayList<TesTDetailsToSyn> report_list) {
        this.report_list = report_list;
    }
}

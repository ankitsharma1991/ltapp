package com.accusterltapp.model;

import java.util.ArrayList;

public class ApoointMentDataToSend {
    String camp_id;


    public String getCamp_id() {
        return camp_id;
    }

    public void setCamp_id(String camp_id) {
        this.camp_id = camp_id;
    }

    public ArrayList<PatientIdList> getList() {
        return list;
    }

    public void setList(ArrayList<PatientIdList> list) {
        this.list = list;
    }

    ArrayList<PatientIdList> list;
}

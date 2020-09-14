package com.accusterltapp.model;

import java.util.ArrayList;

public class CampList1 {
    ArrayList<CampDetails1> campList;
    String status;

    public ArrayList<CampDetails1> getCampList() {
        return campList;
    }

    public void setCampList(ArrayList<CampDetails1> campList) {
        this.campList = campList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

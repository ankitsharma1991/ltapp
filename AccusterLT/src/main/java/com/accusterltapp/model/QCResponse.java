package com.accusterltapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class QCResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("qcDetails")
    @Expose
    private ArrayList<QCDetail> qcData = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ArrayList<QCDetail> getQcData() {
        return qcData;
    }

    public void setQcDetails(ArrayList<QCDetail> qcDetails) {
        this.qcData = qcDetails;
    }
}

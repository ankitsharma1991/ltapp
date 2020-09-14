package com.accusterltapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QCDetail {
    @SerializedName("test_id")
    @Expose
    private int testId;
    @SerializedName("test_name")
    @Expose
    private String testName;
    @SerializedName("qc_id")
    @Expose
    private Integer qcId;
    @SerializedName("qc_L1")
    @Expose
    private String qcL1;
    @SerializedName("qc_L2")
    @Expose
    private String qcL2;
    @SerializedName("qc_C1")
    @Expose
    private String qcC1;
    @SerializedName("qc_C2")
    @Expose
    private String qcC2;
    @SerializedName("qc_C3")
    @Expose
    private String qcC3;
    @SerializedName("qc_Lab_id")
    @Expose
    private String qcLabId;
    @SerializedName("qcType")
    @Expose
    private String qcType;
    @SerializedName("qcDateUpdate")
    @Expose
    private String qcDateUpdate;
    @SerializedName("qc_lt_id")
    @Expose
    private Integer qcLtId;
    @SerializedName("qc_status")
    @Expose
    private  int qc_status;

    @SerializedName("camp_name")
    @Expose
    private String campName;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @SerializedName("qc_created_time")
    @Expose
    private  String time;

    public QCDetail(int testId, String testName, Integer qcId, String qcL1, String qcL2, String qcC1, String qcC2, String qcC3, String qcLabId, String qcType, String qcDateUpdate, Integer qcLtId, int qc_status, String campName) {
        this.testId = testId;
        this.testName = testName;
        this.qcId = qcId;
        this.qcL1 = qcL1;
        this.qcL2 = qcL2;
        this.qcC1 = qcC1;
        this.qcC2 = qcC2;
        this.qcC3 = qcC3;
        this.qcLabId = qcLabId;
        this.qcType = qcType;
        this.qcDateUpdate = qcDateUpdate;
        this.qcLtId = qcLtId;
        this.qc_status = qc_status;
        this.campName = campName;
    }

    public QCDetail() {
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getQcId() {
        return qcId;
    }

    public void setQcId(Integer qcId) {
        this.qcId = qcId;
    }

    public String getQcL1() {
        return qcL1;
    }

    public void setQcL1(String qcL1) {
        this.qcL1 = qcL1;
    }

    public String getQcL2() {
        return qcL2;
    }

    public void setQcL2(String qcL2) {
        this.qcL2 = qcL2;
    }

    public String getQcC1() {
        return qcC1;
    }

    public void setQcC1(String qcC1) {
        this.qcC1 = qcC1;
    }

    public String getQcC2() {
        return qcC2;
    }

    public void setQcC2(String qcC2) {
        this.qcC2 = qcC2;
    }

    public String getQcC3() {
        return qcC3;
    }

    public void setQcC3(String qcC3) {
        this.qcC3 = qcC3;
    }

    public String getQcLabId() {
        return qcLabId;
    }

    public void setQcLabId(String qcLabId) {
        this.qcLabId = qcLabId;
    }

    public String getQcType() {
        return qcType;
    }

    public void setQcType(String qcType) {
        this.qcType = qcType;
    }

    public String getQcDateUpdate() {
        return qcDateUpdate;
    }

    public void setQcDateUpdate(String qcDateUpdate) {
        this.qcDateUpdate = qcDateUpdate;
    }

    public Integer getQcLtId() {
        return qcLtId;
    }

    public void setQcLtId(Integer qcLtId) {
        this.qcLtId = qcLtId;
    }

    public int getQc_status() {
        return qc_status;
    }

    public void setQc_status(int qc_status) {
        this.qc_status = qc_status;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }
}

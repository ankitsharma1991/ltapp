package com.accusterltapp.model;

public class QcData {
    String test_id;
    String C1;
    String C2;
    String lab_id;
    String camp_name;
    int qc_status;

    public QcData(String test_id, String c1, String c2, String lab_id, String camp_name, int qc_status, String date, String time, String status, String c3, String l1, String l2) {
        this.test_id = test_id;
        C1 = c1;
        C2 = c2;
        this.lab_id = lab_id;
        this.camp_name = camp_name;
        this.qc_status = qc_status;
        this.date = date;
        this.time = time;
        this.status = status;
        C3 = c3;
        L1 = l1;
        L2 = l2;
    }

    public QcData() {
    }

    public int getQc_status() {
        return qc_status;
    }

    public void setQc_status(int qc_status) {
        this.qc_status = qc_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String date;
String time;
String status;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLab_id() {
        return lab_id;
    }

    public void setLab_id(String lab_id) {
        this.lab_id = lab_id;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getC1() {
        return C1;
    }

    public void setC1(String c1) {
        C1 = c1;
    }

    public String getC2() {
        return C2;
    }

    public void setC2(String c2) {
        C2 = c2;
    }

    public String getC3() {
        return C3;
    }

    public void setC3(String c3) {
        C3 = c3;
    }

    public String getL1() {
        return L1;
    }

    public void setL1(String l1) {
        L1 = l1;
    }

    public String getL2() {
        return L2;
    }

    public void setL2(String l2) {
        L2 = l2;
    }

    public String getCamp_name() {
        return camp_name;
    }

    public void setCamp_name(String camp_name) {
        this.camp_name = camp_name;
    }

    String C3;
    String L1;
    String L2;

}

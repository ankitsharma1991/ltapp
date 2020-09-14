package com.accusterltapp.model;

import java.util.ArrayList;

public class ReposrtSyncModel {
    private String report_patient_code;

    private String report_result;

    private String report_created_time;

    private ArrayList<String> report_ref_img_name;

    private String report_camp_code;

    private String report_id;

    private String report_lt_id;

    private String report_test_id;

    public String getReport_patient_code() {
        return report_patient_code;
    }

    public String getReport_result() {
        return report_result;
    }

    public String getReport_created_time() {
        return report_created_time;
    }

    public ArrayList<String> getReport_ref_img_name() {
        return report_ref_img_name;
    }

    public String getReport_camp_code() {
        return report_camp_code;
    }

    public String getReport_id() {
        return report_id;
    }

    public String getReport_lt_id() {
        return report_lt_id;
    }

    public String getReport_test_id() {
        return report_test_id;
    }

    public void setReport_patient_code(String report_patient_code) {
        this.report_patient_code = report_patient_code;
    }

    public void setReport_result(String report_result) {
        this.report_result = report_result;
    }

    public void setReport_created_time(String report_created_time) {
        this.report_created_time = report_created_time;
    }

    public void setReport_ref_img_name(ArrayList report_ref_img_name) {
        this.report_ref_img_name = report_ref_img_name;
    }

    public void setReport_camp_code(String report_camp_code) {
        this.report_camp_code = report_camp_code;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public void setReport_lt_id(String report_lt_id) {
        this.report_lt_id = report_lt_id;
    }

    public void setReport_test_id(String report_test_id) {
        this.report_test_id = report_test_id;
    }
}

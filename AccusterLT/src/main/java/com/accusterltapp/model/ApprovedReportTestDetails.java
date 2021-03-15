package com.accusterltapp.model;

import java.util.ArrayList;

public class ApprovedReportTestDetails  {
    String  method;
    String  test_head;
    String  sub_test_head;
    String  head_id;
    String   test_name;
    String  test_upper_bound_male;
    String test_low_bound_male;
    String test_low_bound_female;
    String test_interpretation;
    String test_precautions;
    String report_status;

    public String getReport_status() {
        return report_status;
    }

    public void setReport_status(String report_status) {
        this.report_status = report_status;
    }

    public int  test_manual_status;
    public void setTest_manual_status(int test_manual_status) {
        this.test_manual_status = test_manual_status;
    }

    public boolean is_manual_status() {
        return test_manual_status == 1;
    }

    public String getTest_interpretation() {
        return test_interpretation;
    }

    public void setTest_interpretation(String test_interpretation) {
        this.test_interpretation = test_interpretation;
    }

    public String getTest_precautions() {
        return test_precautions;
    }

    public void setTest_precautions(String test_precautions) {
        this.test_precautions = test_precautions;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTest_head() {
        return test_head;
    }

    public void setTest_head(String test_head) {
        this.test_head = test_head;
    }

    public String getSubTest_head() {
        return sub_test_head;
    }

    public void setSubTest_head(String sub_test_head) {
        this.sub_test_head = sub_test_head;
    }


    public String getHead_id() {
        return head_id;
    }

    public void setHead_id(String head_id) {
        this.head_id = head_id;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getTest_upper_bound_male() {
        return test_upper_bound_male;
    }

    public void setTest_upper_bound_male(String test_upper_bound_male) {
        this.test_upper_bound_male = test_upper_bound_male;
    }

    public String getTest_low_bound_male() {
        return test_low_bound_male;
    }

    public void setTest_low_bound_male(String test_low_bound_male) {
        this.test_low_bound_male = test_low_bound_male;
    }

    public String getTest_low_bound_female() {
        return test_low_bound_female;
    }

    public void setTest_low_bound_female(String test_low_bound_female) {
        this.test_low_bound_female = test_low_bound_female;
    }

    public String getTest_upper_bound_female() {
        return test_upper_bound_female;
    }

    public void setTest_upper_bound_female(String test_upper_bound_female) {
        this.test_upper_bound_female = test_upper_bound_female;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }

    public String getOrganization_address() {
        return organization_address;
    }

    public void setOrganization_address(String organization_address) {
        this.organization_address = organization_address;
    }
    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getReport_approve_status() {
        return report_approve_status;
    }

    public void setReport_approve_status(String report_approve_status) {
        this.report_approve_status = report_approve_status;
    }
    String test_upper_bound_female;
    String result;
    String date;
    String unit;
    String  organization_name;
    String   organization_address;
    String  report_id;
    String  report_approve_status;

    //ADDON fields

    String  image_permission;
    String  test_code;
    String  test_id;
    String  test_price;
    String  camp_code;

    public String getImage_permission() {
        return image_permission;
    }

    public void setImage_permission(String image_permission) {
        this.image_permission = image_permission;
    }

    public String getTest_code() {
        return test_code;
    }

    public void setTest_code(String test_code) {
        this.test_code = test_code;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getTest_price() {
        return test_price;
    }

    public void setTest_price(String test_price) {
        this.test_price = test_price;
    }

    public String getCamp_code() {
        return camp_code;
    }

    public void setCamp_code(String camp_code) {
        this.camp_code = camp_code;
    }

}

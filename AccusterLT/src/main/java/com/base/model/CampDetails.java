package com.base.model;

import android.text.TextUtils;

/**
 * Created by pbadmin on 27/8/17.
 */

public class CampDetails {
   public String camp_name;
    public String orgName;
    public String camp_organization_id;
    public String camp_start_date;
    public String camp_end_date;
    public String camp_start_time;
    public String camp_end_time;
    public String camp_address;
    public String camp_description;
    public String camp_created_date_time;
    public String camp_created_user_id;
    public String camp_id;
    public String camp_sync_status;
    public String camp_code;
    public String camp_pathlogist_Id;
    public String camp_package_code;

    public CampDetails(String camp_name, String orgName, String camp_organization_id, String camp_start_date, String camp_end_date, String camp_start_time, String camp_end_time, String camp_address, String camp_description, String camp_created_date_time, String camp_created_user_id, String camp_id, String camp_sync_status, String camp_code, String camp_pathlogist_Id, String camp_package_code) {
        this.camp_name = camp_name;
        this.orgName = orgName;
        this.camp_organization_id = camp_organization_id;
        this.camp_start_date = camp_start_date;
        this.camp_end_date = camp_end_date;
        this.camp_start_time = camp_start_time;
        this.camp_end_time = camp_end_time;
        this.camp_address = camp_address;
        this.camp_description = camp_description;
        this.camp_created_date_time = camp_created_date_time;
        this.camp_created_user_id = camp_created_user_id;
        this.camp_id = camp_id;
        this.camp_sync_status = camp_sync_status;
        this.camp_code = camp_code;
        this.camp_pathlogist_Id = camp_pathlogist_Id;
        this.camp_package_code = camp_package_code;
    }

    public CampDetails() {
    }

    public String getCamp_name() {
        return camp_name;
    }

    public void setCamp_name(String camp_name) {
        this.camp_name = camp_name;
    }

    public String getCamp_organization_id() {
        return TextUtils.isEmpty(camp_organization_id)?"":camp_organization_id;
    }

    public void setCamp_organization_id(String camp_organization_id) {
        this.camp_organization_id = camp_organization_id;
    }

    public String getCamp_start_date() {
        return camp_start_date;
    }

    public void setCamp_start_date(String camp_start_date) {
        this.camp_start_date = camp_start_date;
    }

    public String getCamp_end_date() {
        return camp_end_date;
    }

    public void setCamp_end_date(String camp_end_date) {
        this.camp_end_date = camp_end_date;
    }

    public String getCamp_start_time() {
        return camp_start_time;
    }

    public void setCamp_start_time(String camp_start_time) {
        this.camp_start_time = camp_start_time;
    }

    public String getCamp_end_time() {
        return camp_end_time;
    }

    public void setCamp_end_time(String camp_end_time) {
        this.camp_end_time = camp_end_time;
    }

    public String getCamp_address() {
        return camp_address;
    }

    public void setCamp_address(String camp_address) {
        this.camp_address = camp_address;
    }

    public String getCamp_id() {
        return camp_id;
    }

    public void setCamp_id(String camp_id) {
        this.camp_id = camp_id;
    }

    public String getCamp_code() {
        return camp_code;
    }

    public void setCamp_code(String camp_code) {
        this.camp_code = camp_code;
    }

    public String getCamp_pathlogist_Id() {
        return camp_pathlogist_Id;
    }

    public void setCamp_pathlogist_Id(String camp_pathlogist_Id) {
        this.camp_pathlogist_Id = camp_pathlogist_Id;
    }

    public String getCampName() {
        return camp_name;
    }

    public void setCampName(String camp_name) {
        this.camp_name = camp_name;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getStartDate() {
        return camp_start_date;
    }

    public void setStartDate(String startDate) {
        this.camp_start_date = startDate;
    }

    public String getEndDate() {
        return camp_end_date;
    }

    public void setEndDate(String endDate) {
        this.camp_end_date = endDate;
    }

    public String getStartTime() {
        return camp_start_time;
    }

    public void setStartTime(String startTime) {
        this.camp_start_time = startTime;
    }

    public String getEndTime() {
        return camp_end_time;
    }

    public void setEndTime(String endTime) {
        this.camp_end_time = endTime;
    }

    public String getAddress() {
        return camp_address;
    }

    public void setAddress(String address) {
        this.camp_address = address;
    }

    public String getCamp_description() {
        return camp_description;
    }

    public void setCamp_description(String camp_description) {
        this.camp_description = camp_description;
    }

    public String getCamp_created_date_time() {
        return camp_created_date_time;
    }

    public void setCamp_created_date_time(String camp_created_date_time) {
        this.camp_created_date_time = camp_created_date_time;
    }

    public String getCamp_created_user_id() {
        return camp_created_user_id;
    }

    public void setCamp_created_user_id(String camp_created_user_id) {
        this.camp_created_user_id = camp_created_user_id;
    }

    public String getCampID() {
        return camp_id;
    }

    public void setCampID(String camp_id) {
        this.camp_id = camp_id;
    }

    @Override
    public String toString() {
        return camp_name;
    }

    public String getCamp_sync_status() {
        return camp_sync_status;
    }

    public void setCamp_sync_status(String camp_sync_status) {
        this.camp_sync_status = camp_sync_status;
    }

    public String getCamp_package_code() {
        return camp_package_code;
    }

    public void setCamp_package_code(String camp_package_code) {
        this.camp_package_code = camp_package_code;
    }


}

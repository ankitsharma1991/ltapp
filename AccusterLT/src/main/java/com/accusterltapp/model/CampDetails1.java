package com.accusterltapp.model;

public class CampDetails1 {
    String id;
    String camp_organization_id;
    String name;
    String startdate;

    public String getCamp_code() {
        return camp_code;
    }

    public void setCamp_code(String camp_code) {
        this.camp_code = camp_code;
    }

    String enddate;
    String starttime;
    boolean footer=true;
    String camp_code;

    public boolean isHeader() {
        return header;
    }

    public boolean isFooter() {
        return footer;
    }

    public void setFooter(boolean footer) {
        this.footer = footer;
    }

    public void setHeader(boolean header) {
        this.header = header;

    }

    String endtime;
    String camp_created_user_id;
    boolean header=false;

    public String getCamp_address() {
        return camp_address;
    }

    public void setCamp_address(String camp_address) {
        this.camp_address = camp_address;
    }

    public String getReport_header() {
        return report_header;
    }

    public void setReport_header(String report_header) {
        this.report_header = report_header;
    }

    public String getCampLogo() {
        return campLogo;
    }

    public void setCampLogo(String campLogo) {
        this.campLogo = campLogo;
    }

    String created;
    String status;
    String organization_name;
    String camp_address;
    String report_header;
    String campLogo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCamp_organization_id() {
        return camp_organization_id;
    }

    public void setCamp_organization_id(String camp_organization_id) {
        this.camp_organization_id = camp_organization_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getCamp_created_user_id() {
        return camp_created_user_id;
    }

    public void setCamp_created_user_id(String camp_created_user_id) {
        this.camp_created_user_id = camp_created_user_id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }
}

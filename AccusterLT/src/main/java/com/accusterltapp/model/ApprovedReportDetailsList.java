package com.accusterltapp.model;

import java.util.ArrayList;

public class ApprovedReportDetailsList {
    ArrayList<RegisterPatient> userdetails;
    ArrayList<ApprovedReportTestDetails> test_details;
    PathologistData pathlogist;

    public PathologistData getPathlogist() {
        return pathlogist;
    }

    public void setPathlogist(PathologistData pathlogist) {
        this.pathlogist = pathlogist;
    }

    public ArrayList<RegisterPatient> getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(ArrayList<RegisterPatient> userdetails) {
        this.userdetails = userdetails;
    }

    public ArrayList<ApprovedReportTestDetails> getTest_details() {
        return test_details;
    }

    public void setTest_details(ArrayList<ApprovedReportTestDetails> test_details) {
        this.test_details = test_details;
    }
}

package com.accusterltapp.model;

import java.util.ArrayList;

public class ApprovedReportList {
    //"status": 1,
          ArrayList<ApprovedReportData>  all_report;

    public ArrayList<ApprovedReportData> getTotalApprovedReport() {
        return all_report;
    }

    public void setTotalApprovedReport(ArrayList<ApprovedReportData> totalApprovedReport) {
        this.all_report = totalApprovedReport;
    }
}

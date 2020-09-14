package com.accusterltapp.model;

/**
 * Created by LoB Android on 17/12/17.
 */

public class ReportSyncResponse {
    private String message;

    private String[] NoneSync_report;

    private String error;

    private String status;

    private String[] sync_report;

    public String getMessage() {
        return message;
    }

    public String[] getNoneSync_report() {
        return NoneSync_report;
    }

    public String getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public String[] getSync_report() {
        return sync_report;
    }
}
